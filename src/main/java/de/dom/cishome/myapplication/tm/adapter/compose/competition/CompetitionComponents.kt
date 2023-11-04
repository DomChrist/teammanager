package de.dom.cishome.myapplication.tm.adapter.compose.competition

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun state(isRefreshing: MutableState<Boolean>, onLoad: ( scope: CoroutineScope )->Unit): PullRefreshState {
    val coroutineScope = rememberCoroutineScope()
    return rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true
            coroutineScope.launch {
                onLoad( coroutineScope )
                isRefreshing.value = false
            }
        }
    )
}