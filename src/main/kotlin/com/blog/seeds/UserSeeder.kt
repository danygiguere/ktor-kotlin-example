package com.blog.seeds

import com.blog.dsls.UserDSL
import kotlinx.coroutines.runBlocking

class UserSeeder() {
     fun run() {
         val userDSL: UserDSL = UserDSL()
         runBlocking {
             if(userDSL.all().isEmpty()) {
                 userDSL.create("johndoe", "johndoe@test.com", "secret")
             }
         }
     }
}

