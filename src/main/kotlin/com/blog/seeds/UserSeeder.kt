package com.blog.seeds

import com.blog.daos.UserDAO
import kotlinx.coroutines.runBlocking

class UserSeeder() {
     fun run() {
         val userDAO: UserDAO = UserDAO()
         runBlocking {
             if(userDAO.all().isEmpty()) {
                 userDAO.create("johndoe", "johndoe@test.com", "secret")
             }
         }
     }
}

