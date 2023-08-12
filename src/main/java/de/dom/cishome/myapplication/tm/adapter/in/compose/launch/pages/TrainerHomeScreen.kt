package de.dom.cishome.myapplication.tm.adapter.`in`.compose.launch.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.CommonComponents
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm

class TrainerHomeScreen {

    @Composable
    fun Screen(clicks: Clicks) {
        var list = listOf(
            CommonComponents.CardMenuItem("Mein Team", "myteam", R.drawable.member),
            CommonComponents.CardMenuItem("Platz", "platz", R.drawable.platz),
            CommonComponents.CardMenuItem("Tuniere", "team", R.drawable.tuniert),
        )
        this.content(list = list, clicks = clicks)
    }

    @Composable
    private fun content(list: List<CommonComponents.CardMenuItem>, clicks: TrainerHomeScreen.Clicks){
        Scaffold(
            topBar = { topBar() },
            bottomBar = { bottomBar(clicks) },
            content = { Body(it, clicks, list) }
        )
    }

    @Composable
    private fun Body(
        paddingValues: PaddingValues,
        clicks: Clicks,
        list: List<CommonComponents.CardMenuItem>
    ) {
        Box(modifier = Modifier.padding(paddingValues)) {
            Box() {
                Box(modifier = Modifier.align(Alignment.TopCenter)) {
                    Column() {
                        Row() {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(15.dp),
                                verticalArrangement = Arrangement.spacedBy(25.dp),
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                items(list.size, key = { it }) {
                                    var item = list[it];
                                    Tm.components()
                                        .MenuCard(i = item, navigate = { clicks.navTo(item.dest) })
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    @Composable
    private fun topBar(){
        Tm.components().TopBar();
    }

    @Composable
    private fun bottomBar(clicks: TrainerHomeScreen.Clicks ){
        Tm.components().TmBottomBar( color= Color.White,  actions = listOf(
            CommonComponents.NavigationBarItemData(
                "HOME",
                click = { clicks.back },
                icon = Icons.Filled.Home)
        ))
    }

    data class Clicks( val back: ()->Unit , val navTo: ( route: String )->Unit ) {

    }

}

@Composable
@Preview
fun TrainerHomeScreenPreview(){

    TrainerHomeScreen().Screen(clicks = TrainerHomeScreen.Clicks({},{}))
}