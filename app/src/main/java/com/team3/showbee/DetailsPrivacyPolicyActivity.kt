package com.team3.showbee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView

class DetailsPrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_privacy_policy)

        val text = findViewById<TextView>(R.id.details_PrivacyPolicy)
        text.movementMethod = ScrollingMovementMethod.getInstance()
    }
}