package com.redamehali.nanoleafassignment.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.redamehali.nanoleafassignment.R
import com.redamehali.nanoleafassignment.models.Device
import java.util.*
import kotlin.collections.ArrayList


/**
 * Adapter class responsible for creating single view items,
 * and binding each single item (device) to the view. Class
 * also contains various adapter related methods to set, get,
 * add, edit data within adapters' list.
 *
 * Created by Reda Mehali on 3/8/21.
 */
class MyDevicesAdapter(val deviceControlListener: DeviceControlListener) : RecyclerView.Adapter<MyDevicesAdapter.MyViewHolder>() {

    var devicesList = ArrayList<Device>()

    /**
     * Single Device View Holder class
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Instantiate views of single item device
        var cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
        var deviceRectangleColor: View = itemView.findViewById(R.id.deviceRectangleColor)
        var deviceName: TextView = itemView.findViewById(R.id.deviceName)
        var deviceId: TextView = itemView.findViewById(R.id.deviceId)
        var deviceOnOff: SwitchCompat = itemView.findViewById(R.id.deviceOnOff)
        var brightnessSeekBar: SeekBar = itemView.findViewById(R.id.brightnessSeekBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.device_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val deviceItem = devicesList[holder.adapterPosition]
        val color = deviceItem.rectangleColor!!

        // Bind views to data
        holder.cardView.setCardBackgroundColor(color)
        holder.deviceRectangleColor.setBackgroundColor(color)
        holder.deviceName.text = deviceItem.myDeviceName
        holder.deviceId.text = deviceItem.id
        holder.deviceOnOff.isChecked = deviceItem.isOn
        holder.brightnessSeekBar.progress = deviceItem.brightness!!

        // Set all colors for switches and seek bars
        setSwitchesSeekBarColorsAttributes(holder)

        // Set switch listener
        holder.deviceOnOff.setOnCheckedChangeListener { _: CompoundButton, isOn: Boolean ->
            deviceControlListener.onSwitchClickListener(holder.adapterPosition, isOn)
        }

        // Set Seek bar change listener
        holder.brightnessSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, brightness: Int, p2: Boolean) {
                deviceControlListener.onBrightnessControlListener(holder.adapterPosition, brightness)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        // Set listener on rectangle clicks
        holder.deviceRectangleColor.setOnClickListener {
            // Click action happens
            deviceControlListener.rectangleClickListener(holder.adapterPosition)
        }
    }

    /**
     * Set colors for devices switches, as well as seek bar progress
     *
     * @param holder single device item view
     */
    private fun setSwitchesSeekBarColorsAttributes(holder: MyViewHolder) {
        val deviceItem = devicesList[holder.adapterPosition]
        val color = deviceItem.rectangleColor!!

        DrawableCompat.setTintList(
            DrawableCompat.wrap(holder.deviceOnOff.thumbDrawable),
            ColorStateList(arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)),
                intArrayOf(Color.WHITE, color))
        )
        DrawableCompat.setTintList(
            DrawableCompat.wrap(holder.deviceOnOff.thumbDrawable),
            ColorStateList(arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)),
                intArrayOf(Color.WHITE, color)))

        holder.brightnessSeekBar.progressDrawable.setColorFilter(
            deviceItem.rectangleColor!!,
            PorterDuff.Mode.MULTIPLY
        )
        holder.brightnessSeekBar.thumb.setColorFilter(
            deviceItem.rectangleColor!!,
            PorterDuff.Mode.SRC_ATOP
        )
    }

    /**
     * get devices item count
     */
    override fun getItemCount(): Int {
        return devicesList.size
    }

    /**
     * get single device at specific position
     *
     * @param position position of current device in the list
     */
    fun getDeviceAtPosition(position: Int) : Device{
        return devicesList[position]
    }

    /**
     * Inserts devices from TreeMap into device ArrayList
     *
     * @param devices TreeMap keys device ID to device data value
     */
    fun setDevicesList(devices: TreeMap<Int, Device>) {
        for (device in devices) {
            devicesList.add(device.value)
        }
        notifyDataSetChanged()
    }

    /**
     * Sets device new color and refresh UI data
     */
    fun setDeviceNewHueColor(colorHsv: Array<Float?>, position: Int) {
        devicesList[position].rectangleColor =
                Color.HSVToColor(floatArrayOf(colorHsv[0]!!, colorHsv[1]!!, colorHsv[2]!!))
        notifyDataSetChanged()
    }

    /**
     * Calculates the average brightness of all devices
     *
     * @return average brightness as an integer
     */
    fun getAverageBrightness() : Int {
        var averageBrightness = 0
        for (device in devicesList) {
            averageBrightness += device.brightness!!
        }
        return (averageBrightness / devicesList.size)
    }

    /**
     * @return true if at least one of the devices are on,
     * false otherwise.
     */
    fun getGlobalPowerSwitchState() : Boolean {
        for (device in devicesList) {
            if (device.isOn) return true
        }
        return false
    }

    /**
     * switches state of all devices based on
     * the current state of the global power control
     */
    fun toggleAllDevices(isOn: Boolean) {
        for (device in devicesList) {
            device.isOn = isOn
        }
        notifyDataSetChanged()
    }

    /**
     * Sets brightness of all deices based on
     * the current global average brightness
     */
    fun setBrightnessForAllDevicesAsGlobalBrightness(brightness: Int) {
        for (device in devicesList) {
            device.brightness = brightness
        }
        notifyDataSetChanged()
    }

    /**
     * Interface that passes on state changes related to
     * the device power switch, or/and brightness change.
     */
    interface DeviceControlListener {
        fun onSwitchClickListener(position: Int, isOn: Boolean)
        fun onBrightnessControlListener(position: Int, brightness: Int)
        fun rectangleClickListener(position: Int)
    }
}