package de.dom.cishome.myapplication.compose.player.service

import com.google.gson.Gson
import de.dom.cishome.myapplication.compose.shared.PlayerRepository
import java.io.Serializable
import java.time.LocalDate
import java.util.Date
import java.util.UUID

class PlayerService( val rep: PlayerRepository? ) {

    private var data: List<Player> = listOf();

    fun player(): List<Player>{
        if( this.data.isEmpty() ){
            this.data = this.rep!!.players();
        }
        return this.data;
    }

    fun add( p: Player ){
        if( this.rep != null ){
            this.rep.write( p )
        }
        this.data = this.data.plus( p );
    }

}


data class Player( val id: String = UUID.randomUUID().toString(), val givenName: String, val familyName: String, val dateOfBirth: LocalDate,
val trial: Boolean = false): Serializable{
    fun fullName(): String {
        return this.givenName + " " + this.familyName;
    }

}