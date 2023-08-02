package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerCommand
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.tm.application.PlayerApplicationService
import de.dom.cishome.myapplication.tm.application.domain.model.Player
import java.lang.Exception

class PlayerViewModel constructor( private var app: PlayerApplicationService ) : ViewModel() {

    private var playersCounter = 0;
    var myPlayers: List<Player> = emptyList();

    fun players(): List<Player> {
        if( myPlayers.isEmpty() || playersCounter == 0 ){
            this.myPlayers = this.app.repo.players();
            this.playersCounter++;
        }
        return this.myPlayers;
    }

    fun player( id: String ): Player?{
        return try{
            this.players().first { it.id.equals(id,true) }
        }catch ( e: Exception){
            null;
        }
    }

    fun teamPlayers( teamFilter: String ): List<Player> {
        return players().filter { it.team.equals(teamFilter,true) }
    }

    fun navItems(){
    }

    fun handle( cmd: NewPlayerCommand) {
        var domainCommand = cmd.toDomainCommand();
        var p = this.app.newPlayer( domainCommand );
        this.myPlayers.plus( p );
    }

    companion object Factory {
        fun navItemsBy( p: Player , nav: NavController): List<PlayerNavItem> {
            return listOf<PlayerNavItem>(
                PlayerNavItem("Ansprechpartner" , "" , click =  {
                    nav.navigate("player/detail/${p.id}/contacts")
                })
            );
        }
    }

}