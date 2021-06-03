package com.markoapps.weather.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.markoapps.weather.Forecast
import com.markoapps.weather.ForecastAdapter
import com.markoapps.weather.R
import com.markoapps.weather.convertors.TempetureType
import com.markoapps.weather.convertors.toTempratureText
import com.markoapps.weather.databinding.FragmentHomeBinding
import com.markoapps.weather.di.Providers
import com.markoapps.weather.utils.Formaters
import com.markoapps.weather.viewmodels.CurrentWeatherViewModel
import com.markoapps.weather.viewmodels.SharedViewModel
import com.markoapps.weather.viewmodels.getIconUrl
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: CurrentWeatherViewModel by viewModels { Providers.weatherViewModelFactory }
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val formaters: Formaters = Formaters()

    private lateinit var hourlyForecastAdapter: ForecastAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentWeather.observe(viewLifecycleOwner) {
            binding.main.apply {
                viewModel.currentTime.value?.let {
                    date.text = formaters.getTimeFromDate(it)
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
                sunrise.text = formaters.getTimeFromDateHourly(Date(it.sys.sunrise.toLong() * 1000))
                sunset.text = formaters.getTimeFromDateHourly(Date(it.sys.sunset.toLong() * 1000))
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
                    Forecast(
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
                TempetureType.Celsius -> "Celsius"
                TempetureType.Ferenite -> "Ferenite"
            }

            sharedViewModel.setTitle(title)

        }
    }

    fun tempText(temp: Float) = temp.toTempratureText(viewModel.mode.value!!)

}