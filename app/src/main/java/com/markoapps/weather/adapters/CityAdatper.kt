package com.markoapps.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.markoapps.weather.convertors.TemperatureType
import com.markoapps.weather.convertors.toTemperatureText
import com.markoapps.weather.databinding.CitiesItemBinding
import com.markoapps.weather.viewmodels.getIconUrl

data class CityItemModel(
        val id: Long,
        val name: String,
        val description: String,
        val icon: String,
        val tempMin: Float,
        val tempMax: Float,
        val mode: TemperatureType
)


class CityAdapter(val onClick: (itemId: Long) -> Unit, val onLongClick: (itemId: Long) -> Unit): ListAdapter<CityItemModel, CityAdapter.ViewHolder>(CityDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(CitiesItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: CitiesItemBinding): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(cityItemModel: CityItemModel) {

            itemBinding.apply {
                name.text = cityItemModel.name
                description.text = cityItemModel.description
                Glide
                    .with(itemView)
                    .load(getIconUrl(cityItemModel.icon))
                    .centerCrop()
                    .into(icon);
                tempMinMax.text = "min ${cityItemModel.tempMin.toTemperatureText(cityItemModel.mode)}" +
                                " max ${cityItemModel.tempMax.toTemperatureText(cityItemModel.mode)}"

                root.setOnClickListener { onClick(cityItemModel.id) }
                root.setOnLongClickListener {
                    onLongClick(cityItemModel.id)
                    true
                }
            }
        }
    }

    class CityDiffUtil: DiffUtil.ItemCallback<CityItemModel>() {
        override fun areItemsTheSame(oldItem: CityItemModel, newItem: CityItemModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CityItemModel, newItem: CityItemModel): Boolean {
            return oldItem == newItem
        }
    }
}