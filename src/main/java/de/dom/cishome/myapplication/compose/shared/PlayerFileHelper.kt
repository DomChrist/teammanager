package de.dom.cishome.myapplication.compose.shared

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileReader
import java.io.FileWriter

open class PlayerFileHelper {

    private fun tm(): File{
        //var f = Environment.getExternalStoragePublicDirectory("documents")
        //var f = Environment.getDataDirectory()!!;
        var f = File("/data/user/0/de.dom.cishome.myapplication/files")
        Log.d("files" , "${f.canRead()}")
        Log.d("files" , "${f.canWrite()}")
        return f;
    }
    fun write( f: File, json: String ){
        var writer = FileWriter(f);
        writer.write( json );
        writer.flush();
        writer.close();
    }

    fun read( f: File ): String{
        val fileReader = FileReader(f);
            val json = fileReader.readText();
            fileReader.close();
        return json;
    }

    fun storagePath(): File{
        val root = tm();
        val tm: File = root.resolve("tm");
        return tm;
    }

    fun playerDir( id: String): File{
        val dir = File( tm().absolutePath + "/tm/players/${id}" )
        if( !dir.exists() ){
            dir.mkdirs()
        };
        return dir;
    }

    fun playerFile( id: String , file: String): File {
        val dir = playerDir(id)
        return File( dir , file);
    }

    fun playerFile( id: String): File {
        val dir = playerDir(id)
        return File( dir , "player.json");
    }

    fun competitionDir(): File{
        return File( storagePath() , "competition");
    }

    fun competitionFile( id: String ): File{
        val competitionDir = File(competitionDir() , id );
        competitionDir.mkdirs();

        return File(competitionDir, "competition.json");
    }

    fun playersDir(): File? {
        val dir = File( tm().absolutePath + "/tm/players" )
        if( !dir.exists()) dir.mkdirs();
        return dir;
    }


}