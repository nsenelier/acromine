@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.acromine

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.acromine.repository.NetworkModule
import com.example.acromine.repository.Repository
import com.example.acromine.viewmodel.AcromineViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class ViewModelTest{

    private lateinit var repository: Repository
    private lateinit var viewModel: AcromineViewModel


    @Before
    fun setUp() {
        repository = Repository(NetworkModule.provideNetwork(NetworkModule.provideRetrofit()))
        viewModel = AcromineViewModel(repository)
    }


    @Test
    fun testViewModel() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            viewModel.searchAcronym("ab")
            advanceUntilIdle() //precaution

            print(viewModel.lDisplayList.value)
            viewModel.lDisplayList.value?.let {
                assertEquals(it[0].acronyms, "AB")
            }
        } finally {
            Dispatchers.resetMain()
        }

    }
}