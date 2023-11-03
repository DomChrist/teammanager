package de.dom.cishome.myapplication.tm.adapter.compose.player.notes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dom.cishome.myapplication.tm.adapter.out.player.PlayerNoteResponse

class PlayerNotesPage( val playerId: String ) {


    @Composable fun Screen( view: PlayerNoteViewModel = viewModel() ){

        val dataTrigger = remember{ mutableStateOf<PlayerNoteResponse?>( null ) }
        view.data.observeForever { dataTrigger.value = it; }


        LaunchedEffect(key1 = Unit){
            view.load( playerId )
        }


        if( dataTrigger.value != null ){
            layout( dataTrigger.value!! )
        }

    }

    @Composable
    fun layout(value: PlayerNoteResponse) {


        LazyColumn(){
            items( count = value.notes.size , key = {it} ){
                val item = value.notes[it];
                Card {
                    Text(" ${item.title} ")
                    Divider()
                    Text("${item.description}")
                }
            }
        }

    }

}


@Composable
@Preview
fun preview(){
    val test = PlayerNoteResponse("1234" ,
        listOf(
            PlayerNoteResponse.PlayerNote("1234" , "Trainignsanzug" , "Größe 124")
        )
        )
    PlayerNotesPage("1234").layout(value = test)
}

