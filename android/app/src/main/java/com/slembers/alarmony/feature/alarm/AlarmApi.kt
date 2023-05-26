package com.slembers.alarmony.feature.alarm

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.slembers.alarmony.feature.notification.NotiDto
import com.slembers.alarmony.feature.notification.deleteNoti
import com.slembers.alarmony.feature.notification.saveNoti
import com.slembers.alarmony.network.api.AlarmonyServer
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

object AlarmApi {
    val alarmApi = AlarmonyServer().alarmApi

    fun getAllAlarmsApi(context : Context) {
        val call = alarmApi.getAllAlarms()
        var alarmDtos : List<AlarmDto>? = null
        call.enqueue(object : Callback<getAllAlarmsResponseDto>{
            override fun onResponse(
                call: Call<getAllAlarmsResponseDto>,
                response: Response<getAllAlarmsResponseDto>
            ) {
                if (response.isSuccessful) {
                    val myResponse = response.body()
                    Log.d("myResponse_getAllAlarms", myResponse.toString())
                    if (myResponse!!.alarms != null) { // 서버에 알람 목록이 있으면
                        Log.d("myResponse_getAllAlarms", myResponse!!.alarms.toString())
                        alarmDtos = myResponse.alarms
                        for(alarmDto : AlarmDto in alarmDtos!!) {    // Room 알람 목록 저장
                            if (alarmDto.content == null) { // content UI 미완성 관계로 임시방편
                                alarmDto.content = "알람 설명이 없습니다."
                            }
                            saveAlarm(alarmDto, context)
                        }
                        Log.d("myResponse", "알람을 불러오기 성공.")
                    }
                } else {
                    Log.e("myResponse", "알람목록 응답 안옴")
                }
            }

            override fun onFailure(call: Call<getAllAlarmsResponseDto>, t: Throwable) {
                Log.e("myResponse", "네트워크 오류")
            }
        })
    }
    fun recordAlarmApi(datetime : String, alarmId : Long) {
        val call = alarmApi.recordAlarm(datetime, alarmId)
        call.enqueue(object : Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("myResponse", response.toString())
                if (response.isSuccessful) {
                    Log.d("myResponse", "알람 기록성공.")
                } else {
                    Log.e("myResponse", "알람 기록실패.")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("myResponse", "알람 기록 네트워크 오류")
            }
        })
    }

    fun snoozeMessageApi(message : String, alarmId : Long) {
        val call = alarmApi.snoozeMessage(message, alarmId)
        call.enqueue(object : Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("myResponse", "스누즈 메세지 전송 : ${response.toString()}")
                if (response.isSuccessful) {
                    Log.d("myResponse", "메세지 전송성공.")
                } else {
                    Log.e("myResponse", "메세지 전송실패.")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("myResponse", "스누즈 메세지 네트워크 오류")
            }
        })
    }
}