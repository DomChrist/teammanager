package de.dom.cishome.myapplication.tm.application.services

import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.compose.team.model.TeamRepository
import java.util.UUID

class TeamApplicationService {

    companion object Factory {
        private var app: TeamApplicationService? = null;

        fun inject(): TeamApplicationService? {
            if( app == null ){
                app = TeamApplicationService();
            }
            return app;
        }

    }


    fun all( onLoad: (list: List<Team>)->Unit ){
        Thread{
            onLoad( TeamRepository().readAll() );
        }.start()
    }

    fun allTeams(){
        val repository = TeamRepository();
        listOf<Team>(
            Team(UUID.randomUUID().toString() , "Bambini"),
            Team(UUID.randomUUID().toString() , "F"),
            Team(UUID.randomUUID().toString() , "E"),
            Team(UUID.randomUUID().toString() , "D"),
            Team(UUID.randomUUID().toString() , "C"),
            Team(UUID.randomUUID().toString() , "B"),
            Team(UUID.randomUUID().toString() , "A"),
        ).forEach{
            repository.write( it );
        }
    }


}