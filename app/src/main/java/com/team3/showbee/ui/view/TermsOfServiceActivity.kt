package com.team3.showbee.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.team3.showbee.DetailsPrivacyPolicyActivity
import com.team3.showbee.DetailsTermsOfServiceActivity
import com.team3.showbee.R

class TermsOfServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)

        val detailsService = findViewById<TextView>(R.id.details_terms_of_service)
        detailsService.setOnClickListener {
            val intent = Intent(this, DetailsTermsOfServiceActivity::class.java)
            startActivity(intent)
        }

        val detailsPrivacy = findViewById<TextView>(R.id.details_privacy_policy)
        detailsPrivacy.setOnClickListener {
            val intent = Intent(this, DetailsPrivacyPolicyActivity::class.java)
            startActivity(intent)
        }
    }
}