package de.dom.cishome.myapplication.tm.application.port.`in`.contactperson

import de.dom.cishome.myapplication.tm.application.domain.contactperson.model.ContactModel
import java.util.UUID

data class CreateContactPersonCommand(
    val id: String = UUID.randomUUID().toString(),
    var forPlayer: String,
    val givenName: String,
    val familyName: String,
    val contactNumber: String,
    val member: Boolean = false
){

    fun toModel(): ContactModel {
        return ContactModel( id , givenName , familyName , contactNumber );
    }

}