package com.redamehali.nanoleafassignment.repositories

import android.util.Log
import com.redamehali.nanoleafassignment.constants.DeviceEnumMeta
import com.redamehali.nanoleafassignment.data.MockData
import com.redamehali.nanoleafassignment.models.Device
import com.redamehali.nanoleafassignment.utils.DevicesHelper
import java.util.*

/**
 * Repository class responsible for printing requests
 * to the console. Example: turning the device off should
 * send the request to the command center class.
 * The command center will then print “Device 12345- Turned off”
 *
 * Created by Reda Mehali on 3/8/21.
 */
class CommandCenterRepository {

    private val TAG = "CommandCenterRepository"
    private val TURNED_ON = "Turned On"
    private val TURNED_OFF = "Turned Off"

    /**
     * Retrieves data, and orders it into an ascending order
     *
     * @return TreeMap list of DeviceId as a key, and Device as value
     */
    fun retrieveAscendingParsedData() : TreeMap<Int, Device> {
        val devicesList = DevicesHelper.parseStringSequenceToDeviceList(
            MockData.devicesSequences,
            MockData.homeLights,
            MockData.colors,
        )

        return DevicesHelper.orderDeviceListInAscending(devicesList)
    }

    /**
     * Sends and prints requests into the console under a Log.info()
     *
     * Example:
     * POWER CHANGE: "Device 12345- Turned off",
     * BRIGHTNESS CHANGE: "Device 12345- Brightness is 55"
     */
    fun processDeviceRequests(device: Device, deviceEnumMeta: DeviceEnumMeta) {
        if (deviceEnumMeta == DeviceEnumMeta.POWER) {
            val isOnStr = if (device.isOn) {
                TURNED_ON
            } else {
                TURNED_OFF
            }

            Log.i(TAG, "Device ${device.id}- $isOnStr")
        } else if (deviceEnumMeta == DeviceEnumMeta.BRIGHTNESS) {
            Log.i(TAG, "Device ${device.id}- Brightness is ${device.brightness}")
        }
    }
}