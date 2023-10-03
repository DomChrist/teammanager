package de.dom.cishome.myapplication.tm.application.services

import de.dom.cishome.myapplication.compose.team.model.Team

class MyTeamApplicationService {

    var selectedTeam: Team? = null;

    fun select(t: Team) {
        this.selectedTeam = t;
    }


}