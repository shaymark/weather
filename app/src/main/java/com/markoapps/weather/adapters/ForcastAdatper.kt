package com.markoapps.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.markoapps.weather.convertors.TempetureType
import com.markoapps.weather.convertors.toTemprature
import com.markoapps.weather.convertors.toTempratureText
import com.markoapps.weather.databinding.HomeHourlyItemBinding
import com.markoapps.weather.models.ForecastModel
import com.markoapps.weather.utils.Formaters
import com.markoapps.weather.viewmodels.getIconUrl
import java.util.*

data class Forecast(
        val dateTime: Date,
        val icon: String,
        val humidity: Float,
        val temp: Float,
        val mode: TempetureType
)

class ForecastAdapter: ListAdapter<Forecast, ForecastAdapter.ViewHolder>(ForecastDiffUtil()) {

    private val formaters: Formaters = Formaters()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(HomeHourlyItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: HomeHourlyItemBinding): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(forecast: Forecast) {

            itemBinding.apply {
                time.text = formaters.getTimeFromDateHourly(forecast.dateTime)
                humidity.text = forecast.humidity.toString()
                Glide
                    .with(itemView)
                    .load(getIconUrl(forecast.icon))
                    .centerCrop()
                    .into(icon);
                temp.text = forecast.temp.toTempratureText(forecast.mode)
            }
        }
    }

    class ForecastDiffUtil: DiffUtil.ItemCallback<Forecast>() {
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem == newItem
        }
    }
}