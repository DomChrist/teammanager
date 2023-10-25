package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerDetailClick
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.DefaultClickModel
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.services.PlayerApplicationService
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.service.PlayerDomainService
import de.dom.cishome.myapplication.tm.application.domain.player.service.TrialPlayerDomainService
import de.dom.cishome.myapplication.tm.application.port.`in`.DeletePlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.`in`.TrialPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.PlayerReaderPort

class PlayerViewModel constructor( var ctx: Context, var id: String,
                                   private var deletePlayer: DeletePlayerUseCase,
                                   var trialUseCase: TrialPlayerUseCase = TrialPlayerDomainService(),
                                   private var playerReaderPort: PlayerReaderPort) : ViewModel() {

    var data: MutableLiveData<List<Player>> = MutableLiveData<List<Player>>( emptyList() );
    var team: MutableLiveData<Map<String,List<Player>>> = MutableLiveData(HashMap())
    var clicks: PlayerDetailClick? = null;

    var player: MutableLiveData<Player?> = MutableLiveData( null );

    init {
        this.initializeModel( id );
    }

    private fun initializeModel( id: String ){
        this.loadPlayer( id ){

        }
    }

    fun clickModel( click: DefaultClickModel ): PlayerDetailClick {
        return PlayerDetailClick(
            back = click.navBack,
            navTo = click.navTo,
            onActivate = {
                player.value.let {
                    this.trialUseCase.startMembership( p = this.player.value!! )
                }
            }
        )
    }



    fun navItemsBy(p: Player, clicks: PlayerDetailClick): List<PlayerNavItem> {

        return mutableListOf<PlayerNavItem>().apply {
            add(
                PlayerNavItem("Kontaktdaten" , "" , click =  {
                    clicks.navTo("player/detail/${p.id}/contacts")
                })
            )

            /*
            add(
                PlayerNavItem("Spieler entfernen" , "" , click =  {
                    deletePlayer.delete( p );
                    clicks.back()
                })
            )
            */

            if( p.isTrial() ){
                add(
                    PlayerNavItem("Spieler aktivieren" , "" , click =  {
                        clicks.onActivate( p )
                        loadPlayer( id )
                    })
                )
            } else {
                add(
                    PlayerNavItem("Spielerpass" , click = {} )
                )
                add(
                    PlayerNavItem("Spieler entfernen" , "" , click =  {
                        deletePlayer.delete( p );
                        clicks.back()
                    })
                )
            }

        }

    }

    fun loadPlayer( id: String , onSuccess: (p:Player)->Unit = {} ){
        playerReaderPort.byId( id ){
            this.player.postValue( it );
        }
    }

    fun trialParticipation( count: Int ){
        player.value.let {
            PlayerApplicationService(ctx).trialParticipation( player.value!! , count )
        }
    }

    fun viewUpdate( p: Player ){
        this.player.postValue(p);
    }


    class PlayerViewFactory(val player: String, val ctx: Context) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val adapter = PlayerPersistenceAdapter();
            val deletePlayerUseCase = PlayerDomainService( ctx , adapter )
            return PlayerViewModel( ctx , id=player , deletePlayerUseCase, playerReaderPort = adapter) as T;
        }
    }

}