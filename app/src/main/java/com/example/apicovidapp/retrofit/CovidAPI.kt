package com.example.apicovidapp.retrofit

import com.example.apicovidapp.model.Covid
import io.reactivex.Single
import retrofit2.http.GET

interface CovidAPI {
    @GET("summary")
    fun getSummary() : Single<Covid>
}