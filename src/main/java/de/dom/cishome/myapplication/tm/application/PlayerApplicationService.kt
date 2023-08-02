package de.dom.cishome.myapplication.tm.application

import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.adapter.out.PlayerRepository
import de.dom.cishome.myapplication.tm.application.domain.service.NewPlayerDomainCommand
import de.dom.cishome.myapplication.tm.application.domain.service.RegisterPlayerDomainService
import de.dom.cishome.myapplication.tm.application.port.`in`.RegisterPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort

class PlayerApplicationService constructor(val repo: PlayerRepository,
                                           private val register: RegisterPlayerUseCase) {

    companion object Factory {

        private var app: PlayerApplicationService? = null;
        fun inject(): PlayerApplicationService {
            var adapter = PlayerPersistenceAdapter();
            if( app == null ){
                app = PlayerApplicationService(
                    PlayerRepository(),
                    RegisterPlayerDomainService(adapter))
            }
            return app!!;
        }
    }

    fun newPlayer(cmd: NewPlayerDomainCommand) {
        this.register.registerPlayer( cmd );
    }

    fun trialParticipation( pid: String ){

    }

    fun trialEnd( pid: String, takeover: Boolean ){

    }

}