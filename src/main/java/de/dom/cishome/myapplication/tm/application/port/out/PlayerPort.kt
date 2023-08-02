package de.dom.cishome.myapplication.tm.application.port.out

import de.dom.cishome.myapplication.tm.application.domain.model.Player


interface CreatePlayerPort{

    fun persist(p: Player)

}