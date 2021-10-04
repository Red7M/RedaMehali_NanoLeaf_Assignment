package com.redamehali.nanoleafassignment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redamehali.nanoleafassignment.constants.DeviceEnumMeta
import com.redamehali.nanoleafassignment.models.Device
import com.redamehali.nanoleafassignment.repositories.CommandCenterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel Class responsible of communicating between activity
 * screen MyDevicesActivityScreen and the repository CommandCenterRepository
 *
 * Created by Reda Mehali on 3/8/21.
 */
class DevicesViewModel public constructor(application: Application) : AndroidViewModel(application) {

    val ascendedDevicesLiveData = MutableLiveData<TreeMap<Int, Device>>()
    val devicesLiveData = MutableLiveData<List<Device>>()
    private val commandCenterRepository = CommandCenterRepository(application.applicationContext)

    /**
     * Calls data retrieval from the commandCenterRepository
     */
    fun retrieveDataInAscendingOrder() {
        ascendedDevicesLiveData.value = commandCenterRepository.retrieveAscendingParsedData()
    }

    fun retrieveAllDevices() {
        devicesLiveData.value = commandCenterRepository.retrieveAllDevicesData()
    }

    /**
     * Calls data change from the commandCenterRepository
     */
    fun deviceChange(device: Device, deviceEnumMeta: DeviceEnumMeta) {
        viewModelScope.launch(Dispatchers.IO) {
            commandCenterRepository.processDeviceRequests(device, deviceEnumMeta)
        }
    }

    /**
     * Calls for color request from commandCenterRepository
     */
    fun retrieveColor(device: Device, position: Int) {
        commandCenterRepository.colorHueRequest(device, position)
    }

    /**
     * returns Hue color Mutable LiveData
     */
    fun getColorHueLiveData() : MutableLiveData<Array<Float?>> {
        return commandCenterRepository.getColor()
    }

}