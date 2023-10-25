package de.dom.cishome.myapplication.tm.application.services

import android.content.Context
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.service.NewPlayerDomainCommand
import de.dom.cishome.myapplication.tm.application.domain.player.service.PlayerDomainService
import de.dom.cishome.myapplication.tm.application.domain.player.service.RegisterPlayerDomainService
import de.dom.cishome.myapplication.tm.application.port.`in`.DeletePlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.`in`.RegisterPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.`in`.contactperson.CreateContactPersonCommand

class PlayerApplicationService constructor(val ctx: Context ) {

    private val adapter: PlayerPersistenceAdapter = PlayerPersistenceAdapter();
    private val deletePlayerUseCase: DeletePlayerUseCase;
    private val registerUseCase: RegisterPlayerUseCase;

    init {
        this.deletePlayerUseCase = PlayerDomainService( ctx , adapter )
        this.registerUseCase = RegisterPlayerDomainService( adapter , adapter )
    }

    fun newPlayer(cmd: NewPlayerDomainCommand , cp: CreateContactPersonCommand):Player {
        var player = this.registerUseCase.registerPlayer( cmd );
        return player;
    }

    fun delete( p: Player ){
        this.deletePlayerUseCase.delete(p);
    }

    fun trialParticipation(player: Player, count: Int){
        PlayerPersistenceAdapter().trialParticipation( count, player.id )
        player.state.trial?.trialCount = player.state.trial?.trialCount?.inc() ?: 0
    }

    fun trialEnd( pid: String, takeover: Boolean ){

    }

}