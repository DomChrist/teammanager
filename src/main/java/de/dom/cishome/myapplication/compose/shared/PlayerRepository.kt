package de.dom.cishome.myapplication.compose.shared

import android.content.Context
import android.os.Environment
import android.util.Log
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.gson.Gson
import de.dom.cishome.myapplication.compose.player.service.Player
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.FilenameFilter

interface PlayerRepository{

    fun players(): List<Player>;

    fun write(p: Player)

}

@ExperimentalPermissionsApi
class FileRep( var c: Context, var p: PermissionState ) : PlayerRepository{

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
        p.launchPermissionRequest();

        val file = File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players" )


        val list = file.listFiles().filter { it.isDirectory }
            .map {
                val data = File( file , "/${it.name}/data.player")
                val fileReader = FileReader(data);
                    var player : Player = Gson().fromJson( fileReader.readText() , Player::class.java );
                fileReader.close()
                player;
            }

        if( list == null || list.isEmpty() ) return listOf();

        return list;
    }

    override fun write(p: Player) {

        val toJson = Gson().toJson(p);

        val file = File(Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${p.id}"); ///${p.id}.json")
            file.mkdirs();

        val playerFile = File( file , "data.player")
        var writer = FileWriter( playerFile );
            writer.write(toJson)
        writer.flush()
        writer.close()


    }


}