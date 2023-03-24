package com.example.startstopwatch

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToInt

class StopWatchService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    private val timer = Timer()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(CURRENT_TIME,0.0)
        timer.scheduleAtFixedRate(StopWatchTimerTask(time),0,1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }
    companion object{
        const val CURRENT_TIME = "current time"
        const val UPDATE_TIME = "updated time"
    }

    private inner class StopWatchTimerTask(private var time:Double):TimerTask(){
        override fun run() {
            val intent = Intent(UPDATE_TIME)
            time++
            intent.putExtra(CURRENT_TIME,time)
            sendBroadcast(intent)
        }
    }

}