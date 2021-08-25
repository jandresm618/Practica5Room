package com.jandres.deudoresapp.utils

import android.util.Patterns

fun emailValidator(email: String) : Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

fun minTextSizeValidator(text: String,minTextSize : Int) : Boolean{
    val textSize = text.length
    return textSize >= minTextSize
}