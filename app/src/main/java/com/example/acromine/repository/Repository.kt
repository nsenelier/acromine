package com.example.acromine.repository

class Repository(private val api : NetworkApi) {

    suspend fun getAcronym(sf : String) = api.getAcronym(mapOf("sf" to sf))
    suspend fun getLongForm(lf : String) = api.getAcronym(mapOf("lf" to lf))

    companion object{
       const val BASE_URL = "http://www.nactem.ac.uk/software/acromine/"
    }

}