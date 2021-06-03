package com.markoapps.weather.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.markoapps.weather.R
import com.markoapps.weather.convertors.toTempratureText
import com.markoapps.weather.databinding.FragmentCityDetailsBinding
import com.markoapps.weather.di.Providers
import com.markoapps.weather.viewmodels.CitiesViewModel
import com.markoapps.weather.viewmodels.SharedViewModel
import com.markoapps.weather.viewmodels.getIconUrl

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CityDetailsFragment : Fragment() {

    val sharedViewModel: SharedViewModel by activityViewModels()

    val citiesViewModel: CitiesViewModel by navGraphViewModels(R.id.nav_graph) {Providers.weatherViewModelFactory}

    private lateinit var binding: FragmentCityDetailsBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCityDetailsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       citiesViewModel.selectedCityLiveData.observe(viewLifecycleOwner) {

           sharedViewModel.setTitle(it.name)
           sharedViewModel.setMenu(R.menu.menu_city_details)
           sharedViewModel.buttons.observe(viewLifecycleOwner) {
               when(it.get()) {
                   R.id.action_refresh -> citiesViewModel.refreshCities()
               }
           }

           binding.apply {
               name.text = it.name
               description.text = it.weather.first().description
               minMax.text = "min - ${it.main.TempMin.toTempratureText(citiesViewModel.mode.value!!)} " +
                            "max - ${it.main.TempMax.toTempratureText(citiesViewModel.mode.value!!)}"
               Glide
                   .with(icon)
                   .load(getIconUrl(it.weather.first().icon))
                   .centerCrop()
                   .into(icon);
           }

       }
    }


}