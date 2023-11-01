package de.dom.cishome.myapplication.tm.adapter.compose.player.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dom.cishome.myapplication.compose.shared.MyColorTheme
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.contactperson.components.ContactModalSheet
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.dialogs.ContactDataDialog
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.DefaultClickModel
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail

class PlayerContactDetailsPage(
    private var defaultClickModel: DefaultClickModel,
    private var onContactAddClick: ()->Unit = { defaultClickModel.navTo("") }
    ) {

    private val progressState: MutableState<ProgressState>? = null;

    @Composable
    fun Screen( playerId: String , model: PlayerContactViewModel = viewModel()) {
        val state = remember { mutableStateOf(listOf<PlayerContactDetail>()) }
        val detailDialogVisible = remember { mutableStateOf(false) }
        val progressState = remember { mutableStateOf(ProgressState.NOT_STARTED) }

        onContactAddClick = { detailDialogVisible.value = !detailDialogVisible.value }

        model.data.observeForever { state.value = it; progressState.value = ProgressState.FINISHED }


        LaunchedEffect(key1 = Unit) {
            progressState.value = ProgressState.IN_PROGRESS
            model.load(playerId)
        }

        if( detailDialogVisible.value ){
            Dialog( onDismissRequest = { detailDialogVisible.value = false }) {
                Box(modifier = Modifier.background(Color.LightGray).padding(5.dp).border(1.dp, color = Color.Transparent , shape = RoundedCornerShape(15.dp))){
                    AddContactDataComponent(
                        onDismissRequest = { detailDialogVisible.value = false },
                        { model.addDetail(playerId, it); detailDialogVisible.value = false }
                    )
                }
            }
        }
        layout(list = state.value  )
    }

    @Composable
    fun layout( list: List<PlayerContactDetail> ){
        Scaffold(topBar = { header() },
            floatingActionButton = { PlayerFloatingButton(onContactAddClick) }) {
            Box(Modifier.padding(it)) {

                LazyColumn {
                    items(list.size, key = { k -> k }) { detail ->
                        MessageRow(list[detail])
                    }
                }

            }
        }
    }

    @Composable
    private fun MessageRow(detail: PlayerContactDetail) {
        var color: MyColorTheme = TmColors.App;

            var showBottomSheet = remember { mutableStateOf(false) }
        ContactModalSheet(model = detail, state = showBottomSheet)

        Box(Modifier.padding(8.dp)) {
            Row(modifier = Modifier
                .padding(2.dp, 2.dp)
                .clickable { showBottomSheet.value = true }) {
                Column(Modifier.weight(2f)) {
                    Icon(
                        Icons.Filled.Person, contentDescription = "",
                        tint = color.secondary,
                        modifier = Modifier
                            .size(45.dp)
                            .background(color = color.primary, shape = RoundedCornerShape(75.dp))
                    )
                }
                Column(
                    Modifier
                        .weight(6f)
                        .padding(5.dp, 0.dp)
                ) {
                    Row() {
                        Text(fontWeight = FontWeight.Bold, text = "${detail.description}")
                    }
                    Row() {
                        Text("${detail.value}")
                    }
                }
                Column(Modifier.weight(1f)) {
                    Icon(Icons.Filled.Menu, contentDescription = "")
                }

            }

        }
    }

    @Composable
    private fun PlayerFloatingButton(onPlayerAddClick: () -> Unit) {
        FloatingActionButton(onClick = onPlayerAddClick, containerColor = TmColors.App.primary) {
            Text("+", color = TmColors.App.primaryText)
        }
    }

    @Composable
    fun header(){
        var title = "Spieler Contact Details"
        var color = TmColors.App;
        var defaults = TopAppBarDefaults.topAppBarColors(containerColor = color.primary)
        TopAppBar(
            title = { Text(title, color = color.primaryText) },
            navigationIcon = {
                IconButton(onClick = defaultClickModel.navBack) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        tint = color.primaryText,
                        contentDescription = null
                    )
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = defaultClickModel.navBack) {
                    Icon(
                        Icons.Filled.Menu,
                        tint = color.primaryText,
                        contentDescription = "Localized description"
                    )
                }
            },
            colors = defaults
        )
        if( this.progressState != null && this.progressState.value == ProgressState.IN_PROGRESS){
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 66.dp, 0.dp, 0.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                trackColor = MaterialTheme.colorScheme.secondary,
            )
        }

    }

}

private enum class ProgressState{
    NOT_STARTED,
    IN_PROGRESS,
    FINISHED,
    DEACTIVATED
}