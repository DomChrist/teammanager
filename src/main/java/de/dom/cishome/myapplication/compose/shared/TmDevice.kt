package de.dom.cishome.myapplication.compose.shared

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.util.Objects

fun openCamera( id: String, path: String, context: Context) {
    val values = ContentValues()

    values.put(MediaStore.Images.Media.TITLE, "New Picture")
    values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

    //camera intent
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    // set filename
    var vFilename = "main.jpg"

    var file = File(Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${id}")
    file = File(file, vFilename);

    Log.i("Pic" , "exist = " + file.exists() )
    Log.i("Pic" , "exist = " + file.absolutePath )

    val image_uri = FileProvider.getUriForFile(Objects.requireNonNull( context ), "tm.provider", file);

    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
    var a = context.startActivity(cameraIntent);

    Log.i("image" , a.toString());
}