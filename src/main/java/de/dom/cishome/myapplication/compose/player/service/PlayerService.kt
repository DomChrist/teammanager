package de.dom.cishome.myapplication.compose.player.service

import de.dom.cishome.myapplication.compose.player.model.player.PlayerRepository
import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

class PlayerService( val rep: PlayerRepository? ) {

    private var data: List<Player> = listOf();
    var selected: Player? = null;

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

    fun select( p: Player ){
        this.selected = p;
    }

    fun startMembership(p: Player) {
        this.data.filter { p.id == it.id }.forEach{
            it.trial = false
            this.rep!!.write( p );
        }
    }

    fun updateTrialCount( count: Int , p: Player){

    }

}


data class Player( val id: String = UUID.randomUUID().toString(), val givenName: String, val familyName: String, val dateOfBirth: LocalDate,
var team: String, var trial: Boolean = false): Serializable{
    fun fullName(): String {
        return this.givenName + " " + this.familyName;
    }

}