package com.israis007.contactslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openRightPositionSample(view: View?) {
        val intent = Intent(this, RightPositionActivity::class.java)
        startActivity(intent)
    }

    fun openLeftPositionSample(view: View?) {
        val intent = Intent(this, LeftPositionActivity::class.java)
        startActivity(intent)
    }

    fun openCustomIndexSample(view: View?) {
        val intent = Intent(this, CustomIndexActivity::class.java)
        startActivity(intent)
    }
}
