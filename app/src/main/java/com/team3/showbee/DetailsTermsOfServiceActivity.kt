package com.team3.showbee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView

class DetailsTermsOfServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_terms_of_service)

        val text = findViewById<TextView>(R.id.details)
        text.movementMethod = ScrollingMovementMethod.getInstance()
    }


}