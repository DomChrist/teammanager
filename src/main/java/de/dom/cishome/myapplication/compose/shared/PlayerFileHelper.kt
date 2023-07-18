package de.dom.cishome.myapplication.compose.shared

import android.os.Environment
import java.io.File

open class PlayerFileHelper {

    fun storagePath(): File{
        val property = System.getProperty("seperator")
        val root = Environment.getExternalStoragePublicDirectory("documents")

        val tm: File = root.resolve("tm");
        return tm;
    }

}