package de.dom.cishome.myapplication.compose.player.model.player

import android.util.Log
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.compose.player.service.Player
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import java.io.FileNotFoundException
import java.io.FileReader
import java.lang.Exception


interface PlayerRepository{

    fun players(): List<Player>;

    fun playersByTeam( team: String ): List<Player> ;


    fun write(p: Player)

}

@ExperimentalPermissionsApi
class FileRep() : PlayerRepository {

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

    override fun playersByTeam(team: String): List<Player> {
        return this.players().filter { it.team.equals(team,true) }
    }

    override fun write(p: Player) {
        try {
            val helper = PlayerFileHelper();
            val toJson = GsonUtils.mapper().toJson( p );

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

