package com.redamehali.nanoleafassignment.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model Data class for Device Type
 *
 * Created by Reda Mehali on 3/8/21.
 */
@Entity
data class Device(
    @PrimaryKey var id: String = "_",
    @ColumnInfo(name = "my_device_name") var myDeviceName: String,
    @ColumnInfo var rectangleColor: Int? = null,
    @ColumnInfo var isOn: Boolean = false,
    @ColumnInfo var brightness: Int? = 0
)