package com.projectapollo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class WifiP2pBaseActivity : AppCompatActivity() {
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

    fun onCreate(savedInstanceState: Bundle?, layoutResID: Int, peerListListener: WifiP2pManager.PeerListListener?, connectionListener: WifiP2pManager.ConnectionInfoListener?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResID)

        channel = manager?.initialize(this, mainLooper, null)
        if (peerListListener != null && connectionListener != null) {
            channel?.also { channel ->
                receiver = manager?.let {
                    WiFiDirectBroadcastReceiver(
                        it,
                        channel,
                        this,
                        peerListListener,
                        connectionListener
                    )
                }
            }
        }

        // Register the broadcast receiver with the intent values
        receiver?.also { receiver ->
            registerReceiver(receiver, intentFilter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        receiver?.also { receiver ->
            unregisterReceiver(receiver)
        }
        disconnectFromSession()
    }

    private fun disconnectFromSession() {
        manager?.requestGroupInfo(channel, WifiP2pManager.GroupInfoListener { group ->
            if (group != null && manager != null && channel != null) {
                manager!!.removeGroup(channel, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() {
                        println("removeGroup SUCCESS")
                    }

                    override fun onFailure(reason: Int) {
                        println("removeGroup FAIL - $reason")
                    }
                })

            }
        })
    }
}