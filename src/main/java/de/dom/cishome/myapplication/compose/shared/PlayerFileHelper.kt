package de.dom.cishome.myapplication.compose.shared

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter

open class PlayerFileHelper {

    private fun tm(): File{
        var f = File("/data/user/0/de.dom.cishome.myapplication/files")
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

    fun whenPlayerFileExist( playerId: String, fileName: String , then: (f:File)->Unit ){
        val dir = File( tm().absolutePath + "/tm/players/${playerId}" , fileName )
        if( dir != null && dir.exists() ){
            then( dir );
        }
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

    fun moduleDir( module: String ): File {
        var m = module.lowercase();
        val dir = File( tm().absolutePath + "/tm/${m}" )
        if( !dir.exists()) dir.mkdirs();
        return dir;
    }

    fun moduleDir( module: String, path: String ): File {
        var m = module.lowercase();
        val dir = File( tm().absolutePath + "/tm/${m}/${path}" )
        if( !dir.exists()) dir.mkdirs();
        return dir;
    }

    fun copy(readBytes: ByteArray, targetFile: File) {
        val out = FileOutputStream( targetFile );
        out.write(readBytes)
        out.close();
    }


}