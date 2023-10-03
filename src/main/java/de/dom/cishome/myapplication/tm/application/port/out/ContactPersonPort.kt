package de.dom.cishome.myapplication.tm.application.port.out

import de.dom.cishome.myapplication.tm.application.domain.contactperson.model.ContactModel

interface ContactReaderPort{

    fun readAll( onSuccess:( list: List<ContactModel> )->Unit );

    fun readFromPlayer( id: String , onSuccess: (list: List<ContactModel>) -> Unit )

    fun readById( id: String , onSuccess: (list: ContactModel) -> Unit )

}

interface ContactWriterPort{

    fun persist( c: ContactModel)

}