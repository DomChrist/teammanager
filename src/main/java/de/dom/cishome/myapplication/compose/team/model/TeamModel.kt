package de.dom.cishome.myapplication.compose.team.model

data class Team( var id: String, var label: String ){

    fun key(): String{
        return label.lowercase().replace(" ","");
    }

}

data class TeamPlayer( var id: String, val givenName: String, var familyName: String ){

}