package de.dom.cishome.myapplication.tm.adapter.out

import android.content.Context
import android.util.Log
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.config.CDI
import de.dom.cishome.myapplication.tm.adapter.out.player.PlayerRestCall
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort
import java.io.FileNotFoundException
import java.lang.Exception

@Deprecated(message = "Use the specific repository")
class PlayerRepository() {

    private val helper = PlayerFileHelper();

    fun players(): List<Player> {
        val file = helper.playersDir()!!;

        val list = file.listFiles()?.filter { it.exists() && it.isDirectory }
            ?.map {
                var player: Player? = null;
                try{
                    val playerfile = helper.playerFile(it.name);
                    var txt = helper.read( playerfile );
                    player = GsonUtils.mapper().fromJson( txt , Player::class.java );
                }catch ( e: Exception){
                    if( e is FileNotFoundException){
                        Log.e("FileNotFound" , "File for player ${player?.id ?: "(noId)"} not found")
                    } else {
                        e.printStackTrace()
                    }
                }
                player;
            }?.filterNotNull() ?: listOf()

        return list;
    }

    fun players( teamId: String, onSuccess: (p: List<Player>) -> Unit ){
        Thread{
        }.start()
    }

    fun player( playerId: String  , onSuccess: (p: Player) -> Unit ){
        Thread{
        }.start()
    }

    fun asyncPlayers( onSuccess: (p: List<Player>) -> Unit ){
        Thread{
            var list = players();
            onSuccess( list );
        }.start();

    }

    fun asyncPlayer( id: String , onSuccess: (p: Player) -> Unit ){
        Thread{
            var file = helper.playerFile(id)
            var json = helper.read(file);
            var player = GsonUtils.mapper().fromJson<Player>( json , Player::class.java );
            onSuccess( player );
        }.start()
    }



}