package com.team3.showbee.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team3.showbee.data.entity.*
import com.team3.showbee.data.network.NetworkResponse
import com.team3.showbee.data.repository.schedule.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    val repository: ScheduleRepository
) : ViewModel() {
    private val _msg = MutableLiveData<Event<String>>()
    private val _email = MutableLiveData<String>()
    val msg : LiveData<Event<String>> = _msg
    val email:LiveData<String>
        get() = _email

    private val _category = MutableLiveData<Event<Map<String,List<String>>>>()
    val category : LiveData<Event<Map<String,List<String>>>> = _category

    private val _list = MutableLiveData<Event<MutableMap<String, MutableList<ScheduleContentModel>>>>()
    val list : LiveData<Event<MutableMap<String, MutableList<ScheduleContentModel>>>> = _list

    private val _schedule = MutableLiveData<Event<Schedule>>()
    val schedule : LiveData<Event<Schedule>> = _schedule

    private val _total = MutableLiveData<Event<List<Long>>>()
    val total : LiveData<Event<List<Long>>> = _total

    init {
        _email.value = ""
    }
    fun inviteUser(email: String) {
        if(validation(email)) {
            viewModelScope.launch {
                val email = email
                val response:NetworkResponse<InviteeResponse,ErrorResponse> = repository.inviteUser(email)
                when(response) {
                    is NetworkResponse.Success -> {
                        Log.d("dkdkd22", "djfljwife")
                        _msg.value = (Event(response.body.msg))
                        val message = response.body.msg
                        val inviteeEmail = response.body.data.email
                        Log.d("TAG", "inviteUser: ${_msg.value}")
                        Log.d("dkdkd33", "${inviteeEmail}")
                        _email.value = inviteeEmail
                        existUser(message, inviteeEmail)
                        Log.d(TAG, "inviteUser: exist user 실행??")
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
    }
    fun createS(stitle:String, content:String, price:Int, date:String, cycle:Int, shared:Boolean, participant:ArrayList<String>, inoutcome:Boolean,category:String) {
        viewModelScope.launch {
            val schedule = Schedule(stitle = stitle, content= content, price = price, date = date, cycle = cycle, shared = shared, participant = participant, inoutcome = inoutcome, category = category)
            val response:NetworkResponse<Int, ErrorResponse> = repository.createSchedule(schedule)

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

    fun getSchedule(sid: Long) {
        viewModelScope.launch {
            val response = repository.getSchedule(sid)

            when(response) {
                is NetworkResponse.Success -> {
                    _schedule.postValue(Event(response.body))
                    //var price = response.body.price.toString()
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

    fun getCategory(nowDate:String) {
        viewModelScope.launch {
            val response = repository.getCategoryIcon(nowDate = nowDate)

            when(response) {
                is NetworkResponse.Success -> {
                    _category.postValue(Event(response.body))
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

    fun getSList(nowDate: String) {
        viewModelScope.launch {
            val response = repository.getSList(nowDate = nowDate)

            when(response) {
                is NetworkResponse.Success -> {
                    _list.postValue(Event(response.body))
                    Log.d("response", "getSList: ${response.body}")
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

    fun getSMonthlyTotal(nowDate: String) {
        viewModelScope.launch {
            val response = repository.getSMonthlyTotal(nowDate = nowDate)

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

    fun existUser(result:String, email: String) {
        Log.d(TAG, "existUser: 첫 부분")
        if(result == "성공하였습니다.") {
            Log.d(TAG, "existUser: ${result}")

            val email = email

            inviteeList.add(Pair(email, "sdfksjf"))
            Log.d("텍스트띄우기", "onCreate: ${inviteeList.size} + ${inviteeList[0].first} + ${inviteeList[0].second}")
            Log.d(TAG, "existUser: ${inviteeList}")
        }
    }

    fun updateSchedule(sid:Long, stitle:String, content:String, price:Int, date:String, cycle:Int, shared:Boolean, participant:ArrayList<String>, inoutcome:Boolean,category:String) {
        Log.d(TAG, "updateSchedule: update!")
        viewModelScope.launch {
            val schedule = Schedule(sid = sid, stitle = stitle, content= content, price = price, date = date, cycle = cycle, shared = shared, participant = participant, inoutcome = inoutcome, category = category)
            val response = repository.createSchedule(schedule)

            when(response) {
                is NetworkResponse.Success -> {
                    _msg.postValue(Event((response.body.toString())))
                    Log.d(TAG, "updateSchedule: isSuccess????")
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

    fun deleteSchedule(sid: Long) {
        viewModelScope.launch {
            val response = repository.deleteSchedule(sid)

            when(response) {
                is NetworkResponse.Success -> {
                    _msg.postValue(Event((response.body.msg)))
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

    private fun validation(email: String): Boolean {
        if (email.isEmpty()) {
            _msg.postValue(Event("이메일을 입력해주세요"))
            return false
        }
        return true
    }

    private fun postValueEvent(value : Int) {
        val msgArrayList = arrayOf("Api 오류 : 실패했습니다.",
            "서버 오류 : 실패했습니다.",
            "존재하지 않는 사용자입니다."
        )

        when(value) {
            0 -> _msg.postValue(Event(msgArrayList[0]))
            1 -> _msg.postValue(Event(msgArrayList[1]))
            2 -> _msg.postValue(Event(msgArrayList[2]))
        }
    }
}

