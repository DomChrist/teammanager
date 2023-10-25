package de.dom.cishome.myapplication.tm.adapter.out.player

import android.util.Log
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.io.FileNotFoundException
import kotlin.Exception

class PlayerFileRepository() {

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
        onSuccess( list );
    }


    fun players( teamId: String ): List<Player> {
        try{
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
        }catch ( e: Exception ){
            return listOf<Player>();
        }
    }

    fun player( playerId: String  , onSuccess: (p: Player) -> Unit ){
        Thread{
            var file = helper.playerFile( playerId )
            var json = helper.read(file);
            var player = GsonUtils.mapper().fromJson<Player>( json , Player::class.java );
            onSuccess( player );
        }.start()
    }

    fun persist( p: Player ){
        var idFile = helper.playerFile(p.id , "player.json");
        var json = GsonUtils.mapper().toJson( p );
        helper.write( idFile , json );
    }





}