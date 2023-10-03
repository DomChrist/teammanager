package de.dom.cishome.myapplication.tm.application.port.`in`.contactperson

import de.dom.cishome.myapplication.tm.application.domain.contactperson.model.ContactModel

interface ContactPersonUseCases {

}

interface RegisterContactPersonUseCase{

    fun register( playerId: String, c: CreateContactPersonCommand) : ContactModel;

}



