package de.dom.cishome.myapplication.compose.turnier.model

import de.dom.cishome.myapplication.tm.application.domain.shared.TeamReference

data class NewCompetitionCommand(
    var club: String,
    var place: String,
    var date: String,
    var time: String = "11",
    var team: TeamReference = TeamReference("","")
){

}