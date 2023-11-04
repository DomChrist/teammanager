package de.dom.cishome.myapplication.tm.adapter.compose.competition

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class CompetitionOverviewPage {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Structure() = apply{
        var isRefreshing = remember { mutableStateOf(false) }
        var pullRefreshState = state( isRefreshing ){  }

        Scaffold {
            paddingValues ->
            Box(
                Modifier.padding(paddingValues).pullRefresh(pullRefreshState)
            ){
                PullRefreshIndicator(
                    refreshing = isRefreshing.value,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }

        }
    }



}