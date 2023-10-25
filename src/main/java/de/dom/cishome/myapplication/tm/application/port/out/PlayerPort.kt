package de.dom.cishome.myapplication.tm.application.port.out

import de.dom.cishome.myapplication.config.AsyncResponse
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerListFilter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail


interface CreatePlayerPort{

    fun persist(p: Player)

    fun persistNewTeamPlayer( p: Player , teamId: String , call: AsyncResponse<Player> )

}

interface UpdatePlayerPort{

    fun update( p: Player )

    fun delete( id: String )

    fun create( playerId: String , newContact: PlayerContactDetail , onSuccess: () -> Unit = {} )

}

interface UpdateTrialPlayerPort{

    fun trialParticipation(count: Int, id: String)

    fun startMembership(id: String)

}

interface PlayerReaderPort{

    fun readAll( onSuccess: (list: List<Player>) -> Unit );

    fun readAll( f: PlayerListFilter , onSuccess: (list: List<Player>)->Unit );

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