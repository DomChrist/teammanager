package de.dom.cishome.myapplication.tm.application.domain.player.service

import java.time.LocalDate

data class NewPlayerDomainCommand( var id: String,
    var givenName: String,
    var familyName: String,
    var dateOfBirth: LocalDate,
    var team: String,
    var trail: Boolean = false,
    var member: Boolean = false
)