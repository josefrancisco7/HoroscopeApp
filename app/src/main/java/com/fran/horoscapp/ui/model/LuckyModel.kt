package com.fran.horoscapp.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringDef
import androidx.annotation.StringRes

data class LuckyModel (
    @DrawableRes val image:Int,
    @StringRes val text:Int
)