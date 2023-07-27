package de.dom.cishome.myapplication.compose.turnier.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.turnier.component.CompetitionTimer
import de.dom.cishome.myapplication.compose.turnier.model.CompetitionModel
import de.dom.cishome.myapplication.compose.turnier.model.CompetitionRepository

@Composable
fun CompetitionDetailPage( nav: NavController ) {

    var id = nav.currentBackStackEntry?.arguments?.getString("id");

    var repo = CompetitionRepository()

    var model = remember { mutableStateOf( repo.loadById( id!! ) ) }

    layout( nav , model )
}

@Composable
private fun layout(nav: NavController, model: MutableState<CompetitionModel>){
    var tm = TmComponents();
    var c = TmColors.Competition;
    var box1Height = 200.dp;

    Scaffold(
        topBar = {tm.stage1HeaderCustomize(title = "Turnierdetail", nav = nav , c)},
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
                                text= "${model.value.date}")
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
                                textAlign = TextAlign.Center , text = "${model.value.club}")
                            Row(){
                                Column( modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth() ) {
                                    Text( modifier=Modifier.fillMaxWidth(), color = Color(R.color.competition_font_primary),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = TextUnit(4.2f , TextUnitType.Em),
                                        text = "${model.value.date}")
                                }
                                Column( modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth() ) {
                                    Text( modifier = Modifier.fillMaxWidth(),
                                        color = Color(R.color.competition_font_primary),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = TextUnit(4.2f , TextUnitType.Em),
                                        text = "${model.value.location}")
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
                            Text( modifier = Modifier.padding(15.dp), text = "${model.value.location}")
                        }
                    }
                    Row( modifier = rowModifier ){
                        Card( modifier = Modifier.fillMaxWidth() ) {
                            Text( modifier = Modifier.padding(15.dp), text = "${model.value.date}")
                        }
                    }
                }
            }



        }

    }
}

@Composable
private fun body(){
    Row( modifier = Modifier.fillMaxWidth() ) {
        Column( modifier = Modifier.fillMaxWidth() ) {
            Row( modifier = Modifier.fillMaxWidth() ) {
                Column( modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth() ) {
                    Text("Kettig")
                }
                Column( modifier = Modifier
                    .weight(1f)
                    .padding(15.dp) ) {
                    Row(modifier = Modifier.fillMaxWidth()){
                        CompetitionTimer()
                    }
                }
            }
            Row() {
                Text("lkdfjlkdsjf")
            }
        }
    }


}

@Preview
@Composable
fun CompetitionDetailPagePreview() {
    var nav = rememberNavController();
    var model = remember{ mutableStateOf( CompetitionModel("#id" , "Kettig" , "Kettig - Kunstrasen" , "31.12.2023")) }
    layout(nav = nav, model = model )
}

@Composable
fun CompText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(R.color.competition_font_primary),
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
){

}

