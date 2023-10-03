package de.dom.cishome.myapplication.tm.application.domain.competition

import de.dom.cishome.myapplication.tm.application.domain.shared.TeamReference

data class NewCompetitionCommand(
    var club: String,
    var place: String,
    var date: String,
    var time: String = "11",
    var team: TeamReference = TeamReference("","")
){

}