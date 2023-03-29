package com.example.acromine.repository

import com.example.acromine.model.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

//http://www.nactem.ac.uk/software/acromine/dictionary.py?lf=antibody

interface NetworkApi {

    @GET("dictionary.py")
    suspend fun getAcronym(
        @QueryMap
    parameter: Map<String, String>
    ): List<Response>

}