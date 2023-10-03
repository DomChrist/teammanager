package de.dom.cishome.myapplication.tm.application.services

import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.adapter.out.PlayerRepository
import de.dom.cishome.myapplication.tm.adapter.out.contactperson.ContactpersonPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.domain.contactperson.service.ContactPlayerDomainService
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.service.NewPlayerDomainCommand
import de.dom.cishome.myapplication.tm.application.domain.player.service.PlayerDomainService
import de.dom.cishome.myapplication.tm.application.domain.player.service.RegisterPlayerDomainService
import de.dom.cishome.myapplication.tm.application.port.`in`.DeletePlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.`in`.RegisterPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.`in`.contactperson.CreateContactPersonCommand
import de.dom.cishome.myapplication.tm.application.port.`in`.contactperson.RegisterContactPersonUseCase

class PlayerApplicationService constructor(val repo: PlayerRepository,
                                           private val registerContactPerson: RegisterContactPersonUseCase,
                                           private val deletePlayerUseCase: DeletePlayerUseCase,
                                           private val register: RegisterPlayerUseCase) {
    companion object Factory {

        private var app: PlayerApplicationService? = null;
        fun inject(): PlayerApplicationService {
            var adapter = PlayerPersistenceAdapter.inject();
            var contactAdapter = ContactpersonPersistenceAdapter();
            if( app == null ){
                var repo = PlayerRepository()
                app = PlayerApplicationService(
                    repo,
                    ContactPlayerDomainService( contactAdapter , contactAdapter ),
                    PlayerDomainService.inject(),
                    RegisterPlayerDomainService(  adapter, adapter)
                )
            }
            return app!!;
        }
    }

    fun newPlayer(cmd: NewPlayerDomainCommand , cp: CreateContactPersonCommand):Player {
        var player = this.register.registerPlayer( cmd );
        var contact = this.registerContactPerson.register( player.id , cp );
        this.register.addContactPerson(player.id , contact.id );

        return player;
    }

    fun delete( p: Player ){
        this.deletePlayerUseCase.delete(p);
    }

    fun trialParticipation( pid: String ){

    }

    fun trialEnd( pid: String, takeover: Boolean ){

    }

}