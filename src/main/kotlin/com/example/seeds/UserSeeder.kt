package com.example.seeds

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.dsls.UserDSL
import kotlinx.coroutines.runBlocking


class UserSeeder() {
     fun run() {
         val userDSL: UserDSL = UserDSL()
         runBlocking {
             if(userDSL.all().isEmpty()) {
//                 val password = "secret"
//                 val bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray())
//                 userDSL.create("johndoe", "johndoe@test.com", bcryptHashString)
             }
         }
     }
}

