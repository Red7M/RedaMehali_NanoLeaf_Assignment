package com.redamehali.nanoleafassignment.models

/**
 * Model Data class for Device Type
 *
 * Created by Reda Mehali on 3/8/21.
 */
data class Device (
    var name: String,
    var rectangleColor: Int? = null,
    var id: String? = null,
    var isOn: Boolean = false,
    var brightness: Int? = 0)