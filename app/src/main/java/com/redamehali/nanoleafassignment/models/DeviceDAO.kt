package com.redamehali.nanoleafassignment.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DeviceDAO {

    @Query("SELECT * FROM device")
    fun getAll(): List<Device>

    @Query("SELECT * FROM device WHERE id IN (:deviceIds)")
    fun loadAllByIds(deviceIds: Array<String>): List<Device>

    @Query("SELECT * FROM device WHERE my_device_name LIKE :name")
    fun findByName(name: String) : Device

    @Insert
    fun insertAll(vararg devices: Device)

    @Delete
    fun delete(device: Device)
}