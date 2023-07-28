package de.dom.cishome.myapplication.compose.team.model

import androidx.lifecycle.ViewModel
import java.util.UUID

class TeamViewModel(var repo: TeamPersistencePort ) : ViewModel() {

    var selected: Team? = null;

    fun handle( c: CreateTeamCommand ){
        var team = Team( UUID.randomUUID().toString() , c.name );
        this.repo.write( team );
    }

}