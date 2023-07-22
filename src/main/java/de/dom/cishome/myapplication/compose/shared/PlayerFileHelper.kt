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

    fun playerDir( id: String): File{
        val dir = File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${id}" )
        return dir;
    }

    fun playerFile( id: String , file: String): File {
        val dir = playerDir(id)
        return File( dir , file);
    }

}