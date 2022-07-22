package com.projectapollo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.login_button);
        loginButton.setOnClickListener {
            val intent = Intent(this, JoinOrHostActivity::class.java);
            startActivity(intent);
        }
    }
}