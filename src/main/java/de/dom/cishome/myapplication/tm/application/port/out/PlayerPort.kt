package de.dom.cishome.myapplication.tm.application.port.out

import de.dom.cishome.myapplication.tm.application.domain.player.model.Player


interface CreatePlayerPort{

    fun persist(p: Player)

}

interface UpdatePlayerPort{

    fun update( p: Player )

    fun delete( id: String )

}

interface PlayerReaderPort{

    fun readAll( onSuccess: (list: List<Player>) -> Unit );

    fun readAll( f: PlayerFilter , onSuccess: (list: List<Player>)->Unit );

    fun byId( id: String, onSuccess: (list: Player) -> Unit )

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