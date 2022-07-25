package com.projectapollo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.p2p.WifiP2pManager

class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: WifiP2pBaseActivity,
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // Check to see if Wi-Fi is enabled and notify appropriate activity
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                when (state) {
                    WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
                        // Wifi P2P is enabled
                        println("P2P enabled")
                    }
                    else -> {
                        // Wi-Fi P2P is not enabled
                        println("P2P NOT ENABLED")
                    }
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                // Call WifiP2pManager.requestPeers() to get a list of current peers
               // manager.requestPeers(channel, peerListListener)
                println("PEERS CHANGED")
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                // Respond to new connection or disconnections
                println("NEW CONNECT OR DISCONNECT")
                manager.let { manager ->
                    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    if (connectivityManager.activeNetwork != null) {
                        // We are connected with the other device, request connection
                        // info to find group owner IP
                        //manager.requestConnectionInfo(channel, connectionListener)
                    }
                }
            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                // Respond to this device's wifi state changing
                println("WIFI STATE CHANGE")
            }
        }
    }
}