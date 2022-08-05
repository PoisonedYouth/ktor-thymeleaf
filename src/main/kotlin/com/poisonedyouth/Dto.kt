package com.poisonedyouth

import kotlin.random.Random
import kotlin.random.Random.Default

data class CustomerData(
    var customerId: Long = Random.nextLong(11111,99999),
    var firstName: String = "",
    var lastName: String = "",
    var birthdate: String = "",
    var street: String = "",
    var streetNumber: String = "",
    var zipCode: Int = 0,
    var city: String = ""
)
