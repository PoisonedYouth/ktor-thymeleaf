package com.poisonedyouth

data class CustomerData(
    var customerId: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var birthdate: String = "",
    var street: String = "",
    var streetNumber: String = "",
    var zipCode: Int = 0,
    var city: String = ""
)
