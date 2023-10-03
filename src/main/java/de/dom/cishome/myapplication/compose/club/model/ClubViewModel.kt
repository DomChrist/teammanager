package de.dom.cishome.myapplication.compose.club.model

import androidx.lifecycle.ViewModel
import de.dom.cishome.myapplication.tm.application.services.TeamApplicationService

class ClubViewModel( val app: TeamApplicationService) : ViewModel() {


    fun createAllTeams(){
        this.app.allTeams();
    }

}