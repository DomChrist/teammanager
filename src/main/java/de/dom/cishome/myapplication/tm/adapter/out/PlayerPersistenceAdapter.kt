package de.dom.cishome.myapplication.tm.adapter.out

import android.util.Log
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.config.AsyncResponse
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerListFilter
import de.dom.cishome.myapplication.tm.adapter.out.myteam.TeamRestApi
import de.dom.cishome.myapplication.tm.adapter.out.player.PlayerFileRepository
import de.dom.cishome.myapplication.tm.adapter.out.player.PlayerRestCall
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort
import de.dom.cishome.myapplication.tm.application.port.out.PlayerReaderPort
import de.dom.cishome.myapplication.tm.application.port.out.UpdatePlayerPort
import de.dom.cishome.myapplication.tm.application.port.out.UpdateTrialPlayerPort
import java.io.FileNotFoundException

class PlayerPersistenceAdapter constructor() : CreatePlayerPort , PlayerReaderPort, UpdatePlayerPort ,
    UpdateTrialPlayerPort {

    private val helper = PlayerFileHelper();
    private val fileRepository: PlayerFileRepository = PlayerFileRepository();
    private val playerRestCall: PlayerRestCall = PlayerRestCall()

    companion object {
        var adapter: PlayerPersistenceAdapter? = null;
        fun inject(): PlayerPersistenceAdapter {
            if( adapter == null ) adapter = PlayerPersistenceAdapter();
            return adapter!!;
        }
    }


    override fun persist(p: Player) {
        try{
            this.fileRepository.persist( p );
            Thread{
                TeamRestApi()
                    .addNewTeamPlayer( p.team , p )
                    .map();
            }.start()
        }catch ( e: Exception ){
            e.printStackTrace();
        }
    }

    override fun persistNewTeamPlayer(p: Player, teamId: String , call: AsyncResponse<Player>) {
        try{
            Thread{
                TeamRestApi()
                    .addNewTeamPlayer( p.team , p )
                    .map();
                this.fileRepository.persist( p );
            }.start()
        }catch ( e: Exception ){
            e.printStackTrace();
        }
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

    override fun readAll(f: PlayerListFilter, onSuccess: (list: List<Player>) -> Unit) {
        Thread{
            try{
                val list: List<Player> = this.playerRestCall.playersInTeam( f.value )?.map { e -> e.map() }
                    ?: this.fileRepository.players()
                list.let(onSuccess)
            }catch ( e: Exception){
                onSuccess( listOf() );
            }

        }.start()
    }

    override fun byId(id: String, onSuccess: (list: Player) -> Unit) {
        Thread{
            var player = remoteFindById(id) ?: localFindById(id)

            player?.let {
                PlayerFileRepository().persist(it)
                onSuccess(it)
            }
        }.start()
    }

    override fun readCommunications(
        playerId: String,
        onSuccess: (list: List<PlayerContactDetail>) -> Unit
    ) {
        Thread{
            val list = PlayerRestCall().playerCommunications( playerId )
                .map { PlayerContactDetail( it.id , it.description , it.value ) }
            onSuccess( list )
        }.start()
    }

    override fun create(playerId: String, newContact: PlayerContactDetail, onSuccess: () -> Unit) {
        Thread{
            PlayerRestCall().playerCommunications( playerId = playerId , newContact );
            onSuccess();
        }.start()
    }


    private fun localFindById( id: String ): Player? {
        var file = helper.playerFile(id)
        var json = helper.read(file);
        var player = GsonUtils.mapper().fromJson<Player>( json , Player::class.java );
        return player;
    }

    private fun remoteFindById( id: String ): Player? {
        return PlayerRestCall().playerSync( id )?.map() ?: null;
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



    override fun trialParticipation(count: Int, id: String) {
        Thread{
            PlayerRestCall().trialParticipation( count , id );
        }.start()
    }

    override fun startMembership(id: String) {
        Thread{
            PlayerRestCall().activateMember( id );
        }.start()
    }




}