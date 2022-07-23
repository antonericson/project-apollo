package com.projectapollo.view

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.adamratzman.spotify.auth.pkce.startSpotifyClientPkceLoginActivity
import com.projectapollo.R
import com.projectapollo.utils.SpotifyPkceLoginActivityImpl
import com.projectapollo.utils.pkceClassBackTo

class LoginActivity : AppCompatActivity() {

    private val registerPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted: Boolean ->
        if (isGranted) {
            println("PERMISSION GRANTED")
        } else {
            println("PERMISSION DENIED")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                println("PERMISSION ALREADY GRANTED")
            }
            else -> {
                registerPermissionsLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            if (isPermissionGranted()) {
                println("PERMISSION GRANTED MOVING TO JOIN OR HOST")
                pkceClassBackTo = JoinOrHostActivity::class.java
                startSpotifyClientPkceLoginActivity(SpotifyPkceLoginActivityImpl::class.java)
            } else {
                println("PERMISSIONS NOT GRANTED CHECK AGAIN")
            }

        }

        val skipLoginButton = findViewById<Button>(R.id.skipLoginButton)
        skipLoginButton.setOnClickListener {
            if (isPermissionGranted()) {
                println("PERMISSION GRANTED MOVING TO JOIN OR HOST")
                val intent = Intent(this, JoinOrHostActivity::class.java)
                startActivity(intent)
            } else {
                println("PERMISSIONS NOT GRANTED CHECK AGAIN")
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}