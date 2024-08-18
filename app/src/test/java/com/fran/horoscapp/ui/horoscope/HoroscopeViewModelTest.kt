package com.fran.horoscapp.ui.horoscope

import com.fran.horoscapp.data.providers.HoroscopeProvider
import com.fran.horoscapp.motherobject.HoroscopeMotherObject
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HoroscopeViewModelTest{

    @MockK
    lateinit var horoscopeProvider: HoroscopeProvider

    private lateinit var viewModel: HoroscopeViewModel

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `when viewmodel us created then horoscopes are loaded`(){
        every { horoscopeProvider.getHoroscope() } returns HoroscopeMotherObject.horoscopeInfoList
        viewModel = HoroscopeViewModel(horoscopeProvider)

        val horoscopes = viewModel.horoscope.value

        assertTrue(horoscopes.isNotEmpty())



    }
}