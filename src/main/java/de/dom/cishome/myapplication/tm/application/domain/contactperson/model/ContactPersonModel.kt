package de.dom.cishome.myapplication.tm.application.domain.contactperson.model


data class ContactModel( var id: String, var givenName: String, var familyName: String, var phone: String ) {

    fun initial(): String {
        var firstOfGiven = givenName[0]
        var firstOfLast = familyName[0]
        return "${firstOfGiven}.${firstOfLast}".uppercase();
    }

    fun full(): String {
        return "${givenName} ${familyName}";
    }

}