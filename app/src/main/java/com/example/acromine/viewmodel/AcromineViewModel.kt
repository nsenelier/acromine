package com.example.acromine.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acromine.model.DisplayData
import com.example.acromine.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@SuppressLint("CheckResult")
@HiltViewModel
class AcromineViewModel @Inject constructor(
    private val repository: Repository
    ): ViewModel() {

    var inputEmitter : ObservableEmitter<CharSequence>? = null
    var input : Observable<CharSequence> = Observable.create { emitter ->
        inputEmitter = emitter
    }

    private val mDisplayList : MutableLiveData<List<DisplayData>> by lazy{
        MutableLiveData<List<DisplayData>>()
    }

    val lDisplayList : LiveData<List<DisplayData>> = mDisplayList

    fun searchAcronym(text : String){
        viewModelScope.launch {
            val response = repository.getAcronym(text)
            val displayList = response.map {
                val first = it.sf ?: ""
                val secondList = it.lfs.map { "${it.lf}(${it.freq})"}
                DisplayData(first, secondList)
            }
            mDisplayList.value = displayList
            print(lDisplayList.value)
        }

    }

    init{
        input
            .debounce(500, TimeUnit.MILLISECONDS) // debounce for 500ms
            .subscribe { text: CharSequence? ->
                searchAcronym(text.toString())
            }
    }
}