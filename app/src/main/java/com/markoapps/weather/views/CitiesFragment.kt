package com.markoapps.weather.views

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.markoapps.weather.CityAdapter
import com.markoapps.weather.CityItemModel
import com.markoapps.weather.R
import com.markoapps.weather.WeatherApplication
import com.markoapps.weather.convertors.TemperatureType
import com.markoapps.weather.databinding.FragmentCitiesBinding
import com.markoapps.weather.utils.TextChangedListener
import com.markoapps.weather.viewmodels.CitiesViewModel
import com.markoapps.weather.viewmodels.SharedViewModel
import com.markoapps.weather.viewmodels.MyViewModelFactory
import javax.inject.Inject


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CitiesFragment : Fragment() {

    @Inject lateinit var myViewModelFactory: MyViewModelFactory

    val sharedViewModel: SharedViewModel by activityViewModels()

    val citiesViewModel: CitiesViewModel by navGraphViewModels(R.id.nav_graph) {myViewModelFactory}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as WeatherApplication).appComponent.inject(this)
    }


    private lateinit var binding: FragmentCitiesBinding
    lateinit var listAdapter: CityAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCitiesBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        citiesViewModel._mode.value = TemperatureType.Celsius

        citiesViewModel.refreshCities()

        sharedViewModel.setTitle("Cities")
        sharedViewModel.setMenu(R.menu.menu_cities)
        sharedViewModel.buttons.observe(viewLifecycleOwner) {
            when(it.get()) {
                R.id.action_refresh -> citiesViewModel.refreshCities()
                R.id.action_restore -> citiesViewModel.restoreAllCities()
                R.id.action_toggle_temp -> {
                    citiesViewModel.toggleMode()
                }
            }
        }

        binding.cityList.apply {
            listAdapter = CityAdapter(
                    onClick = ::onItemClick,
                    onLongClick = ::onItemLongClick
            )

            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter

        }

        binding.filterEditText.addTextChangedListener(object : TextChangedListener<EditText?>(binding.filterEditText) {
            override fun onTextChanged(target: EditText?, s: Editable?) {
                citiesViewModel.setFilter(s.toString())
            }
        })

        citiesViewModel.filteredList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it.map {
                CityItemModel(
                        id = it.id,
                        name = it.name,
                        description = it.weather.first().description,
                        icon = it.weather.first().icon,
                        tempMax = it.main.TempMax,
                        tempMin = it.main.TempMin,
                        mode = citiesViewModel.mode.value!!
                )
            })
        }

        citiesViewModel.filterString.observe(viewLifecycleOwner) {
            binding.apply {
                if(!filterEditText.text.toString().equals(it)) {
                    binding.filterEditText.setText(it)
                }
            }
        }

    }

    fun onItemClick(id: Long) {
        citiesViewModel.setSelectedCity(id)
        val navigator = findNavController()
        navigator.navigate(R.id.action_citiesFragment_to_cityDetailsFragment)
    }

    fun onItemLongClick(id: Long) {
        MaterialDialog(requireContext()).show {
            title(R.string.delete_dialog_title)
            positiveButton(R.string.delete_dialog_positive) { dialog ->
                removeItem(id)
            }
            negativeButton(R.string.delete_dialog_dismiss) { dialog ->
                // do nothing
            }
        }

    }

    fun removeItem(id: Long) {
        citiesViewModel.removeCity(id)
    }

}