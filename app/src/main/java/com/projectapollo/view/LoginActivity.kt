package com.projectapollo.view

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.adamratzman.spotify.auth.pkce.startSpotifyClientPkceLoginActivity
import com.projectapollo.R
import com.projectapollo.utils.SpotifyPkceLoginActivityImpl
import com.projectapollo.utils.pkceClassBackTo
import java.util.jar.Manifest

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                isGranted: Boolean ->
            if (isGranted) {
                println("PERMISSION GRANTED")
            } else {
                println("PERMISSION DENIED")
            }
        }

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                println("PERMISSION ALREADY GRANTED")
            }
            else -> {
                registerPermissionsLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

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