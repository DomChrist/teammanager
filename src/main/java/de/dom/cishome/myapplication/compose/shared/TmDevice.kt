package de.dom.cishome.myapplication.compose.shared

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.CombinedVibration
import android.os.Environment
import android.os.VibrationEffect
import android.os.VibratorManager
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.util.Objects


class TmDevice {

    companion object Vibrate{

        fun Call( ctx: Context, text: String ){
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_CALL
                data = Uri.parse("tel:$text")
                //putExtra(Intent.EXTRA_TEXT, text)
                //type = "text/plain"
            }
            //val shareIntent = Intent.createChooser(sendIntent, null)
            try{
                ctx.startActivity( sendIntent );
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        fun vibrate( ctx: Context , millis: Long = 500 ){
            var v = ctx.getSystemService( Context.VIBRATOR_MANAGER_SERVICE ) as VibratorManager
            v.defaultVibrator.vibrate(VibrationEffect.createOneShot(millis,255))
            //var c = CombinedVibration.startParallel().addVibrator( 0 , VibrationEffect.createOneShot(millis ,255) ).combine()
            //v.vibrate( c );
        }

        fun vibrate( ctx: Context, millis: Int = 500, repeat: Int ){
            var v = ctx.getSystemService( Context.VIBRATOR_MANAGER_SERVICE ) as VibratorManager
            var c = CombinedVibration.startParallel().addVibrator( 0 , VibrationEffect.createOneShot(500 ,255) ).combine()
            for( i in repeat downTo 0 step 1){
                v.vibrate( c );
                Thread.sleep(1000)
            }
        }

        fun wave(ctx: Context){
            var v = ctx.getSystemService( Context.VIBRATOR_MANAGER_SERVICE ) as VibratorManager
            var c = CombinedVibration.startParallel().addVibrator( 0, VibrationEffect.createWaveform( longArrayOf(0,100,1000) ,
                intArrayOf(255,0,255) , -1)).combine();
            v.vibrate( c )
            v.cancel();
        }

    }

}


fun shotPlayerImage( id: String, context: Context, onNewImage:()->Unit={}) {
    val values = ContentValues()



    values.put(MediaStore.Images.Media.TITLE, "New Picture")
    values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

    //camera intent
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    // set filename
    var vFilename = "main.jpg"

    var file = PlayerFileHelper().playerDir(id);
    file = File(file, vFilename);


    Log.i("Pic" , "exist = " + file.exists() )
    Log.i("Pic" , "exist = " + file.absolutePath )

    val image_uri = FileProvider.getUriForFile(Objects.requireNonNull( context ), "tm.provider", file);

    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
    var a = context.startActivity(cameraIntent);


    Log.i("image" , a.toString());
    onNewImage();
}

fun share( ctx: Context, text: String ){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    ctx.startActivity( shareIntent );
}

fun share( ctx: Context, text: String, t: String = "text/plain" ){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = t
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    ctx.startActivity( shareIntent );
}

class TmDeviceShare{

    companion object Sharing{
        fun share( ctx: Context, text: String ){
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            ctx.startActivity( shareIntent );
        }

        fun share( ctx: Context, text: String, t: String = "text/plain" ){
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = t
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            ctx.startActivity( shareIntent );
        }
    }

}


