package de.dom.cishome.myapplication.tm.application.port.out

import de.dom.cishome.myapplication.config.AsyncResponse
import de.dom.cishome.myapplication.tm.adapter.compose.player.shared.PlayerListFilter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayersTeamModel
import java.io.File


interface CreatePlayerPort{

    fun persist(p: Player)

    fun persistNewTeamPlayer( p: Player , teamId: String , call: AsyncResponse<Player> )

}

interface UpdatePlayerPort{

    fun update( p: Player )

    fun delete( id: String )

    fun create( playerId: String , newContact: PlayerContactDetail , onSuccess: () -> Unit = {} )

    fun updateImage( playerId: String , imageFile: File)

}

interface UpdateTrialPlayerPort{

    fun trialParticipation(count: Int, id: String)

    fun startMembership(id: String)

}

interface PlayerReaderPort{

    @Deprecated(message = "deprecated, replace with readAll(PlayerListFilter)")
    fun readAll( onSuccess: (list: List<Player>) -> Unit );

    fun readAll(f: PlayerListFilter, onSuccess: (response: PlayersTeamModel)->Unit );

    fun byId( id: String, onSuccess: (list: Player) -> Unit )

    fun readCommunications( playerId: String , onSuccess: (list: List<PlayerContactDetail>) -> Unit = {} )

}

data class PlayerFilter( val filter: String ){

    companion object{

        fun ALL(): PlayerFilter {
            return PlayerFilter("");
        }
        fun team( id: String): PlayerFilter {
            return PlayerFilter("");
        }

    }

}