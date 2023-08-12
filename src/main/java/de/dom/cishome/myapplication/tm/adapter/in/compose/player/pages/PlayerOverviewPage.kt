package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.pages.NavBarItem
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model.AllPlayersViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model.PlayerViewModel
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.time.LocalDate

class PlayerOverviewPage{

    private val START_PAGE = 0;
    private val ADD_PAGE = 900;

    @Composable
    fun Screen(
        filter: PlayerListFilter = PlayerListFilter.none(),
        overview: AllPlayersViewModel = viewModel( factory = AllPlayersViewModel.ViewFactory(filter) ),
        clicks: PlayerOverviewClick ){

        var players = remember{ mutableStateOf<List<Player>>(emptyList()) };
        var page = remember{ mutableStateOf(0) }

        overview.selectedTeams.observeForever { players.value = it }

        if( players.value != null  ){
            if( page.value == START_PAGE ){
                clicks.onPlayerAddClick = {page.value = ADD_PAGE}
                layout(players = players.value,clicks)
            } else if( page.value == ADD_PAGE ){
                AddPlayerScreen().Screen(onPlayerAdded = {
                    it.team.value = TextFieldValue(filter.value)
                    overview.handle(it)
                    page.value = START_PAGE
                } , {page.value = START_PAGE})
            }
        }

    }

    @Composable
    fun layout(players: List<Player>, clicks: PlayerOverviewClick) {
        Scaffold(
            topBar = {header( clicks.onBackClick )},
            bottomBar = { footer() },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = { PlayerFloatingButton(clicks.onPlayerAddClick) },
            content = {Box(modifier = Modifier.padding(it)) { content( players , clicks.onPlayerSelect )} }
        );
    }

    @Composable
    private fun header(onBackClick: () -> Unit) {
        var title = "Spieleransicht"
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
    private fun footer(){
        var ctx = LocalContext.current;

        NavBarItem("", Icon(imageVector = Icons.Filled.Menu, tint = TmColors.secondaryColor, contentDescription = "Localized description"), click = {} )

        NavigationBar( contentColor = TmColors.primaryColor , containerColor = TmColors.primaryColor ) {

            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Person, tint = TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("ABC" , color = TmColors.secondaryColor) }
            )
            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Add, tint= TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("ADD" , color= TmColors.secondaryColor) }
            )
            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.List, tint= TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("AGE" , color= TmColors.secondaryColor) }
            )

        }

    }

    @Composable
    private fun PlayerFloatingButton(onPlayerAddClick: () -> Unit) {
        FloatingActionButton(onClick = onPlayerAddClick  , containerColor = TmColors.App.primary){
            Text("+", color= TmColors.App.primaryText)
        }
    }

    @Composable
    private fun content(players: List<Player>, onPlayerSelect: (p: Player) -> Unit){
        var tabIndex: MutableState<Int> = remember { mutableStateOf(0) }
        val tabs = listOf("SPIELER", "SCHNUPPERER")

        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(selectedTabIndex = tabIndex.value) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = tabIndex.value == index,
                        onClick = { tabIndex.value = index }
                    )
                }
            }

            var playerCurrentLetter: Char = ' ';
            when (tabIndex.value) {
                0 -> {
                    LazyColumn(modifier = Modifier.padding(PaddingValues(5.dp , 25.dp))){
                        items( players.filter { !it.trial }.sortedBy { it.givenName } ){

                            if( it.familyName.uppercase().toCharArray()[0] == playerCurrentLetter ){
                                PlayerItemView( it , onPlayerSelect )
                            } else {
                                playerCurrentLetter = it.familyName.uppercase().toCharArray()[0];
                                Row(){
                                    Column(Modifier.weight(2f)){ Divider(Modifier.padding(0.dp,15.dp)) }
                                    Column(Modifier.weight(1f)){ Text(text = "${playerCurrentLetter}" , modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = TextUnit(5f,
                                        TextUnitType.Em)) }
                                    Column(Modifier.weight(8f)){ Divider(Modifier.padding(0.dp,15.dp)) }
                                }
                                PlayerItemView( it , onPlayerSelect )
                            }

                        }
                    }
                }
                1 -> {
                    LazyColumn(modifier = Modifier.padding(PaddingValues(5.dp , 25.dp))){
                        items( players.filter { it.trial } ){
                            PlayerItemView( it , onPlayerSelect )
                        }
                    }
                }
            }
        }

    }

    @Composable
    fun PlayerItemView(p: Player, onPlayerSelect: (p: Player) -> Unit){
        var c = TmColors.App;
        var m: MutableState<Player> = remember{ mutableStateOf( p ) };


        var colors = CardDefaults.cardColors(containerColor = c.primary , contentColor = c.secondary)

        Card( onClick={onPlayerSelect(p)},
            modifier = Modifier
                .padding(PaddingValues(15.dp, 5.dp, 15.dp, 5.dp))
                .fillMaxWidth() ) {
            Box(modifier = Modifier.padding(PaddingValues(15.dp))){
                Column() {
                    Icon(
                        Icons.Rounded.Person,
                        contentDescription = ""
                    )
                }
                Column(Modifier.padding(30.dp , 0.dp, 0.dp, 0.dp)) {
                    Text(  p.givenName.plus(" ").plus(p.familyName), fontWeight = FontWeight.Bold )
                }
            }
        }
    }

    data class PlayerOverviewClick(
        var onPlayerAddClick: () -> Unit,
        var onBackClick: () -> Unit,
        var onPlayerSelect: (p: Player) -> Unit,
    ) {

        companion object Factory{

            fun clicks( nav: NavController ): PlayerOverviewClick{
                return PlayerOverviewClick( { nav.navigate("player/add") } ,
                    {nav.navigateUp()} ,
                    { nav.navigate("player/detail/${it.id}") });
            }
        }

    }

}


data class PlayerListFilter( var key: String, var value: String , val filter: (p:Player)->Boolean ){
    fun isTeam(): Boolean = key.equals("TEAM" , true);
    fun isPlayer(): Boolean = key.equals("PLAYER" , true);
    fun isDefault(): Boolean = key.equals("ALL" , true);

    companion object Factory{

        fun byPlayer( id: String): PlayerListFilter {
            return PlayerListFilter("PLAYER" , id) { it.id.equals(id, true) };
        }

        fun byTeam( id: String ) = PlayerListFilter("TEAM" , id ){ it.team.equals(id,true) };

        fun none() = PlayerListFilter("ALL" , "") { true }

    }

}

@Composable
@Preview
fun PlayerOverviewPagePreview(){
    var list = listOf<Player>(
        Player("1234","Dominik" , "Christ" , LocalDate.of(1988,1,10) , "bambini"),
        Player("1234","Arnold" , "Affe" , LocalDate.of(1988,1,10) , "bambini"),
        Player("1234","Lennart" , "Franz" , LocalDate.of(1988,1,10) , "bambini"),
    )
    PlayerOverviewPage().layout(players = list, clicks = PlayerOverviewPage.PlayerOverviewClick({},{},{}))
}



