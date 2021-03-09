package com.redamehali.nanoleafassignment.data

import android.graphics.Color

/**
 * Mock data class contains dummy data of:
 * - Devices Parsable String sequence
 * - Different Home Lights
 * - Different Colors
 *
 * Created by Reda Mehali on 3/8/21.
 */
class MockData {

    companion object {
        const val devicesSequences =
                    "10001 1 34 " +
                    "10010 1 88 " +
                    "10004 1 20 " +
                    "10003 0 100 " +
                    "10007 0 5 " +
                    "10002 0 0 " +
                    "10008 0 41 " +
                    "10005 1 76 " +
                    "10006 0 52 " +
                    "10009 1 10"

        val homeLights = arrayOf(
                "Bedroom Light",
                "Kitchen Light",
                "Bathroom Light",
                "Living Light",
                "Basement Light",
                "Garden Light",
                "Pantry Light",
                "Garage Light",
                "Attic Light",
                "Pool Light")

        val colors = arrayOf(
            Color.BLUE,
            Color.GRAY,
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.BLACK,
            Color.LTGRAY,
            Color.DKGRAY)
    }
}