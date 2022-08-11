package com.poisonedyouth

import io.ktor.server.application.call
import io.ktor.server.request.path
import java.time.LocalDate

object DataHolder {

    fun getCustomerList() = CustomerEntity.findAll()

    fun removeCustomer(customer: Customer) = CustomerEntity.removeCustomer(customer)

    fun addCustomer(customer: Customer) = CustomerEntity.addCustomer(customer)

    fun updateCustomer(customer: Customer) = CustomerEntity.updateCustomer(customer)


    fun getCustomerByRequestPath(path: String): Customer? {
        return CustomerEntity.findAll().firstOrNull {
            it.customerId == path.substring(startIndex = path.lastIndexOf("/") + 1).toLong()
        }
    }
}