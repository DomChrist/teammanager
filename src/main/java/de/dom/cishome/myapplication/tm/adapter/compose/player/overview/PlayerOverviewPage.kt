package de.dom.cishome.myapplication.tm.adapter.compose.player.overview

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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerCommand
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.pages.NavBarItem
import de.dom.cishome.myapplication.tm.adapter.compose.player.shared.PlayerListFilter
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.AddPlayerScreen
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.CommonComponents
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.DefaultClickModel
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayersTeamModel
import java.time.LocalDate

class PlayerOverviewPage(private val criteria: PlayerListFilter, private val defaultClickModel: DefaultClickModel ) {


    @Composable
    fun Screen( view: PlayerOverviewViewModel = viewModel() ){

        val workingModel = remember{ mutableStateOf<PlayersTeamModel>( view.model.value ?: PlayersTeamModel(0 , null , listOf()) ) }
        val showAddDialog = remember { mutableStateOf(false) }
        view.model.observeForever { workingModel.value = it }

        LaunchedEffect(key1 = Unit ){
            view.load( criteria )
        }

        if( workingModel.value != null && workingModel.value.ready() ){
            if( showAddDialog.value ){
                AddPlayerModal(
                    team = workingModel.value.team,
                    onPlayerAdded = {view.addPlayer(it); showAddDialog.value = false},
                    onDismissRequest = {showAddDialog.value = false} ,  );
            }

            layout(playersTeamModel = workingModel.value, clicks = PlayerOverviewClicks({ showAddDialog.value = true },{ defaultClickModel.navBack() },{
                defaultClickModel.navTo("player/detail/${it.id}")
            }) )
        } else if( workingModel.value != null && workingModel.value.onFailure() ){
            defaultClickModel.navBack()
        } else if( workingModel.value == null || workingModel.value.onLoad() ){
            CommonComponents().Loading();
        }
    }

    @Composable
    fun layout( playersTeamModel: PlayersTeamModel, clicks: PlayerOverviewClicks) {
        Scaffold(
            topBar = {header( clicks.onBackClick )},
            bottomBar = { footer( { clicks.onPlayerAddClick() } ) },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = { PlayerFloatingButton( playersTeamModel.hasTeam() , clicks.onPlayerAddClick) },
            content = { Box(modifier = Modifier.padding(it)) { content( playersTeamModel.players , clicks.onPlayerSelect )} }
        );
    }

    @Composable
    private fun content(players: List<Player>, onPlayerSelect: (p: Player) -> Unit){
        var tabIndex: MutableState<Int> = remember { mutableStateOf(0) }
        var trialPlayers = players.filter { it.isTrial() }
        var activePlayers = players.filter { !it.isTrial() }
        val tabs = listOf("SPIELER (${activePlayers.size})", "SCHNUPPERER (${trialPlayers.size})")

        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(selectedTabIndex = tabIndex.value) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = tabIndex.value == index,
                        onClick = { tabIndex.value = index }
                    )
                }
            }

            when (tabIndex.value) {
                0 -> {
                    PlayerLazyColumn(activePlayers , onPlayerSelect)
                }
                1 -> {
                    PlayerLazyColumn(activePlayers = trialPlayers, onPlayerSelect = onPlayerSelect)
                }
            }
        }

    }

    @Composable
    private fun PlayerLazyColumn(activePlayers: List<Player>, onPlayerSelect: (p: Player) -> Unit){
        var playerCurrentLetter: Char = ' ';
        activePlayers.withIndex().mapIndexed { index, indexedValue ->  }
        val map = activePlayers.groupBy { p -> p.familyName.uppercase().get(0) }.toSortedMap();
        val l = 'A' .. 'Z';
            LazyColumn(modifier = Modifier.padding(PaddingValues(5.dp , 25.dp))){
                l.toList().forEach {

                    items( map.get(it)?.toList() ?: listOf() ){
                        if( it.familyName.uppercase().toCharArray()[0] == playerCurrentLetter ){
                            PlayerItemView( it , onPlayerSelect )
                        } else {
                            playerCurrentLetter = it.familyName.uppercase().toCharArray()[0];
                            Row(){
                                Column(Modifier.weight(2f)){ Divider(Modifier.padding(0.dp,15.dp)) }
                                Column(Modifier.weight(1f)){ Text(text = "${playerCurrentLetter}" , modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = TextUnit(5f,
                                    TextUnitType.Em)
                                ) }
                                Column(Modifier.weight(8f)){ Divider(Modifier.padding(0.dp,15.dp)) }
                            }
                            PlayerItemView( it , onPlayerSelect )
                        }
                    }
                }

            }

    }


    @Composable
    private fun AddPlayerModal( team: PlayersTeamModel.Team? = null,
                                onPlayerAdded: ( cmd:NewPlayerCommand)->Unit = {},
                                onDismissRequest: () -> Unit = {},
                                ){
        ModalBottomSheet(onDismissRequest = { onDismissRequest() } , ) {
            Box(modifier=Modifier.padding(0.dp,5.dp,5.dp,75.dp)){
                AddPlayerScreen( team ).Screen(
                    onPlayerAdded = { onPlayerAdded(it) },
                    onBackClick = { onDismissRequest() })
            }
        }
    }

    @Composable
    fun PlayerItemView(p: Player, onPlayerSelect: (p: Player) -> Unit){
        var c = TmColors.App;
        var m: MutableState<Player> = remember{ mutableStateOf( p ) };
        val onClick = {
            /*
            playerService.select( p );
            nc.saveState()!!.putString("playerId" , p.id)
            nc.currentBackStackEntry
                ?.savedStateHandle
                ?.set("player" , m);
            nc.navigate("player/detail/" + p.id )
             */
            onPlayerSelect( p );
        }

        var colors = CardDefaults.cardColors(containerColor = c.primary , contentColor = c.secondary)

        Card( onClick=onClick,
            modifier = Modifier
                .padding(PaddingValues(15.dp, 5.dp, 15.dp, 5.dp))
                .fillMaxWidth() ) {

            PlayerOverviewComponents.PlayerShortBox(p = p , true)

        }
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
    private fun footer( onPlayerAddClick: () -> Unit ){
        var ctx = LocalContext.current;

        NavBarItem("", Icon(imageVector = Icons.Filled.Menu, tint = TmColors.secondaryColor, contentDescription = "Localized description"), click = {} )

        NavigationBar( contentColor = TmColors.primaryColor , containerColor = TmColors.primaryColor ) {
            /*
            NavigationBarItem(selected = false,
                onClick = { onPlayerAddClick() },
                icon = { Icon(Icons.Filled.Person, tint = TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("ABC" , color = TmColors.secondaryColor) }
            )
             */
            NavigationBarItem(selected = false,
                onClick = { onPlayerAddClick() },
                icon = { Icon(Icons.Filled.Add, tint= TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("ADD" , color= TmColors.secondaryColor) }
            )
            /*
            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.List, tint= TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("AGE" , color= TmColors.secondaryColor) }
            )
             */
        }
    }

    @Composable
    private fun PlayerFloatingButton( hasTeam: Boolean, onPlayerAddClick: () -> Unit) {
        if( true ){
            FloatingActionButton(onClick = onPlayerAddClick  , containerColor = TmColors.App.primary){
                Text("+", color= TmColors.App.primaryText)
            }
        }
    }

}


@Preview
@Composable
fun preview(){

    val players = listOf<Player>(
        Player("1234" , "Max" , "Mustermann" , LocalDate.of(2018,1,1) , "Bambini" , state = Player.MemberState(null,
            Player.MemberState.Active(true)) ),
        Player("1234" , "Johannes" , "Christ" , LocalDate.of(2018,1,1) , "Bambini" , state = Player.MemberState(null,
            Player.MemberState.Active(true)) ),
        Player("1234" , "Max" , "Mustermann" , LocalDate.of(2018,1,1) , "Bambini" , state = Player.MemberState(null,
            Player.MemberState.Active(true)) ),
        Player("1234" , "Dominik" , "Christ" , LocalDate.of(2018,1,1) , "Bambini" , state = Player.MemberState(null,
            Player.MemberState.Active(true)) ),
        Player("1234" , "Dominik" , "Dominik" , LocalDate.of(2018,1,1) , "Bambini" , state = Player.MemberState(null,
            Player.MemberState.Active(true)) )

    )

    PlayerOverviewPage( PlayerListFilter.byTeam("1234") , DefaultClickModel())
        .layout(playersTeamModel = PlayersTeamModel(200,null, players), clicks = PlayerOverviewClicks({},{},{}) )
}