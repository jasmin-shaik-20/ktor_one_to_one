package com.example.plugins

import com.example.dao.User

interface UsersInterface {
    suspend fun createUser(id: Int, username: String, password: String): User?
    suspend fun selectUser(id:Int):User?

}