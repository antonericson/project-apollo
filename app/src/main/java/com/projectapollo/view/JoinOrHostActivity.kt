package com.projectapollo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.projectapollo.R

class JoinOrHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_or_host)

        val hostSessionCard = findViewById<CardView>(R.id.hostSessionCard)
        hostSessionCard.setOnClickListener {
            val intent = Intent(this, HostActivity::class.java)
            startActivity(intent)
        }

        val joinSessionCard = findViewById<CardView>(R.id.joinSessionCard)
        joinSessionCard.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}