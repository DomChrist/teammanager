package de.dom.cishome.myapplication.tm.application.port.out

import de.dom.cishome.myapplication.compose.team.model.Team

interface TeamReaderPort{

    fun findAll( onSuccess: ( t: List<Team> )->Unit)

    fun findById( id: String, onSuccess: ( t: Team )->Unit)

}

interface TeamWriterPort{
    fun persist( t: Team , onSuccess: ()->Unit )

}