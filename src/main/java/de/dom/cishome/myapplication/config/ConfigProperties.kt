package de.dom.cishome.myapplication.config

import android.content.Context
import android.content.Intent
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class ConfigProperties {


    companion object {

        public var SERVER_PATH: String = ""
        var SERVER_PORT: Int = 8080;
        var AUTH_MODE : AuthMode = AuthMode.LOCAL;


        fun initialize( ctx: Context ){
            val storage = load();
            SERVER_PATH = storage?.serverPath ?: ctx.resources.getString( R.string.server )
            SERVER_PORT = storage?.serverPort ?: ctx.resources.getInteger( R.integer.server_port )
        }


        fun load(): ConfigStorage? {
            try{
                var f = File("/data/user/0/de.dom.cishome.myapplication/files/config.json")
                val fileReader = FileReader(f);
                val json = fileReader.readText();
                fileReader.close();

                val property = GsonUtils.mapper().fromJson(json, ConfigStorage::class.java)
                return property;
            }catch ( e: Exception ){
                return null;
            }
        }

        fun persist() {
            var storage = ConfigStorage(SERVER_PATH, SERVER_PORT);
            var json = GsonUtils.mapper().toJson( storage);
            var f = File("/data/user/0/de.dom.cishome.myapplication/files/config.json")
            val writer = FileWriter(f)
                writer.write(json);
            writer.close();
        }

    }



    public enum class AuthMode{
        LOCAL,REMOTE
    }

}

data class ConfigStorage(
    var serverPath: String,
    var serverPort: Int,
){
}