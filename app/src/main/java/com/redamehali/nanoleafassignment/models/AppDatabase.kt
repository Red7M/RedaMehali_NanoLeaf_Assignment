package com.redamehali.nanoleafassignment.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Device::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDAO
}