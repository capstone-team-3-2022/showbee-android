package com.team3.showbee.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team3.showbee.data.entity.Event
import com.team3.showbee.data.entity.Financial
import com.team3.showbee.data.network.NetworkResponse
import com.team3.showbee.data.repository.financial.FinancialRepository
import com.team3.showbee.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinancialViewModel @Inject constructor(
    val repository: FinancialRepository
) : ViewModel() {
    private val _msg = MutableLiveData<Event<String>>()
    val msg : LiveData<Event<String>> = _msg

    private val _list = MutableLiveData<Event<Map<String,List<Long>>>>()
    val list : LiveData<Event<Map<String,List<Long>>>> = _list

    private val _total = MutableLiveData<Event<List<Long>>>()
    val total : LiveData<Event<List<Long>>> = _total

    fun create(date: String, content: String, price: Int, category: String, bank: String, memo: String, inoutcome: Boolean) {
        viewModelScope.launch {
            val financial = Financial(date, content, price, category, bank, memo, inoutcome)
            val response = repository.createFinancial(financial)

            when(response) {
                is NetworkResponse.Success -> {
                    _msg.postValue(Event(response.body.toString()))
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2)
                }
            }
        }
    }

    fun getMonthlyTotal(nowDate:String) {
        viewModelScope.launch {
            val response = repository.getMonthlyTotal(nowDate = nowDate)

            when(response) {
                is NetworkResponse.Success -> {
                    _total.postValue(Event(response.body))
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2)
                }
            }
        }
    }

    fun getMonthly(nowDate:String) {
        viewModelScope.launch {
            val response = repository.getMonthly(nowDate = nowDate)

            when(response) {
                is NetworkResponse.Success -> {
                    _list.postValue(Event(response.body))
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2)
                }
            }
        }
    }

    fun delete(fid: Int) {
        viewModelScope.launch {
            val response = repository.deleteFinancial(fid)

            when(response) {
                is NetworkResponse.Success -> {
                    _msg.postValue(Event(response.body.msg))
                }
                is NetworkResponse.ApiError -> {
                    postValueEvent(0)
                }
                is NetworkResponse.NetworkError -> {
                    postValueEvent(1)
                }
                is NetworkResponse.UnknownError -> {
                    postValueEvent(2)
                }
            }
        }
    }

    private fun postValueEvent(value : Int) {
        val msgArrayList = arrayOf("Api 오류 : 실패했습니다.",
            "서버 오류 : 실패했습니다.",
            "알 수 없는 오류 : 실패했습니다."
        )

        when(value) {
            0 -> _msg.postValue(Event(msgArrayList[0]))
            1 -> _msg.postValue(Event(msgArrayList[1]))
            2 -> _msg.postValue(Event(msgArrayList[2]))
        }
    }
}