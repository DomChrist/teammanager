package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.MyColorTheme
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.components.CompetitionDialogs
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.CompetitionViewClick
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.CompetitionsViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.preview.PreviewCompetitionRepository
import de.dom.cishome.myapplication.tm.application.services.CompetitionApplicationService
import de.dom.cishome.myapplication.tm.application.domain.competition.TeamCompetitionModel
import de.dom.cishome.myapplication.tm.application.domain.shared.TeamReference

class CompetitionPage( val team: String, val view: CompetitionsViewModel , val clicks: CompetitionViewClick ) {

    private var competitions: MutableState<List<TeamCompetitionModel>> = mutableStateOf(emptyList());

    @Composable fun Screen(){
        this.competitions = remember { mutableStateOf<List<TeamCompetitionModel>>( competitions.value ); }
        var dialogState = remember{ mutableStateOf(false) }

        if( dialogState.value ) {
            CompetitionDialogs.CreateDialog(
                onDismissRequest = { dialogState.value = false },
                onCreate = {
                    it.team = TeamReference(team,team);
                    val model = this.view.app.handle(it)
                    this.competitions.value.plus( model );
                    dialogState.value = false;
                }
            )
        }

        this.init()

        Scaffold(
            topBar = { this.topBar() },
            floatingActionButton = {this.CreateButton(dialogState = dialogState)},
            content = { this.content(it , competitions.value) }
        )
    }

    @Composable private fun init() {
        this.view.repo().readAll(team,competitions)
    }

    @Composable private fun CreateButton(dialogState: MutableState<Boolean>) {
        var c: MyColorTheme = TmColors.Competition;
        FloatingActionButton( shape = RoundedCornerShape(100.dp), containerColor = c.primary, onClick = { dialogState.value = true }){
            Text("+" , color = Color.White, fontSize = TextUnit(6.2f,TextUnitType.Em))
        }
    }

    @Composable private fun topBar() {
        var color = TmColors.Competition
        var title = "Turnier";
        var defaults = TopAppBarDefaults.topAppBarColors( containerColor = color.primary )
        TopAppBar(
            title = { Text(title , color=Color.White) },
            navigationIcon = {
                IconButton(onClick = clicks.back ) {
                    Icon(Icons.Filled.ArrowBack, tint=Color.White, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Menu, tint=Color.White, contentDescription = "Localized description")
                }
            },
            colors = defaults
        )
    }

    @Composable private fun content(paddingValues: PaddingValues , competitions: List<TeamCompetitionModel>) {
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {

            Box(modifier = Modifier.fillMaxSize() ){
                Image( contentScale= ContentScale.FillBounds, modifier= Modifier
                    .fillMaxWidth()
                    .height(200.dp), painter = painterResource(id = R.drawable.tuniert), contentDescription = "9999" )
            }


            Box(modifier = Modifier
                .padding(25.dp, 50.dp, 25.dp, 80.dp)
                .fillMaxSize()
                .background(Color(R.color.competition_primary).copy(0.01f))) {

                if( !competitions.isEmpty() ){
                    LazyColumn{
                        items( competitions.size, key = {it} ){
                            val model = competitions[it]
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = {
                                    }) ) {
                                CompetitionView(c = model )
                            }
                        }
                    }
                }

            }

        }

    }

    @Composable
    private fun CompetitionView(c: TeamCompetitionModel){
        var colors = CardDefaults.cardColors(containerColor = TmColors.Competition.primary, contentColor = Color.White)
        val paddingModifier = Modifier.padding(10.dp)
        val clickModifier = Modifier
            .clickable { clicks.navTo("competition/detail/${c.id}") }
            .fillMaxWidth()
            .padding(15.dp)
        Card(
            colors=colors,
            modifier = clickModifier ) {
            Column( modifier = paddingModifier) {
                Text( fontWeight = FontWeight.ExtraBold, fontSize = TextUnit(5f, TextUnitType.Em) , text = "${c.club.clubName}")
                Text(fontWeight = FontWeight.Light , fontSize = TextUnit(4f, TextUnitType.Em) , text = "${c.dateFormated()}")
            }
        }
    }

}

@Composable
@Preview fun CompetitionPagePreview(){
    var repo = PreviewCompetitionRepository();
    var model = CompetitionsViewModel(
        null,
        CompetitionApplicationService( repo, repo )
    )
    CompetitionPage( "F", model , model.clickModel(rememberNavController()) ).Screen();
}