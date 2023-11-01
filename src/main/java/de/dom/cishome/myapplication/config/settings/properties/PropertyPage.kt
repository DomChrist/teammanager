package de.dom.cishome.myapplication.config.settings.properties

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dom.cishome.myapplication.config.ConfigProperties
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm
import de.dom.cishome.myapplication.ui.MainControl

class PropertyPage( private val mainControl: MainControl = MainControl({},{},{})) {


    @Composable
    fun Screen( model: PropertyViewModel = viewModel() ){

        Scaffold(
            topBar = {Tm.components().TmTopBar(clickModel = mainControl, showBackArrow = true , title = "SETTINGS")},
            bottomBar = { bottomBar() }
        ) {
            Box( modifier = Modifier.padding(it)){
                layout( model.list )
            }
        }

    }

    @Composable
    private fun bottomBar() {
        Button( modifier = Modifier.fillMaxWidth(), onClick = {
            Log.i("Property Value" , "SERVER_PATH: ${ConfigProperties.SERVER_PATH}")
            Log.i("Property Value" , "SERVER_PORT: ${ConfigProperties.SERVER_PORT}")
            ConfigProperties.persist()
            mainControl.back()
        }) {
            Text("OK")
        }
    }


    @Composable()
    fun layout(list: List<PropertyViewModel.PropertyValue>) {

        Column {
            Row{
                LazyColumn(  ){
                    items( list.size , key = {k -> k} ){
                        itemCard( list[it] )
                    }
                }
            }
        }


    }

    @Composable
    fun itemCard(propertyValue: PropertyViewModel.PropertyValue) {
        val view = remember{ mutableStateOf(TextFieldValue(propertyValue.value)) }

        Card( modifier= Modifier
            .padding(5.dp)
            .fillMaxWidth()) {
            Row{
                Column{
                    Text( modifier=Modifier.padding(20.dp), text= propertyValue.key )
                }
                Column {
                    TextField( value = view.value,
                        singleLine = true,
                        onValueChange =  {view.value = it; propertyValue.onChange(view.value.text)} )
                }
            }
        }
    }


}


@Composable
@Preview
fun preview(){
    PropertyPage().Screen();
}