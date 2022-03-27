package com.blog.seeds

import com.blog.daos.PostDAO
import kotlinx.coroutines.runBlocking

class PostSeeder() {
     fun run() {
         val postDAO: PostDAO = PostDAO()
         runBlocking {
             if(postDAO.all().isEmpty()) {
                 postDAO.create(1,"The drive to develop!", "...it's what keeps me going.")
             }
         }
     }
}

