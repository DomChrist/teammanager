package de.dom.cishome.myapplication.tm.application.domain.player.service

import androidx.compose.runtime.Composable
import de.dom.cishome.myapplication.config.AsyncResponse
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.port.`in`.CreatePlayerCommand
import de.dom.cishome.myapplication.tm.application.port.`in`.RegisterPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort
import de.dom.cishome.myapplication.tm.application.port.out.PlayerReaderPort
import java.time.LocalDate

class RegisterPlayerDomainService(
    private val reader: PlayerReaderPort,
    private val out: CreatePlayerPort
) : RegisterPlayerUseCase {

    override fun registerPlayer(cmd: NewPlayerDomainCommand): Player {
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
        out.persistNewTeamPlayer(player, cmd.team , AsyncResponse<Player>( {} , {} ) )
        return player;
    }

    override fun addContactPerson(playerId: String, contactId: String) {
        reader.byId(playerId) {
            //it.contactPersons = it.contactPersons.plus(contactId)
            out.persist(it);
        }
    }


}