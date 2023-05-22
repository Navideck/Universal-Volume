package com.navideck.universal_volume.helper

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class VolumeHelper {

    /// To get `BuildPlatform` of the device
    val buildPlatform: String?
        get() {
            return try {
                val process = ProcessBuilder(
                    "/system/bin/getprop", "ro.board.platform"
                ).redirectErrorStream(true).start()
                val br = BufferedReader(InputStreamReader(process.inputStream))
                var line: String?
                var boardPlatform: String? = ""
                while (br.readLine().also { line = it } != null) {
                    boardPlatform = line
                }
                process.destroy()
                return boardPlatform
            } catch (e: IOException) {
                null
            }
        }
}