package com.vik.countryapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vik.countryapp.R
import com.vik.countryapp.databinding.ItemCountryBinding
import com.vik.countryapp.models.Country
import com.vik.countryapp.models.FlagObject

class CountryListAdapter(private val _countries: ArrayList<Country> = arrayListOf()) :
    RecyclerView.Adapter<CountryListAdapter.CountryVH>() {

    private lateinit var map: HashMap<String, FlagObject>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryVH {
        val inflator = LayoutInflater.from(parent.context)
        val binder =
            DataBindingUtil.inflate<ItemCountryBinding>(
                inflator,
                R.layout.item_country,
                parent,
                false
            )
        return CountryVH(binder)
    }

    override fun getItemCount(): Int = _countries.size

    override fun onBindViewHolder(holder: CountryVH, position: Int) {

        val country = _countries[position]
        holder.bind(country, map[country.alpha2Code])
    }

    fun dispatchToAdapter(newList: ArrayList<Country>, flagMap: HashMap<String, FlagObject>) {
        map = flagMap
        val callback = CountryDiffCallback(_countries, newList)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
        _countries.clear()
        _countries.addAll(newList)
    }


    private class CountryDiffCallback(
        val oldList: ArrayList<Country>,
        val newList: ArrayList<Country>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList.getOrNull(oldItemPosition)?.alpha2Code == newList.getOrNull(newItemPosition)?.alpha2Code

        override

        fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true

    }


    class CountryVH(private val binder: ItemCountryBinding) : RecyclerView.ViewHolder(binder.root) {

        fun bind(country: Country, flag: FlagObject?) {
            with(binder) {
                this.country = country
                this.flag = flag?.emoji
                executePendingBindings()
            }
        }
    }

}