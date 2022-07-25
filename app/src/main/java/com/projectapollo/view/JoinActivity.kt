package com.projectapollo.view

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectapollo.R
import com.projectapollo.utils.WifiP2pBaseActivity

class JoinActivity : WifiP2pBaseActivity() {

    private val buddiesMap = mutableMapOf<String, String>()
    private val buddiesNames = mutableListOf<String>()
    private lateinit var deviceListRecyclerView: RecyclerView;

    private val connectionListener = WifiP2pManager.ConnectionInfoListener { info ->
        var groupOwnerAddress: String = ""
        if (info.groupOwnerAddress != null){
            groupOwnerAddress = info.groupOwnerAddress.hostAddress ?: "none"
        }
        println("GROUP OWNER $groupOwnerAddress")
    }

    val txtListener = WifiP2pManager.DnsSdTxtRecordListener { fullDomain, record, device ->
        Log.d("DEBUG", "DnsSdTxtRecord available -$record")
        record["buddyname"]?.also {
            buddiesMap[device.deviceAddress] = it
        }
    }

    val servListener = WifiP2pManager.DnsSdServiceResponseListener { instanceName, registrationType, resourceType ->
        // Update the device name with the human-friendly version from
        // the DnsTxtRecord, assuming one arrived.
        resourceType.deviceName = buddiesMap[resourceType.deviceAddress] ?: resourceType.deviceName

        // Add to the custom adapter defined specifically for showing
        // wifi devices.
        (deviceListRecyclerView.adapter as WifiPeerListAdapter).apply {
            addDevice(resourceType)
            notifyDataSetChanged()
        }

        Log.d("LOG", "onBonjourServiceAvailable $instanceName")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, R.layout.activity_join, txtListener, servListener)
        deviceListRecyclerView = this.findViewById<RecyclerView>(R.id.deviceList)



        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        deviceListRecyclerView.layoutManager = linearLayoutManager
        deviceListRecyclerView.adapter = WifiPeerListAdapter(manager, channel)

        val serviceRequest = WifiP2pDnsSdServiceRequest.newInstance()
        manager?.addServiceRequest(
            channel,
            serviceRequest,
            object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    Log.d("test", "SERVICE REQUEST CREATED")
                }

                override fun onFailure(reason: Int) {
                    Log.d("test", "SERVICE REQUEST NOT CREATED")
                }
            }
        )
        manager?.discoverServices(
            channel,
            object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    Log.d("TAG", "SUCCESSFULLY DISCOVEDER SERVEICES")
                }

                override fun onFailure(p0: Int) {
                    Log.d("TAG", "FAILED TO DISCOVER SERVICES")
                }

            }
        )

    }
}