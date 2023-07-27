package de.dom.cishome.myapplication.compose.shared

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import de.dom.cishome.myapplication.compose.home.header
import de.dom.cishome.myapplication.compose.player.service.Player
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FilePermission
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


interface PlayerRepository{

    fun players(): List<Player>;

    fun write(p: Player)

}

@ExperimentalPermissionsApi
class FileRep( var c: Context, var p: PermissionState ) : PlayerRepository{

    val root = "/data/user/0/de.dom.cishome.myapplication/files/tm"

    @ExperimentalPermissionsApi
    private fun fileFun(){

        p.launchPermissionRequest();

        val filesDir = Environment.getExternalStoragePublicDirectory("documents")
        val new = File( filesDir , "myapp" );
        Log.i("path" , filesDir?.absolutePath!! );
        Log.i("path" , new?.absolutePath!! );
        val createNewFile = new.mkdirs()
        Log.i( "file" , new.absolutePath + " " + createNewFile );

        File(new , "test.txt").createNewFile()

        filesDir.list().forEach { Log.i("file" , it) }

        val out: FileOutputStream = c.openFileOutput( "test.txt" , Context.MODE_PRIVATE)

    }

    override fun players(): List<Player> {
        var pfh = PlayerFileHelper();
        val file = pfh.playersDir()!!;

        file.listFiles()?.filter {
            it.exists() && it.isDirectory
        }

        val list = file.listFiles()?.filter { it.exists() && it.isDirectory }
            ?.map {
                var player: Player? = null;
                try{
                    val playerfile = pfh.playerFile(it.name);
                    val fileReader = FileReader(playerfile);
                    var txt = fileReader.readText();
                    player = GsonUtils.mapper().fromJson( txt , Player::class.java );
                    fileReader.close()
                }catch ( e: Exception){
                    if( e is FileNotFoundException ){
                        Log.e("FileNotFound" , "File for player ${player?.id ?: "(noId)"} not found")
                    } else {
                        e.printStackTrace()
                    }
                }
                player;
            }?.filterNotNull() ?: listOf()

        return list;
    }

    override fun write(p: Player) {
        try {
            val helper = PlayerFileHelper();
            val toJson = GsonUtils.Json.mapper().toJson( p );

            var key = this.key( p );
            var file = helper.playerFile( key )

            helper.write( file , toJson )
        }catch (e: Exception){

        }

    }


    private fun key( p: Player): String {
        var key = p.familyName.lowercase().replace(" " , "").plus( "_" ).plus(p.givenName.lowercase())
        return key;
    }


}

