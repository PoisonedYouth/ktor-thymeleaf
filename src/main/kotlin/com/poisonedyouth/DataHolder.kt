package com.poisonedyouth

import io.ktor.server.application.call
import io.ktor.server.request.path
import java.time.LocalDate

object DataHolder {

    val customerList = mutableListOf(
        Customer(
            firstName = "John",
            lastName = "Doe",
            customerId = 12345L,
            birthdate = LocalDate.of(2000, 1, 1),
            address = Address(
                street = "Main Street",
                streetNumber = "12A",
                zipCode = 90001,
                city = "Los Angeles"
            )
        ),
        Customer(
            firstName = "Max",
            lastName = "DeMarco",
            customerId = 12346L,
            birthdate = LocalDate.of(1988, 10, 2),
            address = Address(
                street = "Second Street",
                streetNumber = "45",
                zipCode = 90007,
                city = "Los Angeles"
            )
        )
    )

    fun getCustomerByRequestPath(path: String): Customer? {
        return customerList.firstOrNull {
            it.customerId == path.substring(startIndex = path.lastIndexOf("/") + 1).toLong()
        }
    }
}