package com.projectapollo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
    }
}