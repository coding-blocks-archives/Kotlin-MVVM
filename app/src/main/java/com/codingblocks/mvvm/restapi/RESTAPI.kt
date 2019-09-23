package com.codingblocks.mvvm.restapi

import retrofit2.Response
import retrofit2.http.GET


interface RESTAPI {

    @get:GET("users/aggarwalpulkit596")
    val getMe: Response<List<String>>


    @get:GET("users/aggarwalpulkit596/repos")
    val getrepos: Response<List<String>>


}