package de.dom.cishome.myapplication.tm.application.domain.player.service

import androidx.compose.runtime.Composable
import de.dom.cishome.myapplication.config.AsyncResponse
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.port.`in`.CreatePlayerCommand
import de.dom.cishome.myapplication.tm.application.port.`in`.RegisterPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort
import de.dom.cishome.myapplication.tm.application.port.out.PlayerReaderPort
import java.time.LocalDate

class RegisterPlayerDomainService(
    private val adapter: PlayerPersistenceAdapter = PlayerPersistenceAdapter(),
) : RegisterPlayerUseCase {

    override fun registerPlayer(cmd: NewPlayerDomainCommand , onSuccess: () -> Unit): Player {
        val state = if( cmd.trail ) Player.MemberState(Player.MemberState.Trial(LocalDate.now(), 0), null )
            else Player.MemberState( null , Player.MemberState.Active(true))
        val player = Player(
            cmd.id,
            cmd.givenName,
            cmd.familyName,
            cmd.dateOfBirth,
            cmd.team,
            listOf<String>(),
            state
        )
        adapter.persistNewTeamPlayer(player, cmd.team , AsyncResponse<Player>( { onSuccess()} , {} ) )
        return player;
    }

    override fun addContactPerson(playerId: String, contactId: String) {
        adapter.byId(playerId) {
            //it.contactPersons = it.contactPersons.plus(contactId)
            adapter.persist(it);
        }
    }


}