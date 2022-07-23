package com.projectapollo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView
import com.adamratzman.spotify.auth.pkce.startSpotifyClientPkceLoginActivity
import com.projectapollo.R
import com.projectapollo.utils.SpotifyPkceLoginActivityImpl
import com.projectapollo.utils.pkceClassBackTo

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            pkceClassBackTo = JoinOrHostActivity::class.java
            startSpotifyClientPkceLoginActivity(SpotifyPkceLoginActivityImpl::class.java)
        }

        val skipLoginButton = findViewById<Button>(R.id.skipLoginButton)
        skipLoginButton.setOnClickListener {
            val intent = Intent(this, JoinOrHostActivity::class.java)
            startActivity(intent)
        }
    }
}