package com.example.apicovidapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apicovidapp.model.Country
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recycler_countries.view.*

class AdapterCountries(val list: List<Country>) :
    RecyclerView.Adapter<AdapterCountries.CountriesViewHolder>() {
    class CountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_countries, parent, false)

        return CountriesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val view = holder.itemView
        Picasso.get().load(list[position].countryFlagUrl).into(view.imgCountry)
        view.txtCountryName.text = list[position].country
        view.txtTotalConfirmed.text = list[position].totalConfirmed.toString()
            .plus(" " + view.resources.getString(R.string.confirmed))
        view.txtTotalDead.text = list[position].totalDeaths.toString()
            .plus(" " + view.resources.getString(R.string.dead))
        view.txtTotalRecovered.text = list[position].totalRecovered.toString()
            .plus(" " + view.resources.getString(R.string.recovered))
    }

}