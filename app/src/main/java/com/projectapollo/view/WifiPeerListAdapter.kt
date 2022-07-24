package com.projectapollo.view

import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.projectapollo.R


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class WifiPeerListAdapter(
    private val deviceList: MutableList<WifiP2pDevice>,
    private val manager: WifiP2pManager?,
    private val channel: WifiP2pManager.Channel?
) : RecyclerView.Adapter<WifiPeerListAdapter.ViewHolder>() {

    class ViewHolder(
        view: View,
        deviceList: MutableList<WifiP2pDevice>,
        manager: WifiP2pManager?,
        channel: WifiP2pManager.Channel?
    ) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.textView)
            textView.setOnClickListener {
                println(textView.text.toString())
                var clickedDevice: WifiP2pDevice? = null

                deviceList.forEach { device ->
                    if (textView.text.toString() == device.deviceName) {
                        clickedDevice = device
                    }
                }

                if (clickedDevice == null) {
                    return@setOnClickListener
                }

                val config = WifiP2pConfig().apply {
                    deviceAddress = clickedDevice!!.deviceAddress
                    wps.setup = WpsInfo.PBC
                }
                manager?.connect(
                    channel,
                    config,
                    object : WifiP2pManager.ActionListener {
                        override fun onSuccess() {
                            println("CONNECTION TO " + clickedDevice!!.deviceName.toString() + " SUCCESS")
                        }

                        override fun onFailure(reason: Int) {
                            println("CONNECTION TO " + clickedDevice!!.deviceName.toString() + " FAILED")
                        }

                    }
                )
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.device_item, viewGroup, false)
        return ViewHolder(view, deviceList, manager, channel)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val device = deviceList.elementAt(position)
        viewHolder.textView.text = device.deviceName
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

}