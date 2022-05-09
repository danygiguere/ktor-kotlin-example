package com.blog.seeds

import com.blog.dsls.PostDSL
import kotlinx.coroutines.runBlocking

class PostSeeder() {
     fun run() {
         val postDSL: PostDSL = PostDSL()
         runBlocking {
             if(postDSL.all().isEmpty()) {
                 postDSL.create(1,"The drive to develop!", "...it's what keeps me going.")
             }
         }
     }
}

