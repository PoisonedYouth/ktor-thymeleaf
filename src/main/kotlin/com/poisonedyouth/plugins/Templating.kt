package com.poisonedyouth.plugins

import com.poisonedyouth.CustomerData
import com.poisonedyouth.DataHolder
import com.poisonedyouth.ObjectMapping
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.request.path
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver


fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/thymeleaf/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }

    routing {
        get("main") {
            call.respond(ThymeleafContent("index", mapOf("customerList" to DataHolder.customerList)))
        }

        get("new") {
            call.respond(ThymeleafContent("newcustomer", mapOf("customer" to CustomerData())))
        }
        post("new") {
            DataHolder.customerList.add(ObjectMapping.mapResponseToCustomer(call.receiveText()))
            call.respondRedirect("/main", false)
        }
        post("edit") {
            val customer = ObjectMapping.mapResponseToCustomer(call.receiveText())
            DataHolder.customerList.removeIf { it.customerId == customer.customerId }
            DataHolder.customerList.add(customer)
            call.respondRedirect("/main", false)
        }
        get("edit/{customerId}") {
            val path = call.request.path()
            val customer =
                DataHolder.customerList.firstOrNull {
                    it.customerId == path.substring(startIndex = path.lastIndexOf("/") + 1).toLong()
                }
            if (customer != null) {
                call.respond(
                    ThymeleafContent(
                        "editcustomer", mapOf(
                            "customer" to ObjectMapping.mapCustomerToCustomerData(customer)
                        )
                    )
                )
            }
        }
    }
}
