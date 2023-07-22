package de.dom.cishome.myapplication.compose.player.model.contact

import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import java.io.File

interface ContactRepository{

    fun readAll(): List<ContactModel>;

    fun readFromPlayer( id: String ): List<ContactModel>


}


class ContactAdapter: PlayerFileHelper(), ContactRepository{
    override fun readAll(): List<ContactModel> {
        var file: File = PlayerFileHelper().storagePath()
        return listOf();
    }


    override fun readFromPlayer( id: String ): List<ContactModel> {
        val playerFile = PlayerFileHelper().playerFile(id, "contacts.json");
        val fromJson = GsonUtils.mapper()
            .fromJson<ContactModelStorage>(playerFile.readText(), ContactModelStorage::class.java)

        return fromJson.list;
    }

    fun previewReadFromPlayer(): List<ContactModel>{

        return listOf<ContactModel>(
            ContactModel("Dominik" , "Christ" , "+49 178 523 71 62"),
            ContactModel("Rebecca" , "Christ" , "+49 178 523 71 62")
        )

    }

}


private data class ContactModelStorage( var list: List<ContactModel> ){

}

data class ContactModel( var givenName: String, var familyName: String, var phone: String ) {
}