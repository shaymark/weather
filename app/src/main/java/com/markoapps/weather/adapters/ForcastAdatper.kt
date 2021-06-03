package com.markoapps.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.markoapps.weather.convertors.TemperatureType
import com.markoapps.weather.convertors.toTemperatureText
import com.markoapps.weather.databinding.HomeHourlyItemBinding
import com.markoapps.weather.utils.DateFormatter
import com.markoapps.weather.viewmodels.getIconUrl
import java.util.*

data class ForecastItemModel(
        val dateTime: Date,
        val icon: String,
        val humidity: Float,
        val temp: Float,
        val mode: TemperatureType
)

class ForecastAdapter: ListAdapter<ForecastItemModel, ForecastAdapter.ViewHolder>(ForecastDiffUtil()) {

    private val dateFormatter: DateFormatter = DateFormatter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(HomeHourlyItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: HomeHourlyItemBinding): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(forecastItemModel: ForecastItemModel) {

            itemBinding.apply {
                time.text = dateFormatter.getTimeFromDateHourly(forecastItemModel.dateTime)
                humidity.text = forecastItemModel.humidity.toString()
                Glide
                    .with(itemView)
                    .load(getIconUrl(forecastItemModel.icon))
                    .centerCrop()
                    .into(icon);
                temp.text = forecastItemModel.temp.toTemperatureText(forecastItemModel.mode)
            }
        }
    }

    class ForecastDiffUtil: DiffUtil.ItemCallback<ForecastItemModel>() {
        override fun areItemsTheSame(oldItem: ForecastItemModel, newItem: ForecastItemModel): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

        override fun areContentsTheSame(oldItem: ForecastItemModel, newItem: ForecastItemModel): Boolean {
            return oldItem == newItem
        }
    }
}