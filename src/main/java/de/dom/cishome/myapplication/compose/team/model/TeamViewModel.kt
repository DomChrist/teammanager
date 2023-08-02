package de.dom.cishome.myapplication.compose.team.model

import androidx.lifecycle.ViewModel
import java.util.UUID

class TeamViewModel(var repo: TeamPersistencePort ) : ViewModel() {

    var selected: Team? = null;

    fun handle( c: CreateTeamCommand , onSuccess:()->Unit ){
        var team = Team( UUID.randomUUID().toString() , c.name );
        Thread{
            Thread.sleep(2000)
            this.repo.write( team );
            onSuccess()
        }.start();
    }

}