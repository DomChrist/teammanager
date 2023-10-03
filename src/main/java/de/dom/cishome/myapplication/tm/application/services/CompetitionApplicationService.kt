package de.dom.cishome.myapplication.tm.application.services

import de.dom.cishome.myapplication.tm.application.domain.competition.Club
import de.dom.cishome.myapplication.tm.application.domain.competition.CompetitionDate
import de.dom.cishome.myapplication.tm.application.domain.competition.NewCompetitionCommand
import de.dom.cishome.myapplication.tm.application.domain.competition.TeamCompetitionModel
import de.dom.cishome.myapplication.tm.application.port.out.CompetitionReader
import de.dom.cishome.myapplication.tm.application.port.out.CompetitionWriter
import java.time.LocalDateTime
import java.util.UUID

class CompetitionApplicationService( private var writer: CompetitionWriter , var reader: CompetitionReader) {
    fun handle(it: NewCompetitionCommand): TeamCompetitionModel {
        var model = TeamCompetitionModel(
            UUID.randomUUID().toString(),
            Club( it.club , it.place ),
            CompetitionDate( it.date , it.time ),
            it.team
        )
        writer.persist(  model );
        return model;
    }


}