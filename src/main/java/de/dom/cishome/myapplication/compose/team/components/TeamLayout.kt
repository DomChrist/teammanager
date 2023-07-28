package de.dom.cishome.myapplication.compose.team.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.team.shared.TeamTheme

@Composable
fun TeamScaffold(nav: NavController, content: @Composable (t: TeamTheme) -> Unit){
    var theme = TeamTheme();
    var tm = TmComponents();

    Scaffold( topBar = { tm.stage1HeaderCustomize(title = "Team", nav = nav, color = theme.color) } ) {
        Box( modifier = Modifier.padding(it) ){
            content( theme )
        }
    }

}