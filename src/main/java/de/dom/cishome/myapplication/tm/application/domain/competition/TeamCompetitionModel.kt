package de.dom.cishome.myapplication.tm.application.domain.competition

import de.dom.cishome.myapplication.compose.turnier.model.CompetitionModel
import de.dom.cishome.myapplication.tm.application.domain.shared.TeamReference
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TeamCompetitionModel( val id: String , val club: Club , val date: CompetitionDate , val team: TeamReference  ) {
    fun isTeam(tId: String): Boolean {
        if( team != null && team.id != null ){
            return team.id.equals(tId,true)
        }
        return false;
    }

    fun dateFormated(): String {
        if( date != null && date.date != null ){
            return date.format();
        } else {
            return "Unknown"
        }
    }
}

data class Club( val clubName: String , val location: String )

data class CompetitionDate( val date: String , val time: String ) {
    init {
        LocalDate.parse(date.trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    fun format(): String {
        try{
            if( date == null ) return "";
            return date.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
        }catch (e: Exception){
            return "";
        }
    }
};

