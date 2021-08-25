package com.jandres.deudoresapp.utils

const val MIN_SIZE_PASSWORD = 6
const val EMAIL_DONT_MATCH = "Usuario NO existe."
const val INCORRECT_PASSWORD = "Contraseña Incorrecta."
const val PASSWORD_DONT_MATCH = "Contraseñas no coinciden."
const val PASSWORD_UNLESS = "Minimo 6 Caracteres"
const val EMAIL_EXAMPLE = "example@domain.com..."


fun delBlankSpace(text: String) : String {
    return text.replace("\\s".toRegex(),"")
}

fun equal(text1: String,text2: String) : Boolean {
    return text1 == text2
}