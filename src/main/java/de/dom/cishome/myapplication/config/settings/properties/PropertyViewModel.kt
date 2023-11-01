package de.dom.cishome.myapplication.config.settings.properties

import androidx.lifecycle.ViewModel
import de.dom.cishome.myapplication.config.ConfigProperties

class PropertyViewModel : ViewModel() {

    val list: List<PropertyValue> = listOf(
        PropertyValue( "SERVER_URL" , ConfigProperties.SERVER_PATH ) { v -> ConfigProperties.SERVER_PATH = v },
        PropertyValue( "SERVER_PORT" , ConfigProperties.SERVER_PORT.toString() ) { v -> try{ConfigProperties.SERVER_PORT = Integer.getInteger(v)}catch (e: Exception){} },
    );

    data class PropertyValue( val key: String , var value: String, val onChange: ( v: String ) -> Unit )

}