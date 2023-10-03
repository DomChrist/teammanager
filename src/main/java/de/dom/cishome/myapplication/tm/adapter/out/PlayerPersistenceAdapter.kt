package de.dom.cishome.myapplication.tm.adapter.out

import android.util.Log
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort
import de.dom.cishome.myapplication.tm.application.port.out.PlayerFilter
import de.dom.cishome.myapplication.tm.application.port.out.PlayerReaderPort
import de.dom.cishome.myapplication.tm.application.port.out.UpdatePlayerPort
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.FileNotFoundException

class PlayerPersistenceAdapter private constructor(): CreatePlayerPort , PlayerReaderPort, UpdatePlayerPort {

    private val helper = PlayerFileHelper();

    companion object CDI{
        var adapter: PlayerPersistenceAdapter? = null;
        fun inject(): PlayerPersistenceAdapter {
            if( adapter == null ) adapter = PlayerPersistenceAdapter();
            return adapter!!;
        }
    }


    override fun persist(p: Player) {

        var file = helper.playerFile( p.key() , "player.json" );
        var idFile = helper.playerFile(p.id , "player.json");

        var json = GsonUtils.mapper().toJson( p );

        //helper.write( file , json );
        helper.write( idFile , json );

        Thread{
            try{
                Log.i("PlayerRepository:Persist","persist player to server")
                val client = OkHttpClient();
                var body = json.toRequestBody( "application/json".toMediaType() )
                var request = Request.Builder()
                    .url("${R.string.server}:8071/players")
                    .post( body = body )
                    .build();
                val newCall = client.newCall(request);
                val response = newCall.execute();
                Log.i("PlayerRepository:Persist" , response.message)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }.start()



    }

    override fun readAll(onSuccess: (list: List<Player>) -> Unit) {
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

    override fun readAll(f: PlayerFilter, onSuccess: (list: List<Player>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun byId(id: String, onSuccess: (list: Player) -> Unit) {
        Thread{
            var file = helper.playerFile(id)
            var json = helper.read(file);
            var player = GsonUtils.mapper().fromJson<Player>( json , Player::class.java );
            onSuccess( player );
        }.start()
    }

    override fun update(p: Player) {
        this.persist(p);
    }

    override fun delete(id: String) {
        var playerDir = helper.playerFile( id );
        playerDir.listFiles().forEach {
            it.delete()
        }
        playerDir.delete();
    }

}