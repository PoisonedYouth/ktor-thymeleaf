package com.poisonedyouth

import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDate

object ObjectMapping {

    fun mapResponseToCustomer(responseText: String): Customer {
        val newMap: HashMap<String, Any> = HashMap()
        responseText.split("&")
            .forEach { k -> newMap[k.split("=")[0]] = k.split("=")[1] }

        val objectMapper = ObjectMapper()
        val customerDto = objectMapper.convertValue(newMap, CustomerData::class.java)

        return Customer(
            customerId = customerDto.customerId,
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

    fun mapCustomerToCustomerData(customer: Customer) = CustomerData(
        customerId = customer.customerId,
        firstName = customer.firstName,
        lastName = customer.lastName,
        birthdate = customer.birthdate.toString(),
        street = customer.address.street,
        streetNumber = customer.address.streetNumber,
        zipCode = customer.address.zipCode,
        city = customer.address.city
    )
}