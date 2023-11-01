package de.dom.cishome.myapplication.tm.application.domain.player.service

import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.port.`in`.TrialPlayerUseCase

class TrialPlayerDomainService : TrialPlayerUseCase {

    private val adapter = PlayerPersistenceAdapter();


    override fun trialParticipation( count: Int, p: Player, onSuccess: () -> Unit) {
        Thread{
            this.adapter.trialParticipation( count , p.id );
            onSuccess();
        }.start()
    }

    override fun startMembership(p: Player, onSuccess: () -> Unit) {
        Thread{
            this.adapter.startMembership( p.id )
            onSuccess();
        }.start()
    }


}