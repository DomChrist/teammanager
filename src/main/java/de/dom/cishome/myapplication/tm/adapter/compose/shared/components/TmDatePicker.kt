package de.dom.cishome.myapplication.tm.adapter.compose.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import java.time.LocalDate


@Composable
fun TmDatePicker( page1: MutableState<Int>,
                  onDateSelected: ( l: LocalDate ) -> Unit,
                  content: @Composable ( result: TmDatePickerResult ) -> Unit,
)
{
    val date = remember{mutableStateOf(LocalDate.now())};
    //val page: MutableState<Int> = remember{ mutableStateOf(0) }

    TmDatePickerView( page1 , date , onDateSelected , content )

}

@Composable
fun TmDatePickerView(
    page: MutableState<Int>,
    date: MutableState<LocalDate>,
    onDateSelected: (l: LocalDate) -> Unit,
    content: @Composable ( result: TmDatePickerResult ) -> Unit
) {
    if( page.value == 0 ){
        chooseYear(onSelect = {
            date.value = date.value.withYear(it);
            page.value = 1
        })
    } else if( page.value == 1 ){
        chooseMonth( onSelect = { date.value = date.value.withMonth(it); page.value = 2 })
    } else if( page.value == 2 ){
        chooseDay( preDate=date.value, onSelect = {
            date.value = date.value.withDayOfMonth(it);
            page.value = 3;
            onDateSelected(date.value)
        })
    } else {
        content( TmDatePickerResult( date.value , onReset =  {page.value = 0} ) )
    }
}

data class TmDatePickerResult( val date: LocalDate , val onReset: () -> Unit )

@Composable
fun chooseDay(onSelect: (month: Int) -> Unit, preDate: LocalDate?){

    var list = mutableListOf<Int>()
    val max = preDate?.month?.maxLength() ?: 31;
    for( i in 1..max){
        list.add(i)
    }


    LazyVerticalGrid(columns = GridCells.Fixed(7),
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp) ){
        items( list.size , key = {it}){
            numberView(i = list[it] , choose = onSelect)
        }
    }

}

@Composable
fun chooseMonth( onSelect: (month: Int) -> Unit ){
    var list = mutableListOf<Int>()
    for( i in 1..12 ){
        list.add(i);
    }
    LazyVerticalGrid(columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp) ){
        items( list.size , key = {it}){
            numberView(i = list[it] , choose = onSelect)
        }
    }
}

@Composable
fun chooseYear( min: Int = LocalDate.now().year - 40,
                max: Int = LocalDate.now().year - 3,
                onSelect: ( year: Int ) -> Unit
){
    var list = mutableListOf<Int>()
    for( i in min..max ){
        list.add(i);
    }
    list = list.asReversed()
    LazyVerticalGrid(columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp) ){
        items( list.size , key = {it}){
            numberView(i = list[it] , choose = {
                    value -> onSelect(value)
            })
        }
    }

}

@Composable
fun numberView( i: Int , choose: ( value: Int) -> Unit = {} ){
    var m = Modifier
        .fillMaxWidth()
        .height(35.dp);

    Card( modifier = Modifier.clickable {
        choose(i)
    } ) {
        Text( modifier=m,fontSize= TextUnit(5f, TextUnitType.Em),
            textAlign = TextAlign.Center, text = "${i}" )
    }
}

@Composable
@Preview
fun preview(){
    chooseYear(onSelect = {})
}