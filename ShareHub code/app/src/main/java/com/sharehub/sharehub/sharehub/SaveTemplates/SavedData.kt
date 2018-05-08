package com.sharehub.sharehub.sharehub.SaveTemplates

import android.content.Context
import android.content.ContextWrapper

import org.apache.commons.io.IOUtils

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

import android.content.Context.MODE_PRIVATE

object SavedData {
    fun <T> Read(context: ContextWrapper, name: String): T? {
        try {
            val fis = context.openFileInput(name)
            val input = ObjectInputStream(fis)
            val re = input.readObject() as T
            input.close()
            fis.close()
            return re
        } catch (e: Exception) {
            return null
        }

    }

    fun <T> Write(context: ContextWrapper, name: String, o: T) {
        try {
            val fos = context.openFileOutput(name, Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(o)
            os.close()
            fos.close()
        } catch (e: Exception) {

        }

    }
}
