package com.projectapollo.view

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo
import android.os.Bundle
import android.util.Log
import com.projectapollo.ApolloApplication
import com.projectapollo.R
import com.projectapollo.utils.WifiP2pBaseActivity
import java.util.logging.Level
import java.util.logging.Logger

class HostActivity : WifiP2pBaseActivity() {

    private val peers = mutableListOf<WifiP2pDevice>()
    private val peerListListener = WifiP2pManager.PeerListListener { peerList ->
        val refreshedPeers = peerList.deviceList
        if (refreshedPeers != peers) {
            peers.clear()
            peers.addAll(refreshedPeers)
            // Perform any other updates needed based on the new list of
            // peers connected to the Wi-Fi P2P network.
        }

        if (peers.isEmpty()) {
            return@PeerListListener
        }
    }
    private val connectionListener = WifiP2pManager.ConnectionInfoListener { info ->
        val groupOwnerAddress: String? = info.groupOwnerAddress.hostAddress
        println("GROUP OWNER $groupOwnerAddress")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_host, null, null)
        startRegistration()
    }

    private fun startRegistration() {
        val record: Map<String, String> = mapOf(
            "listenport" to SERVER_PORT.toString(),
            "buddyname" to "ApolloGroup",
            "available" to "visible"
        )

        val serviceInfo = WifiP2pDnsSdServiceInfo.newInstance("apollogroup", "_presence._tcp", record)

        manager?.addLocalService(channel, serviceInfo, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                // Command successful! Code isn't necessarily needed here,
                // Unless you want to update the UI or add logging statements.
                Log.d("LOG", "REGISTRATION SUCCESS")
            }

            override fun onFailure(reason: Int) {
                Log.d("WARNING", "Failed to add local service - REASON $reason")
            }
        })
    }
}