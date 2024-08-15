package com.fran.horoscapp.ui.detail

import com.fran.horoscapp.domain.model.HoroscopeModel

sealed class HoroscopeDetailState {
    data object Loading:HoroscopeDetailState()
    data class Error(val error:String):HoroscopeDetailState()
    data class Sucess(val prediction:String, val sign:String, val horoscopeModel: HoroscopeModel):HoroscopeDetailState()

}