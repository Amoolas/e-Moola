package com.amoola.e_moola.user.model

data class User (
    var userId: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var confirmPass: String
)