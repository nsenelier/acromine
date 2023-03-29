package com.example.acromine

import com.example.acromine.repository.NetworkModule
import com.example.acromine.repository.Repository
import io.mockk.InternalPlatformDsl.toStr
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NetworkTest {
    private lateinit var repository: Repository
    @Before
    fun setUp() {
        repository = Repository(NetworkModule.provideNetwork(NetworkModule.provideRetrofit()))
    }
    @Test
    fun getAcronym() {
        runBlocking {
            val response = repository.getAcronym("ab")
            print(response.toStr())
            assertEquals(response[0].sf, "AB")
        }
    }
    @Test
    fun getLongForm() {
        runBlocking {
            val response = repository.getLongForm("antibody")
            assertEquals(response[0].sf, "AB")
        }
    }

}