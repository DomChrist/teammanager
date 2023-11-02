package de.dom.cishome.myapplication.tm.adapter.compose.player.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.DefaultClickModel
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.service.PlayerDomainService
import de.dom.cishome.myapplication.tm.application.domain.player.service.TrialPlayerDomainService
import java.io.File

class PlayerDetailViewModel: ViewModel() {

    var playerLifecycle: MutableLiveData<PlayerLifecycle> = MutableLiveData( PlayerLifecycle.notStarted() );

    fun load(playerId: String ) {
        PlayerPersistenceAdapter().byId( playerId , onSuccess = {
            this.playerLifecycle.postValue( PlayerLifecycle.successful(it) )
        })
    }

    fun clickModel( click: DefaultClickModel): PlayerDetailClick {
        return PlayerDetailClick(
            back = click.navBack,
            navTo = click.navTo,
            onActivate = { p -> this.trialActivatePlayer(p) },
            onTrialParticipation = {
                count, player -> this.trialParticipationUpdate( count, player )
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

            if( p.isTrial() ){

            } else {
                /*
                add(
                    PlayerNavItem("Spielerpass" , click = {} )
                )
                 */
                /*
                add(
                    PlayerNavItem("Spieler entfernen" , "" , click =  {
                        //deletePlayer.delete( p );
                        clicks.back()
                    })
                )
                 */
            }

        }

    }

    fun isSuccessful(): Boolean = playerLifecycle.value != null && playerLifecycle.value!!.isSuccessful()

    fun trialParticipationUpdate( counter: Int, p: Player) {
        TrialPlayerDomainService().trialParticipation( counter , p ){
            this.load( p.id )
        }
    }

    fun trialActivatePlayer( p: Player ){
        TrialPlayerDomainService().startMembership( p ){
            this.load( p.id );
        }
    }

    fun updateImage( f: File){
        val bytes = f.readBytes();
        val id = playerLifecycle.value?.p?.id ?: return;
        PlayerDomainService().updateImage( id , f );
    }

    data class PlayerLifecycle( var state: Int, var p: Player? ) {

        fun notStarted(): Boolean = state < 100;
        fun inProgress(): Boolean = state in 101..199;

        fun isSuccessful(): Boolean = state in 200 .. 299;

        fun response( player: Player ){
            this.p = player;
            this.state = 200;
        }

        companion object {
            fun notStarted(): PlayerDetailViewModel.PlayerLifecycle? {
                return PlayerLifecycle( 0 , null);
            }

            fun successful( p: Player ): PlayerLifecycle {
                return PlayerLifecycle( 200 , p );
            }
        }
    }

}