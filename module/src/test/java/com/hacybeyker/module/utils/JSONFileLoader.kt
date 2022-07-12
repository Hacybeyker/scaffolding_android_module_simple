package com.hacybeyker.module.utils

import com.google.gson.Gson
import java.io.InputStreamReader

class JSONFileLoader {

    fun loadJsonString(file: String): String? {
        val loader: InputStreamReader? =
            InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        val resultData = loader?.readText()
        loader?.close()
        return resultData
    }

    inline fun <reified T> loadJsonString(file: String): T {
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        val resultData = loader.readText()
        loader.close()
        return Gson().fromJson(resultData, T::class.java)
    }
}
