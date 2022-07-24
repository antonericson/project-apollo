package com.projectapollo.view

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectapollo.R
import com.projectapollo.utils.WifiP2pBaseActivity

class JoinActivity : WifiP2pBaseActivity() {

    private val peers = mutableListOf<WifiP2pDevice>()
    private lateinit var deviceListRecyclerView: RecyclerView;

    private val peerListListener = WifiP2pManager.PeerListListener { peerList ->
        val refreshedPeers = peerList.deviceList
        if (refreshedPeers != peers) {
            peers.clear()
            peers.addAll(refreshedPeers)

            // If an AdapterView is backed by this data, notify it
            // of the change. For instance, if you have a ListView of
            // available peers, trigger an update.
            (deviceListRecyclerView.adapter as WifiPeerListAdapter).notifyDataSetChanged()

            // Perform any other updates needed based on the new list of
            // peers connected to the Wi-Fi P2P network.
        }

        if (peers.isEmpty()) {
            return@PeerListListener
        }
    }

    private val connectionListener = WifiP2pManager.ConnectionInfoListener { info ->
        var groupOwnerAddress: String = ""
        if (info.groupOwnerAddress != null){
            groupOwnerAddress = info.groupOwnerAddress.hostAddress ?: "none"
        }
        println("GROUP OWNER $groupOwnerAddress")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_join, peerListListener, connectionListener)
        deviceListRecyclerView = this.findViewById<RecyclerView>(R.id.deviceList)



        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        deviceListRecyclerView.layoutManager = linearLayoutManager
        deviceListRecyclerView.adapter = WifiPeerListAdapter(peers, manager, channel)

        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {

            override fun onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank. Code for peer discovery goes in the
                // onReceive method, detailed below.
                println("DISCOVER ENABLED SUCCESS")
            }

            override fun onFailure(reasonCode: Int) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
                println("FAIL ON INIT DISCOVER")
            }
        })
    }
}