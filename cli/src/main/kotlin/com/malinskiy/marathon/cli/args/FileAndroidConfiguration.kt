package com.malinskiy.marathon.cli.args

import com.fasterxml.jackson.annotation.JsonProperty
import com.malinskiy.marathon.android.AndroidConfiguration
import java.io.File
import javax.naming.ConfigurationException

data class FileAndroidConfiguration(@JsonProperty("androidSdk") val androidSdk: File?,
                                    @JsonProperty("applicationApk") val applicationOutput: File,
                                    @JsonProperty("testApplicationApk") val testApplicationOutput: File,
                                    @JsonProperty("autoGrantPermission") val autoGrantPermission: Boolean?,
                                    @JsonProperty("adbInitTimeoutMillis") val adbInitTimeoutMillis: Int?)
    : FileVendorConfiguration {


    fun toAndroidConfiguration(androidSdkOverride: File?): AndroidConfiguration {
        val finalAndroidSdk = androidSdkOverride
                ?: androidSdk
                ?: throw ConfigurationException("No android SDK path specified")

        if (autoGrantPermission != null && adbInitTimeoutMillis != null) {
            return AndroidConfiguration(
                    finalAndroidSdk,
                    applicationOutput,
                    testApplicationOutput,
                    autoGrantPermission,
                    adbInitTimeoutMillis)
        } else if(autoGrantPermission != null) {
            return AndroidConfiguration(
                    finalAndroidSdk,
                    applicationOutput,
                    testApplicationOutput,
                    autoGrantPermission = autoGrantPermission)
        } else if(adbInitTimeoutMillis != null) {
            return AndroidConfiguration(
                    finalAndroidSdk,
                    applicationOutput,
                    testApplicationOutput,
                    adbInitTimeoutMillis = adbInitTimeoutMillis)
        } else {
            return AndroidConfiguration(
                    finalAndroidSdk,
                    applicationOutput,
                    testApplicationOutput)
        }
    }
}