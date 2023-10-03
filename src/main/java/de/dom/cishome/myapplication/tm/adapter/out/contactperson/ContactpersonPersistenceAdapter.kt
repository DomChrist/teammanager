package de.dom.cishome.myapplication.tm.adapter.out.contactperson

import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.tm.application.domain.contactperson.model.ContactModel
import de.dom.cishome.myapplication.tm.application.port.out.ContactReaderPort
import de.dom.cishome.myapplication.tm.application.port.out.ContactWriterPort
import java.io.Externalizable
import java.io.File

class ContactpersonPersistenceAdapter : ContactReaderPort, ContactWriterPort {

    private val helper = PlayerFileHelper();
    private val module = "contactperson/data";

    override fun readAll(onSuccess: (list: List<ContactModel>) -> Unit) {
        var dir = helper.moduleDir( module = module );
        if( !dir.exists() ) return;
        val list = dir.listFiles()
            .filter { it.isDirectory }
            .map {
                val file = File(it , "contactperson.json")
                GsonUtils.mapper().fromJson( helper.read(file) , ContactModel::class.java )
            }
        onSuccess( list );
    }

    override fun readFromPlayer(id: String, onSuccess: (list: List<ContactModel>) -> Unit) {

    }

    override fun readById(id: String, onSuccess: (list: ContactModel) -> Unit) {
        try{
            var file = File( helper.moduleDir(module) , "${id}/contactperson.json" );
            if( !file.exists() ) return;
            val model = GsonUtils.mapper().fromJson<ContactModel>( helper.read(file) , ContactModel::class.java );
            onSuccess( model );
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    override fun persist(c: ContactModel) {
        var dir = File(helper.moduleDir(module = module) , "${c.id}");
        if( !dir.exists() ){
            dir.mkdirs();
        }
        var file = File( dir , "contactperson.json");
        var json = GsonUtils.mapper().toJson( c );
        helper.write( file , json );
    }


}