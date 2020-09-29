package com.example.numberpi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager


const val TAG = "TomchikYM"
const val KEY_NAME = "ValueKey"
class MainActivity : AppCompatActivity() {
   private var x = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState !=null) {
            with(savedInstanceState){
                x = getInt(KEY_NAME)
            }
        }
        x++
        Log.w(TAG,"$x")

        Intent(this, NumberPi::class.java).also { intent ->
            intent.putExtra("num",x)
            startService(intent)

        }
        LocalBroadcastManager.getInstance(this).registerReceiver(numberReceiver, IntentFilter("sendBroadcast"))
    }

    private val numberReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val number = intent.getIntExtra("numberPi", 0)
            Log.d(TAG, "number: $number")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.run{
            putInt(KEY_NAME,x)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(numberReceiver);
        Intent(this, NumberPi::class.java).also { intent ->
            stopService(intent)
        }
    }
}