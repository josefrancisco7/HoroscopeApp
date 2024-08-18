package com.fran.horoscapp.ui.provider

import org.junit.Assert.*
import org.junit.Test

class RandomCardProviderTest{

    @Test
    fun `getRandomCard should return random card`(){
        val randomCard=RandomCardProvider()

        val card =randomCard.getLucky()

        assertNotNull(card)
    }
}