package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerDetailClick
import de.dom.cishome.myapplication.tm.application.services.PlayerApplicationService
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.lang.Exception

class PlayerViewModel constructor( var id: String, private var app: PlayerApplicationService) : ViewModel() {

    var data: MutableLiveData<List<Player>> = MutableLiveData<List<Player>>( emptyList() );
    var team: MutableLiveData<Map<String,List<Player>>> = MutableLiveData(HashMap())

    var player: MutableLiveData<Player?> = MutableLiveData( null );


    init {
        this.loadPlayer( id );
    }

    fun navItemsBy(p: Player, clicks: PlayerDetailClick): List<PlayerNavItem> {
        return listOf<PlayerNavItem>(
            PlayerNavItem("Ansprechpartner" , "" , click =  {
                clicks.navTo("player/detail/${p.id}/contacts")
            }),
            PlayerNavItem("Spieler entfernen" , "" , click =  {
                this.app.delete( p );
                clicks.back()
            })
        );
    }

    fun loadPlayer( id: String ){
        this.app.repo.asyncPlayer( id ){
            player.postValue( it );
        }
    }

    fun viewUpdate( p: Player ){
        this.player.postValue(p);
    }


    class PlayerViewFactory( val player: String ) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PlayerViewModel( id=player , PlayerApplicationService.inject() ) as T;
        }
    }

}