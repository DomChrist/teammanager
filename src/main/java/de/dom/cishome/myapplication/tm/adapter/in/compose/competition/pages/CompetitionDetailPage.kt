package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.compose.turnier.component.CompetitionTimer
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.CompetitionDetailViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.CompetitionDetailViewModelFactory
import de.dom.cishome.myapplication.tm.application.domain.competition.Club
import de.dom.cishome.myapplication.tm.application.domain.competition.CompetitionDate
import de.dom.cishome.myapplication.tm.application.domain.competition.TeamCompetitionModel
import de.dom.cishome.myapplication.tm.application.domain.shared.TeamReference


class CompetitionDetailPage {


    @Composable fun Screen(
        detailId: String,
        detail: CompetitionDetailViewModel = viewModel( factory = CompetitionDetailViewModelFactory(detailId)),
    ){
        var model: MutableState<TeamCompetitionModel?> = remember{ mutableStateOf(null) }
        detail.data.observeForever {
            model.value = it;
        }
        if( model.value != null ){
            Layout(model = model.value!!)
        } else {
            Text("...Loading...")
        }
    }

    @Composable
    fun Layout( model: TeamCompetitionModel ){
        var tm = TmComponents();
        var c = TmColors.Competition;
        var box1Height = 200.dp;

        Scaffold(
            topBar = { topBar() },
        ){
            var maxWidthModifier = Modifier.fillMaxWidth();
            Box( modifier = Modifier
                .padding(it)
                .fillMaxSize()){
                Row( maxWidthModifier ){
                    Column(maxWidthModifier) {
                        Row( modifier= Modifier
                            .background(c.primary)
                            .height(box1Height) ) {
                            Column( Modifier.weight(1f) ) {
                                Text( modifier= Modifier
                                    .fillMaxWidth()
                                    .padding(21.dp)
                                    .align(Alignment.CenterHorizontally),
                                    fontSize = TextUnit(6f , TextUnitType.Em),
                                    color = Color.White,
                                    text= "${model.date.format()}")
                            }
                            Column( Modifier.weight(1f) ) {
                                Box( Modifier.padding(15.dp)) {
                                    CompetitionTimer()
                                }
                            }
                        }
                    }
                }
            }

            Box(modifier = Modifier.padding(15.dp , 155.dp)){
                var rowModifier = Modifier.padding(PaddingValues(0.dp , 10.dp , 0.dp , 5.dp))
                Row(){
                    Column() {
                        Row( modifier = maxWidthModifier
                            .height(130.dp)){
                            Card(maxWidthModifier.fillMaxSize()) {
                                Text( modifier = maxWidthModifier,
                                    color = c.primary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = TextUnit(7f, TextUnitType.Em),
                                    textAlign = TextAlign.Center , text = "${model.club.clubName}")
                                Row(){
                                    Column( modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth() ) {
                                        Text( modifier= Modifier.fillMaxWidth(), color = Color(R.color.competition_font_primary),
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = TextUnit(4.2f , TextUnitType.Em),
                                            text = "${model.date.format()}")
                                    }
                                    Column( modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth() ) {
                                        Text( modifier = Modifier.fillMaxWidth(),
                                            color = Color(R.color.competition_font_primary),
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = TextUnit(4.2f , TextUnitType.Em),
                                            text = "${model.club.location}")
                                    }
                                }
                            }
                        }

                        Row( rowModifier ){
                            Column(Modifier.weight(1f)) {
                                Card( Modifier.fillMaxWidth()) {
                                    Text( modifier = Modifier.fillMaxWidth(),
                                        color = Color(R.color.competition_font_primary).copy(1f),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = TextUnit(4.2f , TextUnitType.Em),
                                        text = " 8 Minuten ")
                                }
                            }
                            Column(Modifier.weight(1f)) {
                                Card( Modifier.fillMaxWidth()) {
                                    Text( modifier = Modifier.fillMaxWidth(),
                                        color = Color(R.color.competition_font_primary),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = TextUnit(4.2f , TextUnitType.Em),
                                        text = " 11 Teams ")
                                }
                            }
                        }

                        Row( rowModifier ){
                            Card( modifier = Modifier.fillMaxWidth() ) {
                                Text( modifier = Modifier.padding(15.dp), text = "${model.club.location}")
                            }
                        }
                        Row( modifier = rowModifier ){
                            Card( modifier = Modifier.fillMaxWidth() ) {
                                Text( modifier = Modifier.padding(15.dp), text = "${model.date}")
                            }
                        }
                    }
                }



            }

        }
    }

    @Composable private fun topBar() {
        var color = TmColors.Competition
        var title = "Turnier";
        var defaults = TopAppBarDefaults.topAppBarColors( containerColor = color.primary )
        TopAppBar(
            title = { Text(title , color=Color.White) },
            navigationIcon = {
                IconButton(onClick = {} ) {
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
}

@Composable
@Preview
private fun CompetitionDetailPagePreview(){
    CompetitionDetailPage().Layout( TeamCompetitionModel("1234" , Club("Kettig","Kunstrasen") , CompetitionDate("26.04.2023" , ""), TeamReference("","")) )
}