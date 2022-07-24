package com.projectapollo.view

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import com.projectapollo.R
import com.projectapollo.utils.WifiP2pBaseActivity

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
        super.onCreate(savedInstanceState, R.layout.activity_host, peerListListener, connectionListener)

        manager?.createGroup(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                println("GROUP CREATED READY FOR PEERS")
            }

            override fun onFailure(reason: Int) {
                println("GROUP CREATE FAIL")
            }

        })
    }
}