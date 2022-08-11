package com.poisonedyouth

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction

object CustomerTable : LongIdTable(name = "customer", columnName = "id") {
    val customerId = long("customer_id")
    val firstName = varchar("first_name", 255)
    val lastName = varchar("last_name", 255)
    val birthdate = date("birth_date")
    val address = reference("address_id", AddressTable)
}

class CustomerEntity(id: EntityID<Long>) : LongEntity(id) {
    var firstName by CustomerTable.firstName
    var lastName by CustomerTable.lastName
    var birthdate by CustomerTable.birthdate
    var customerId by CustomerTable.customerId
    var addressEntity by AddressEntity referencedOn CustomerTable.address

    companion object : LongEntityClass<CustomerEntity>(CustomerTable) {
        fun updateCustomer(customer: Customer) = transaction {
            val entity = CustomerEntity.find { CustomerTable.customerId eq customer.customerId }.first()
            entity.customerId = customer.customerId
            entity.firstName = customer.firstName
            entity.lastName = customer.lastName
            entity.birthdate = customer.birthdate
            entity.addressEntity = customer.address.let {
                AddressEntity.new(customer.address.id) {
                    street = customer.address.street
                    streetNumber = customer.address.streetNumber
                    zipCode = customer.address.zipCode
                    city = customer.address.city
                }
            }
        }


        fun addCustomer(customer: Customer) = transaction {
            CustomerEntity.new {
                customerId = customer.customerId
                firstName = customer.firstName
                lastName = customer.lastName
                birthdate = customer.birthdate
                addressEntity = AddressEntity.new {
                    street = customer.address.street
                    streetNumber = customer.address.streetNumber
                    zipCode = customer.address.zipCode
                    city = customer.address.city
                }
            }
        }

        fun removeCustomer(customer: Customer) =
            transaction { CustomerEntity.find { CustomerTable.customerId eq customer.customerId }.first().delete() }

        fun findAll(): List<Customer> = transaction {
            CustomerEntity.all().map {
                Customer(
                    customerId = it.customerId,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    birthdate = it.birthdate,
                    address = Address(
                        id = it.addressEntity.id.value,
                        street = it.addressEntity.street,
                        streetNumber = it.addressEntity.streetNumber,
                        zipCode = it.addressEntity.zipCode,
                        city = it.addressEntity.city

                    )
                )
            }
        }

    }
}

object AddressTable : LongIdTable(name = "address", columnName = "id") {
    val street = varchar("street", 255)
    val streetNumber = varchar("street_number", 255)
    val zipCode = integer("zip_code")
    val city = varchar("city", 255)
}

class AddressEntity(id: EntityID<Long>) : LongEntity(id) {
    var street by AddressTable.street
    var streetNumber by AddressTable.streetNumber
    var zipCode by AddressTable.zipCode
    var city by AddressTable.city

    companion object : LongEntityClass<AddressEntity>(AddressTable)
}