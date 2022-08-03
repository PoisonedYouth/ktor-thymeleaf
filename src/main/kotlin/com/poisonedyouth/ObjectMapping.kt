package com.poisonedyouth

import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.random.Random
import java.time.LocalDate

object ObjectMapping {

    fun mapResponseToCustomer(responseText: String): Customer {
        val newMap: HashMap<String, Any> = HashMap()
        responseText.split("&")
            .forEach { k -> newMap[k.split("=")[0]] = k.split("=")[1] }

        val objectMapper = ObjectMapper()
        val customerDto = objectMapper.convertValue(newMap, CustomerData::class.java)

        return Customer(
            customerId = Random.nextLong(11111, 99999),
            firstName = customerDto.firstName,
            lastName = customerDto.lastName,
            birthdate = LocalDate.parse(customerDto.birthdate),
            address = Address(
                street = customerDto.street,
                streetNumber = customerDto.streetNumber,
                zipCode = customerDto.zipCode,
                city = customerDto.city,
            )
        )
    }
}