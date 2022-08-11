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
        get("/") {
            call.respond(ThymeleafContent("index", mapOf("customerList" to DataHolder.getCustomerList())))
        }
        get("new") {
            call.respond(ThymeleafContent("newcustomer", mapOf("customer" to CustomerData())))
        }
        post("new") {
            DataHolder.addCustomer(ObjectMapping.mapResponseToCustomer(call.receiveText()))
            call.respondRedirect("/", false)
        }
        post("edit") {
            val customer = ObjectMapping.mapResponseToCustomer(call.receiveText())
            DataHolder.updateCustomer(customer)
            call.respondRedirect("/", false)
        }
        get("edit/{customerId}") {
            val customer = DataHolder.getCustomerByRequestPath(call.request.path())
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
        get("delete/{customerId}") {
            val customer = DataHolder.getCustomerByRequestPath(call.request.path())
            if (customer != null) {
                DataHolder.removeCustomer(customer)
                call.respondRedirect("/", false)
            }
        }
    }
}
