package com.redamehali.nanoleafassignment.repositories

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.redamehali.nanoleafassignment.constants.DeviceEnumMeta
import com.redamehali.nanoleafassignment.data.MockData
import com.redamehali.nanoleafassignment.models.Device
import com.redamehali.nanoleafassignment.utils.DevicesHelper
import java.util.*
import kotlin.random.Random

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

    private val colorLiveData = MutableLiveData<Array<Float?>>()

    /**
     * Retrieves data, and orders it into an ascending order
     *
     * @return TreeMap list of DeviceId as a key, and Device as value
     */
    fun retrieveAscendingParsedData(): TreeMap<Int, Device> {
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

    /**
     * Makes requests for different color necessaries like: hue,
     * brightness, and saturation
     *
     * @param device single item device selected/clicked in main screen
     * @param position position of the device selected/clicked in main screen
     */
    fun colorHueRequest(device: Device, position: Int) {
        val hsvColor = Array<Float?>(4) { null }

        getHue(device.id!!) { exception, hue ->
            handleResponse(hsvColor, 0, exception, hue?.toFloat())
        }
        getSaturation(device.id!!) { exception, saturation ->
            handleResponse(hsvColor, 1, exception, saturation)
        }
        getBrightness(device.id!!) { exception, brightness ->
            handleResponse(hsvColor, 2, exception, brightness)
        }
        //TODO Needs to implement a proper way to re-propagate device position
        // back to the activity screen instead of storing value in hsvColor
        hsvColor[3] = position.toFloat()
    }

    /**
     * Handles response from fake-pseudo server
     * Prints error message and returns if there is an exception
     */
    private fun handleResponse(hsvColor: Array<Float?>, floatPosition: Int, e: Exception?, value: Float?) {
        if (e != null) {
            Log.i(TAG, e.message!!)
            return
        }
        hsvColor[floatPosition] = value!!.toFloat()
        setColorToLiveDataColor(hsvColor)
    }

    /**
     * Sets new color into LiveData only and only if all floats
     * builder are not null
     */
    private fun setColorToLiveDataColor(hsvColor: Array<Float?>) {
        if (hsvColor[0] != null && hsvColor[1] != null && hsvColor[2] != null && hsvColor[3] != null) {
            colorLiveData.value = hsvColor
        }
    }

    /**
     * get current mutable LiveData color
     */
    fun getColor(): MutableLiveData<Array<Float?>> {
        return colorLiveData
    }

    fun getHue(deviceId: String, complete: ((Exception?, Int?) -> Unit)) {
        Log.d(TAG, "Requesting hue from device $deviceId")
        Handler(Looper.getMainLooper()).postDelayed({
            val success = true // Change it to random exception
            if (success) {
                val hue = Random.nextInt(0, 100)
                Log.d(TAG, "Received hue from device $deviceId")
                complete(null, hue)
            } else {
                Log.d(TAG, "Error receiving hue from $deviceId")
                complete(Exception("Request Timed Out"), null)
            }
        }, 5000)
    }

    fun getSaturation(deviceId: String, complete: ((Exception?, Float?) -> Unit)) {
        val delay = Random.nextLong(1000, 3000)
        Log.d(TAG, "Requesting saturation from device $deviceId")
        Handler(Looper.getMainLooper()).postDelayed({
            val success = Random.nextFloat() > 0.25f
            if (success) {
                val saturation = Random.nextFloat()
                Log.d(TAG, "Received saturation from device $deviceId")
                complete(null, saturation)
            } else {
                Log.d(TAG, "Error receiving saturation from $deviceId")
                complete(Exception("Request Timed Out"), null)
            }
        }, delay)
    }

    fun getBrightness(deviceId: String, complete: ((Exception?, Float?) -> Unit)) {
        val delay = Random.nextLong(1000, 3000)
        Log.d(TAG, "Requesting brightness from device $deviceId")
        Handler(Looper.getMainLooper()).postDelayed({
            val success = Random.nextFloat() > 0.25f
            if (success) {
                val brightness = Random.nextFloat()
                Log.d(TAG, "Received brightness from device $deviceId")
                complete(null, brightness)
            } else {
                Log.d(TAG, "Error receiving brightness from $deviceId")
                complete(Exception("Request Timed Out"), null)
            }
        }, delay)
    }
}