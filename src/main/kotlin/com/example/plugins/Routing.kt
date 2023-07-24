package com.example.plugins

import com.example.dao.Profile
import com.example.dao.User
import com.example.dao.UserProfile
import com.example.dao.Users
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.configureRouting() {
    routing {
        route("/user") {
            val usersInterfaceImpl = UsersInterfaceImpl()
            get {
                val query = transaction {
                    Users.selectAll().map {
                        mapOf("id" to it[Users.id], "username" to it[Users.username], "password" to it[Users.password])
                    }

                }
                call.respond(query)
            }


            post {
                val userdetails = call.receive<User>()
                val user = usersInterfaceImpl.createUser(userdetails.id, userdetails.username, userdetails.password)
                    ?: return@post call.respondText("Unable top create em[ployee")
                call.respond(user)

            }

            get("/{id?}") {
                val id=call.parameters["id"]?:return@get throw Throwable()
                val user1 = usersInterfaceImpl.selectUser(id.toInt())
                if(user1!=null){
                    call.respond(user1)
                }
                else{
                    call.respond("No user found")
                }

            }

        }

        route("/userprofile") {
            val profileInterfaceImpl = ProfileInterfaceImpl()
            get {
                val query1 = transaction {
                    Profile.selectAll().map {
                        mapOf("id" to Profile.profileid, "email" to Profile.email, "age" to Profile.age)
                    }
                }
                call.respond(query1)
            }

            post {
                val profiledetails = call.receive<UserProfile>()
                val profile = profileInterfaceImpl.createUserProfile(
                    profiledetails.profileid,
                    profiledetails.userid,
                    profiledetails.email,
                    profiledetails.age
                ) ?: return@post call.respondText("unable to create userprofile")
                call.respond(profile)
            }


            get("/{id?}"){
                val profileid=call.parameters["id"]?:return@get throw Throwable()
                val profile1=profileInterfaceImpl.getUserProfile(profileid.toInt())
                if(profile1!=null) {
                    call.respond(profile1)
                }
                else{
                    call.respond("No user profile")
                }

            }
        }
    }


}


