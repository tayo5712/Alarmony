package com.slembers.alarmony.feature.notification

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.slembers.alarmony.feature.alarm.Alarm
import com.slembers.alarmony.feature.alarm.AlarmDto

class NotiDto (
    @SerializedName("id") val notiId : Long,
    @SerializedName("profileImg") var profileImg : String,
    @SerializedName("content") val content : String,
    @SerializedName("type") val type : String
)  {
    companion object {
        fun toDto(noti: Noti): NotiDto {
            val notiDto = NotiDto(
                noti.notiId,
                noti.profileImg,
                noti.content,
                noti.type
            )
            return notiDto
        }
    }
}