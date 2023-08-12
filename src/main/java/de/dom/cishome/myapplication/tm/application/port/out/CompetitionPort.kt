package de.dom.cishome.myapplication.tm.application.port.out

import androidx.compose.runtime.MutableState
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.compose.turnier.model.CompetitionModel
import de.dom.cishome.myapplication.tm.application.domain.competition.TeamCompetitionModel

interface CompetitionReader{

    fun readAll( team: String ): List<TeamCompetitionModel>

    fun readAll( team: String , state: MutableState<List<TeamCompetitionModel>> )

    fun readAll( team: String , onLoad: ( list: List<TeamCompetitionModel> )->Unit )


    fun read( id: String ): TeamCompetitionModel?

}

interface CompetitionWriter{

    fun persist( team: TeamCompetitionModel )

}