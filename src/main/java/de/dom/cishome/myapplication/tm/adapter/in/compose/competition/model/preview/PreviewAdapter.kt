package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.preview

import androidx.compose.runtime.MutableState
import de.dom.cishome.myapplication.compose.turnier.model.CompetitionModel
import de.dom.cishome.myapplication.tm.application.domain.competition.Club
import de.dom.cishome.myapplication.tm.application.domain.competition.CompetitionDate
import de.dom.cishome.myapplication.tm.application.domain.competition.TeamCompetitionModel
import de.dom.cishome.myapplication.tm.application.domain.shared.TeamReference
import de.dom.cishome.myapplication.tm.application.port.out.CompetitionReader
import de.dom.cishome.myapplication.tm.application.port.out.CompetitionWriter
import java.time.LocalDateTime


class PreviewCompetitionRepository : CompetitionReader, CompetitionWriter {
    override fun readAll(team: String): List<TeamCompetitionModel> {
        return listOf<TeamCompetitionModel>(
            TeamCompetitionModel( "1234" , Club("Ketting" , "Kunstrasen") , CompetitionDate("01.08.2023" , "11 Uhr") , TeamReference("b","b")  ),
            TeamCompetitionModel( "1234" , Club("Ketting" , "Kunstrasen") , CompetitionDate("17.09.2023" , "14 Uhr"), TeamReference("b","b")  ),
            TeamCompetitionModel( "1234" , Club("Ketting" , "Kunstrasen") , CompetitionDate("21.10.2023" , "17 Uhr"), TeamReference("b","b")  ),
        )
    }

    override fun readAll(team: String, state: MutableState<List<TeamCompetitionModel>>) {
        state.value = readAll(team);
    }

    override fun readAll(team: String, onLoad: (list: List<TeamCompetitionModel>) -> Unit) {
        onLoad(
            readAll( team )
        )
    }

    override fun read(id: String): TeamCompetitionModel? {
        return readAll(id)[0]
    }

    override fun persist(team: TeamCompetitionModel) {
    }

}
