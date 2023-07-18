package de.dom.cishome.myapplication.compose.membership.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.compose.home.header
import kotlinx.coroutines.launch

@Composable
fun MembershipWelcomePage(){

    content()

}






@Composable
fun content(){

    Scaffold(
        topBar = {header()}
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
        }
    }

    val scope = rememberCoroutineScope();
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 128.dp,
        sheetContent = {
        }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Text("Scaffold Content")
        }
    }

}


@Composable
@Preview
fun MembershipWelcomePagePreview(){

    content()


}