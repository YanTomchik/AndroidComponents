package com.example.numberpi

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.math.BigDecimal
import java.math.RoundingMode


class NumberPi : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val x = intent.extras?.getInt("num") ?: 0
        val numPi = BigDecimal(3.14159265359).setScale(x , RoundingMode.HALF_EVEN)
        Intent("sendBroadcast").also { broadcastIntent ->
            broadcastIntent.putExtra("numberPi", numPi)
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
        }
        Log.w(TAG,"$numPi")

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}
