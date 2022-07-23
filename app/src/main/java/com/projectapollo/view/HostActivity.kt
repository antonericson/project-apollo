package com.projectapollo.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projectapollo.R
import com.projectapollo.utils.WiFiDirectBroadcastReceiver

class HostActivity : AppCompatActivity() {
    val manager: WifiP2pManager? by lazy(LazyThreadSafetyMode.NONE) {
        getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?
    }
    var channel: WifiP2pManager.Channel? = null
    var receiver: BroadcastReceiver? = null
    val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        channel = manager?.initialize(this, mainLooper, null)
        channel?.also {
                channel ->
            receiver = manager?.let { WiFiDirectBroadcastReceiver(it, channel, this) }
        }
    }

    override fun onResume() {
        super.onResume()
        // Register the broadcast receiver with the intent values
        receiver?.also { receiver ->
            registerReceiver(receiver, intentFilter)
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister the broadcast receiver
        receiver?.also { receiver ->
            unregisterReceiver(receiver)
        }
    }
}