package com.example.li.myviewdemos

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    val handler:Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timer().schedule(object : TimerTask() {
            var vecter = 1
            override fun run() {
                if(pizzview.currentPiece >= pizzview.totalPiece){
                    vecter = -1
                }else if(pizzview.currentPiece <= 0){
                    vecter = 1
                }
                val currentpice = pizzview.currentPiece + vecter
                handler.post({ pizzview.setCurrentPice(currentpice)})
            }
        },500,500)
    }
}
