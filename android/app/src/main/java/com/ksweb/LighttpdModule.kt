package com.ksweb

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Callback
import java.io.File

class LighttpdModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    init {
        // Load all required shared libraries
        loadLibraries()
    }

    private fun loadLibraries() {
        try {
            // List of all required .so files
            val libraries = listOf(
                "libapac.so",
                "libapr-1.so",
                "libaprutil-1.so",
                "libcrypto102u.so",
                "libcrypto.so",
                "libc++_shared.so",
                "libcurl.so",
                "libexpat.so",
                "libfreetype.so",
                "libgd.so",
                "libiconv.so",
                "libicudata.so",
                "libicui18n.so",
                "libicuio.so",
                "libicutest.so",
                "libicutu.so",
                "libicuuc.so",
                "libintl.so",
                "libjpeg.so",
                "libksweb_1-2.so",
                "liblber.so",
                "libldap.so",
                "liblhttpd.so",  // Lighttpd library
                "libmod_fastcgi.so",
                "libngx.so",
                "libonig.so",
                "libpcre.so",
                "libph-5-6-40-api23.so",
                "libph-7-1-33-api23.so",
                "libph-7-2-34-api23.so",
                "libph-7-3-30-api23.so",
                "libph-7-4-23-api23.so",
                "libph-8-0-28-api23.so",
                "libph-8-1-19-api23.so",
                "libph-8-2-6-api23.so",
                "libphcgi-5-6-40-api23.so",
                "libphcgi-7-1-33-api23.so",
                "libphcgi-7-2-34-api23.so",
                "libphcgi-7-3-30-api23.so",
                "libphcgi-7-4-23-api23.so",
                "libphcgi-8-0-28-api23.so",
                "libphcgi-8-1-19-api23.so",
                "libphcgi-8-2-6-api23.so",
                "libpng16.so",
                "libsmtp.so",
                "libsqld-5-6-38-api23.so",
                "libsqld-5-6-38-api27.so",
                "libsqld-5-7-34-api27.so",
                "libsqlite3.so",
                "libssl102u.so",
                "libssl.so",
                "libxml2.so",
                "libzip.so"
            )

            // Load each library
            for (library in libraries) {
                System.loadLibrary(library.removePrefix("lib").removeSuffix(".so"))
                Log.d("LighttpdModule", "Loaded library: $library")
            }
        } catch (e: UnsatisfiedLinkError) {
            Log.e("LighttpdModule", "Failed to load a library: ${e.message}")
        } catch (e: Exception) {
            Log.e("LighttpdModule", "Unexpected error: ${e.message}")
        }
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