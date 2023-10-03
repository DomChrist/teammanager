package de.dom.cishome.myapplication.tm.application.services

import de.dom.cishome.myapplication.tm.adapter.out.contactperson.ContactpersonPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.port.out.ContactReaderPort
import de.dom.cishome.myapplication.tm.application.port.out.ContactWriterPort

class ContactPersonApplicationService private constructor(
    val reader: ContactReaderPort,
    val writer: ContactWriterPort
){

    companion object Singleton {

        var service: ContactPersonApplicationService? = null;

        fun inject(): ContactPersonApplicationService {
            if( service == null ){
                val adapter = ContactpersonPersistenceAdapter();
                service = ContactPersonApplicationService( adapter , adapter );
            }
            return service!!;
        }

    }




}