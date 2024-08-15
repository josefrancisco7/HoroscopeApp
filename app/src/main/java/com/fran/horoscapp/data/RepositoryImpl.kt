package com.fran.horoscapp.data

import android.util.Log
import com.fran.horoscapp.data.network.HorosocopeApiService
import com.fran.horoscapp.domain.model.PredictionModel
import com.fran.horoscapp.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: HorosocopeApiService): Repository {
    override suspend fun getPrediction(sign: String):PredictionModel? {
        runCatching { apiService.getHoroscope(sign) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("fran","Ha ocurrido un error ${it.message}") }
        return null

    }
}