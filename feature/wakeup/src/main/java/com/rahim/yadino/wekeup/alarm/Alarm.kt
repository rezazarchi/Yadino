package com.rahim.yadino.wekeup.alarm

import android.content.Context

interface Alarm {
    fun cancelAlarm(context: Context, idAlarm: Long?)
}