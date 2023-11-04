package de.dom.cishome.myapplication.tm.adapter.compose.player.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.pages.NavBarItem
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.DefaultClickModel
import de.dom.cishome.myapplication.tm.adapter.out.player.PlayerNoteResponse

class PlayerNotesPage( val playerId: String, val defaultClickModel: DefaultClickModel ) {

    private var onDeleteNote: ( noteId: String )->Unit = {}

    @Composable fun Screen( view: PlayerNoteViewModel = viewModel() ){

        val dataTrigger = remember{ mutableStateOf<PlayerNoteResponse?>( null ) }
        view.data.observeForever { dataTrigger.value = it; }


        LaunchedEffect(key1 = Unit){
            view.load( playerId )
        }

        if( dataTrigger.value != null ){
            layout( view, dataTrigger.value!! )
        }

    }

    @Composable
    fun layout(view: PlayerNoteViewModel, value: PlayerNoteResponse) {
        val showAddNoteDialog = remember {
            mutableStateOf(false)
        }
        onDeleteNote = { view.delete(it) }

        AddNoteComponent(
            showAddNoteDialog.value,
            { title, txt -> view.add( title,txt ) },
            { showAddNoteDialog.value = false }
        )

        Scaffold(
            modifier = Modifier.background(Color.Green),
            topBar = {header( {defaultClickModel.navBack()} )},
            bottomBar = { footer{ showAddNoteDialog.value = true} },
            content = {Box(
                Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color(205, 205, 205))){content(value)}}
        )


    }

    @Composable fun content(value: PlayerNoteResponse) {
        LazyColumn(){
            items( count = value.notes.size , key = {it} ){
                val item = value.notes[it];
                Note(item)
            }
        }
    }


    @Composable fun Note(item: PlayerNoteResponse.PlayerNote) {
        var cardColors = CardDefaults.cardColors( Color.White,Color.White,Color.White,Color.White );
        var textColor = Color.Black;
        Card(Modifier.padding(5.dp) , colors = cardColors, shape = CardDefaults.elevatedShape) {
            Column(Modifier.padding(5.dp)){
                Text(" ${item.title} ",
                    modifier=Modifier.padding(5.dp),
                    color=textColor,
                    fontSize = TextUnit(3.9f, TextUnitType.Em),
                    fontWeight = FontWeight.Bold)
                Divider()
                Text("${item.description}",
                    color=textColor,
                    modifier=Modifier.padding(5.dp),
                    maxLines = 2)
            }
            Column( modifier=Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                var btnColors = ButtonDefaults.buttonColors(Color.Red,Color.White)
                Button( colors = btnColors, onClick = {onDeleteNote(item.id)} ){
                    Icon(Icons.Filled.Delete , contentDescription = "")
                }
            }
        }
    }

    @Composable
    private fun header(onBackClick: () -> Unit) {
        var title = "Notes"
        var color = TmColors.App;
        var defaults = TopAppBarDefaults.topAppBarColors( containerColor = color.primary )
        TopAppBar(
            title = { Text(title , color=color.primaryText) },
            navigationIcon = {
                IconButton(onClick = onBackClick ) {
                    Icon(Icons.Filled.ArrowBack, tint=color.primaryText, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.Menu, tint=color.primaryText, contentDescription = "Localized description")
                }
            },
            colors = defaults
        )
    }

    @Composable
    private fun footer( onNoteAddClick: () -> Unit ){

        NavBarItem("", Icon(imageVector = Icons.Filled.Menu, tint = TmColors.secondaryColor, contentDescription = "Localized description"), click = {} )

        NavigationBar( contentColor = TmColors.primaryColor , containerColor = TmColors.primaryColor ) {
            NavigationBarItem(selected = false,
                onClick = { onNoteAddClick() },
                icon = { Icon(Icons.Filled.Add, tint= TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("ADD" , color= TmColors.secondaryColor) }
            )
        }
    }

}


@Composable
@Preview
fun preview(){
    val test = PlayerNoteResponse("1234" ,
        listOf(
            PlayerNoteResponse.PlayerNote("1234" , "Trainignsanzug" , "Größe 124"),
            PlayerNoteResponse.PlayerNote("1234" , "Trainignsanzug" , "Größe 124"),
            PlayerNoteResponse.PlayerNote("1234" , "Trainignsanzug" , "Größe 124 sdlfsdlkj kdsjflkd jlk djskl fjsdlksdjlk sdj fklsdj dslk jdsklj flsdkj sdlkjf sdlkjdslkjsdlk jkdl jk ljskl jsd klj kj lksjflksdjfldskfjdsl jl j dlskfj sdlkfj sdkldj ljsdlkjfdsklfjdslkfjdsklfjdskl jdkl jdlksj slk jfdslfjlk")
        )
    )
    PlayerNotesPage("1234" , DefaultClickModel()).layout(view = PlayerNoteViewModel(), value = test)
}

