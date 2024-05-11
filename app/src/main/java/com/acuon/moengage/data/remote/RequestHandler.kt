package com.acuon.moengage.data.remote

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object RequestHandler {

    const val GET: String = "GET"
    const val POST: String = "POST"
    const val TIMEOUT = 6000

    @Throws(IOException::class)
    inline fun <reified T> requestGet(url: String?): T? {
        val obj = URL(url)
        val connection = obj.openConnection() as HttpURLConnection
        connection.requestMethod = GET
        connection.readTimeout = TIMEOUT
        connection.connectTimeout = TIMEOUT
        val responseCode = connection.responseCode
        return if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = Gson().fromJson(reader, T::class.java)
            reader.close()
            inputStream.close()
            response
        } else {
            null
        }
    }
}