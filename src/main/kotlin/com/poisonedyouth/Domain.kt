package com.poisonedyouth

import java.time.LocalDate

data class Customer(
    val customerId: Long,
    val firstName: String,
    val lastName: String,
    val birthdate: LocalDate,
    val address: Address
)

data class Address(
    val street: String,
    val streetNumber: String,
    val zipCode: Int,
    val city: String
)
