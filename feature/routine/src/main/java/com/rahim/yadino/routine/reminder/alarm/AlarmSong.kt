package com.rahim.yadino.routine.reminder.alarm

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Vibrator

interface AlarmSong {
    //    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//    vibrator.vibrate(4000)
//
//    Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show()
//    var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
//    if (alarmUri == null) {
//        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//    }
//
//    val ringtone = RingtoneManager.getRingtone(context, alarmUri)
//
//    // play ringtone
//
//    // play ringtone
//    ringtone.play()
//    fun playRingtone(context:Context,alarmId: Long?,ringUri: Uri?)
    fun playRingtone(context:Context,alarmId: Long?)
    fun stopRingtone(ringtone: Ringtone?,context:Context,alarmId: Long?)
}