package com.example.apicovidapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apicovidapp.model.Country
import com.example.apicovidapp.retrofit.CountryApiProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "TAG"

class MainActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val startList = ArrayList<Country>()
    private val searchList = ArrayList<Country>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dispose = CountryApiProvider.provideApi().getSummary()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.countries.forEach() { country ->
                    val url = "https://www.countryflags.io/${country.countryCode}/flat/64.png"
                    country.countryFlagUrl = url
                }
                startList.addAll(it.countries)
                setupRecycler(it.countries)
            }
            .subscribe({ }, {
                Log.e(TAG, it.localizedMessage)
            })

        edtCountry.doAfterTextChanged { t ->
            searchList.clear()
            if (t.toString().isEmpty()) {
                setupRecycler(startList)
            } else {
                startList.forEach { item ->
                    if (item.country.toLowerCase().contains(t.toString().toLowerCase())) {
                        searchList.add(item)
                    }
                }
                setupRecycler(searchList)
            }
        }

        compositeDisposable.addAll(dispose)
    }

    private fun setupRecycler(countriesList: List<Country>) {
        recyclerView.adapter = AdapterCountries(countriesList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
