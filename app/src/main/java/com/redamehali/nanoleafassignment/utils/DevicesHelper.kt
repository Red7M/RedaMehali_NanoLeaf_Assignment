package com.redamehali.nanoleafassignment.utils

import com.redamehali.nanoleafassignment.models.Device
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

/**
 * Helper Class provides parsing method,
 * and an ascending order helper method
 *
 * Created by Reda Mehali on 3/8/21.
 */
class DevicesHelper {

    companion object {
        /**
         * Method takes data and returns a parsed list of Device
         * Selection of home lights device names, and colors is
         * done randomly. Which means, names and colors could be duplicates.
         * The purpose of this approach is to have a realistic result, and to
         * avoid constant hardcoded insertion.
         *
         * @param sequence string sequence of data
         * @param homeLights various titles of lights available in a home
         * @param colors different set of colors
         *
         * @return an ArrayList of type Device
         */
        fun parseStringSequenceToDeviceList(
                sequence: String,
                homeLights: Array<String>,
                colors: Array<Int>) : ArrayList<Device> {
            val sequenceSplit : List<String> = sequence.split("\\s".toRegex())
            val devicesArrayList = ArrayList<Device>()

            for (i in sequenceSplit.indices step 3) {
                val device = Device(
                    homeLights[Random.nextInt(homeLights.size)],
                    colors[Random.nextInt(colors.size)])

                if (sequenceSplit[i].length == 5) {
                    device.id = sequenceSplit[i]
                }
                if (sequenceSplit[i+1].length == 1) {
                    device.isOn = sequenceSplit[i+1] == "1"
                }
                if (sequenceSplit[i+2].isNotEmpty() && sequenceSplit[i+2].length <= 3){
                    device.brightness = sequenceSplit[i+2].toInt()
                }

                devicesArrayList.add(device)
            }

            return devicesArrayList
        }

        /**
         * Orders devices lists in ascending order by returning a TreeMap List
         *
         * @param devicesList list of devices
         * @return TreeMap of deviceId key, and device model value
         */
        fun orderDeviceListInAscending(devicesList: ArrayList<Device>) : TreeMap<Int, Device> {
            val devicesTreeList = TreeMap<Int, Device>()

            for (device in devicesList) {
                devicesTreeList[device.id!!.toInt()] = device
            }

            return devicesTreeList
        }
    }

}