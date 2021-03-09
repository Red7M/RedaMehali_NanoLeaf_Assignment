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
class DevicesViewModel(application: Application) : AndroidViewModel(application) {

    val devicesInfoLiveData = MutableLiveData<TreeMap<Int, Device>>()
    private val commandCenterRepository = CommandCenterRepository()

    /**
     * Calls data retrieval from the commandCenterRepository
     */
    fun retrieveData() {
        devicesInfoLiveData.value = commandCenterRepository.retrieveAscendingParsedData()
    }

    /**
     * Calls data change from the commandCenterRepository
     */
    fun deviceChange(device: Device, deviceEnumMeta: DeviceEnumMeta) {
        viewModelScope.launch(Dispatchers.IO) {
            commandCenterRepository.processDeviceRequests(device, deviceEnumMeta)
        }
    }
}