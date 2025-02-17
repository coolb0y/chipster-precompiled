package com.ksweb

import android.util.Log
import android.os.Build
import android.widget.Toast
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Callback
import java.io.File

class LighttpdModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    init {
        System.loadLibrary("lhttpd")  // Load the Lighttpd library
    }

    @ReactMethod
    fun startLighttpd(callback: Callback) {
        try {
            val serverExecutable = File("/data/data/com.ksweb/files/lib/httpd")
            if (serverExecutable.exists()) {
                // Assuming you have the Lighttpd binary inside the app's internal storage
                val process = Runtime.getRuntime().exec(serverExecutable.absolutePath)
                callback.invoke("Lighttpd started successfully")
            } else {
                callback.invoke("Lighttpd executable not found!")
            }
        } catch (e: Exception) {
            callback.invoke("Error starting Lighttpd: ${e.message}")
        }
    }

    override fun getName(): String {
        return "LighttpdModule"
    }
}
