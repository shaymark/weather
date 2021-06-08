package com.markoapps.weather.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.markoapps.weather.ForecastAdapter
import com.markoapps.weather.ForecastItemModel
import com.markoapps.weather.R
import com.markoapps.weather.WeatherApplication
import com.markoapps.weather.convertors.TemperatureType
import com.markoapps.weather.convertors.toTemperatureText
import com.markoapps.weather.databinding.FragmentHomeBinding
import com.markoapps.weather.utils.DateFormatter
import com.markoapps.weather.utils.TimeUtil
import com.markoapps.weather.viewmodels.CurrentWeatherViewModel
import com.markoapps.weather.viewmodels.MyViewModelFactory
import com.markoapps.weather.viewmodels.SharedViewModel
import com.markoapps.weather.viewmodels.getIconUrl
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.Temporal
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    @Inject
    lateinit var myViewModelFactory: MyViewModelFactory

    @Inject
    lateinit var timeUtil: TimeUtil

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: CurrentWeatherViewModel by viewModels { myViewModelFactory }
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val dateFormatter: DateFormatter = DateFormatter()

    private lateinit var hourlyForecastAdapter: ForecastAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as WeatherApplication).appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) {
            binding.main.apply {
                viewModel.currentTime.value?.let {
                    date.text = dateFormatter.getTimeFromDate(it)
                }
                location.text = it.name
                minMax.text = "Min ${tempText(it.main.TempMin)} - Max ${tempText(it.main.TempMax)}"
                temp.text = tempText(it.main.temp)
                feelsLike.text = "Feels like ${tempText(it.main.FeelsLike)}"
                description.text = it.weather.first().description
                Glide
                    .with(this@HomeFragment)
                    .load(getIconUrl(it.weather.first().icon))
                    .centerCrop()
                    .into(icon);
            }

            binding.sunriseSunset.apply {
                sunrise.text = dateFormatter.getTimeFromDateHourly(Date(it.sys.sunrise.toLong() * 1000))
                sunset.text = dateFormatter.getTimeFromDateHourly(Date(it.sys.sunset.toLong() * 1000))
                if(timeUtil.getCurrentTime().time > it.sys.sunrise && timeUtil.getCurrentTime().time < it.sys.sunset) {
                    custView.setSunBitmap(R.drawable.ic_more)
                } else {
                    custView.setSunBitmap(R.drawable.ic_sun)
                }
                var progress = (1  - timeUtil.getTimeUntilMidnite().toFloat() / 86400000) * 0.75f + 0.125f
                custView.setProgress(progress)
            }

            binding.details.apply {
                wind.text = it.wind.speed.toString() + "km\\h"
                humidity.text = it.main.humidity.toString() + "%"
                pressure.text = it.main.pressure.toString() + "hpa"
                visibility.text = (it.visibility / 1000).toString() + "km"
            }
        }


        binding.hourly.apply {
            hourlyForecastAdapter = ForecastAdapter()
            forecastList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            forecastList.adapter = hourlyForecastAdapter
        }

        viewModel.forecast.observe(viewLifecycleOwner) {
            binding.main.apply {
                hourlyForecastAdapter.submitList(it.map {
                    ForecastItemModel(
                            Date(it.dateTime * 1000),
                            it.weather.first().icon,
                            it.main.humidity,
                            it.main.temp,
                            viewModel.mode.value!!
                    )
                })
            }
        }

        viewModel.refreshCurrentWeather()
        viewModel.refreshWeatherForecast()

        sharedViewModel.setMenu(R.menu.menu_main)
        sharedViewModel.buttons.observe(viewLifecycleOwner) {
            when(it.get()) {
                R.id.action_refresh -> viewModel.refreshAll()
                R.id.action_more -> {
                    val navigation = findNavController()
                    navigation.navigate(R.id.action_homeFragment_to_citiesFragment)
                }
                R.id.action_toggle_temp -> {
                    viewModel.toggleMode()
                }
            }
        }
        viewModel.mode.observe(viewLifecycleOwner) {

            val title = when(it) {
                TemperatureType.Celsius -> "Celsius"
                TemperatureType.Fahrenheit -> "fahrenheit"
            }

            sharedViewModel.setTitle(title)

        }
    }

    fun tempText(temp: Float) = temp.toTemperatureText(viewModel.mode.value!!)

}