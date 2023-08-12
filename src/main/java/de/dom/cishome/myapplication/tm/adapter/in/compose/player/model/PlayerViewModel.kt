package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerCommand
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerDetailClick
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerListFilter
import de.dom.cishome.myapplication.tm.adapter.out.PlayerRepository
import de.dom.cishome.myapplication.tm.application.PlayerApplicationService
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.lang.Exception

class PlayerViewModel constructor( var id: String, private var app: PlayerApplicationService ) : ViewModel() {

    var data: MutableLiveData<List<Player>> = MutableLiveData<List<Player>>( emptyList() );
    var team: MutableLiveData<Map<String,List<Player>>> = MutableLiveData(HashMap())

    var player: MutableLiveData<Player?> = MutableLiveData( null );


    init {
        this.loadPlayer( id );
    }


    fun loadPlayers() {
        this.app.repo.asyncPlayers(){
            data.postValue( it );
        }
    }

    fun selectPlayer( p:Player ){
        player.postValue( p );
    }

    fun selectPlayer( id: String ): MutableLiveData<Player?>{
        try{
            this.player.postValue( this.data.value?.first { it.id.equals(id,true) } )
        }catch ( e: Exception){
            null;
        }
        return this.player;
    }

    fun loadPlayer( id: String ){
        this.app.repo.asyncPlayer( id ){
            player.postValue( it );
        }
    }




    companion object Factory {
        fun navItemsBy(p: Player, clicks: PlayerDetailClick): List<PlayerNavItem> {
            return listOf<PlayerNavItem>(
                PlayerNavItem("Ansprechpartner" , "" , click =  {
                    clicks.navTo("player/detail/${p.id}/contacts")
                })
            );
        }
    }


    class PlayerViewFactory( val player: String ) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PlayerViewModel( id=player , PlayerApplicationService.inject() ) as T;
        }
    }

}