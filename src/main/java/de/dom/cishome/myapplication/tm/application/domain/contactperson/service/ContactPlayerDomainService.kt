package de.dom.cishome.myapplication.tm.application.domain.contactperson.service

import de.dom.cishome.myapplication.tm.application.domain.contactperson.model.ContactModel
import de.dom.cishome.myapplication.tm.application.port.`in`.contactperson.CreateContactPersonCommand
import de.dom.cishome.myapplication.tm.application.port.`in`.contactperson.RegisterContactPersonUseCase
import de.dom.cishome.myapplication.tm.application.port.out.ContactReaderPort
import de.dom.cishome.myapplication.tm.application.port.out.ContactWriterPort

class ContactPlayerDomainService(
    private var reader: ContactReaderPort,
    private var writer: ContactWriterPort
) : RegisterContactPersonUseCase {

    override fun register(playerId: String, c: CreateContactPersonCommand): ContactModel {
        var model = c.toModel();
        writer.persist(model);
        return model;
    }

}