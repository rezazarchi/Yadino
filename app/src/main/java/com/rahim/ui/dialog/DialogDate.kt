package com.rahim.ui.dialog

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.rahim.R
import com.rahim.data.modle.data.TimeDate
import com.rahim.ui.theme.Periwinkle
import com.rahim.ui.theme.YadinoTheme
import com.rahim.utils.base.view.DialogButtonBackground
import com.rahim.utils.base.view.gradientColors
import com.rahim.utils.enums.HalfWeekName
import com.rahim.utils.extention.calculateMonthName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogChoseDate(
    modifier: Modifier = Modifier,
    times: List<TimeDate>,
    yearNumber: Int,
    monthNumber: Int,
    dayNumber: Int,
    closeDialog: () -> Unit,
    dayCheckedNumber: (yer: Int, month: Int, day: Int) -> Unit,
    monthChange: (yer: Int, month: Int) -> Unit,
) {
    var dayClicked by rememberSaveable { mutableIntStateOf(dayNumber) }
    var currentDay by rememberSaveable { mutableIntStateOf(dayNumber) }
    var currentMonth by rememberSaveable { mutableIntStateOf(monthNumber) }
    var yearClicked by rememberSaveable { mutableIntStateOf(yearNumber) }
    var monthClicked by rememberSaveable { mutableIntStateOf(monthNumber) }
    BasicAlertDialog(properties = DialogProperties(
        usePlatformDefaultWidth = false, dismissOnClickOutside = true
    ), modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp), onDismissRequest = {}) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(percent = 6),
        ) {
            Column(modifier = Modifier.padding(bottom = 35.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(brush = Brush.verticalGradient(gradientColors))
                        .padding(vertical = 10.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        var month = monthNumber.plus(1)
                        var year = yearNumber
                        if (month > 12) {
                            month = 1
                            year = yearNumber.plus(1)
                        }
                        dayClicked = if (month == currentMonth) currentDay else 1
                        monthClicked = month
                        yearClicked = year
                        monthChange(year, month)
                    }) {
                        Icon(
                            tint = Color.White,
                            painter = painterResource(id = R.drawable.less_then),
                            contentDescription = "less then sign"
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth(0.5f),
                        text = "$yearClicked ${monthClicked.calculateMonthName()}",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = {
                        var month = monthNumber.minus(1)
                        var year = yearNumber
                        if (month < 1) {
                            month = 12
                            year = yearNumber.minus(1)
                        }
                        dayClicked = if (month == currentMonth) currentDay else 1
                        monthClicked = month
                        yearClicked = year
                        monthChange(year, month)
                    }) {
                        Icon(
                            painterResource(id = R.drawable.greater_then),
                            contentDescription = "greater then sign",
                            tint = Color.White
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val days = listOf(
                        HalfWeekName.SATURDAY.nameDay,
                        HalfWeekName.SUNDAY.nameDay,
                        HalfWeekName.MONDAY.nameDay,
                        HalfWeekName.TUESDAY.nameDay,
                        HalfWeekName.WEDNESDAY.nameDay,
                        HalfWeekName.THURSDAY.nameDay,
                        HalfWeekName.FRIDAY.nameDay
                    )
                    days.reversed().forEach {
                        when (it) {
                            days.first() -> {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                            }

                            days.last() -> {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }

                            else -> {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }

                    }
                }
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                        columns = GridCells.Fixed(7),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        userScrollEnabled = false
                    ) {
                        items(times) {
                            TimeItems(
                                it,
                                dayClicked,
                                monthClicked,
                                yearClicked,
                                dayCheckedNumber = { yer, month, day ->
                                    yearClicked = yer
                                    monthClicked = month
                                    dayClicked = day
                                })
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        DialogButtonBackground(text = stringResource(id = R.string.selection),
                            gradient = Brush.verticalGradient(gradientColors),
                            modifier = Modifier
                                .fillMaxWidth(0.25f)
                                .height(40.dp),
                            textSize = 16.sp,
                            textStyle = TextStyle(fontWeight = FontWeight.Bold),
                            onClick = {
                                dayCheckedNumber(yearClicked, monthClicked, dayClicked)
                                closeDialog()
                            })
                        Spacer(modifier = Modifier.width(10.dp))
                        TextButton(onClick = {
                            dayCheckedNumber(0, 0, 0)
                            closeDialog()
                        }) {
                            Text(
                                fontSize = 16.sp,
                                text = stringResource(id = R.string.cancel),
                                style = TextStyle(
                                    brush = Brush.verticalGradient(
                                        gradientColors
                                    ), fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimeItems(
    timeDate: TimeDate,
    dayNumberChecked: Int,
    monthNumberChecked: Int,
    yerNumberChecked: Int,
    dayCheckedNumber: (yer: Int, month: Int, day: Int) -> Unit,
) {
    if (timeDate.dayNumber <= 0 || timeDate.nameDay.isNullOrEmpty()) return
    if (timeDate.isToday && timeDate.dayNumber != dayNumberChecked) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .padding(2.dp)
                .border(
                    1.dp,
                    brush = Brush.verticalGradient(gradientColors),
                    shape = RoundedCornerShape(4.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.clickable {
                    dayCheckedNumber(
                        timeDate.yerNumber,
                        timeDate.monthNumber,
                        timeDate.dayNumber
                    )
                },
                text = timeDate.dayNumber.toString(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                style = TextStyle(
                    brush = Brush.verticalGradient(
                        gradientColors
                    )
                )
            )
        }
    } else if (timeDate.nameDay == HalfWeekName.FRIDAY.nameDay && timeDate.dayNumber != dayNumberChecked) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .padding(2.dp)
                .background(
                    color = Periwinkle,
                    shape = RoundedCornerShape(4.dp),
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.clickable {
                    dayCheckedNumber(
                        timeDate.yerNumber,
                        timeDate.monthNumber,
                        timeDate.dayNumber
                    )
                },
                text = timeDate.dayNumber.toString(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface,
            )
        }
    } else if (timeDate.dayNumber == dayNumberChecked && timeDate.yerNumber == yerNumberChecked && timeDate.monthNumber == monthNumberChecked) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .padding(2.dp)
                .background(
                    brush = Brush.verticalGradient(gradientColors),
                    shape = RoundedCornerShape(4.dp),
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = timeDate.dayNumber.toString(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(46.dp)
                .padding(2.dp)
                .background(
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    shape = RoundedCornerShape(4.dp),
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.clickable {
                    dayCheckedNumber(
                        timeDate.yerNumber,
                        timeDate.monthNumber,
                        timeDate.dayNumber
                    )
                },
                text = timeDate.dayNumber.toString(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
@Preview(device = Devices.PIXEL_4)
fun DialogChoseDateWrapperLight() {
    val times = ArrayList<TimeDate>()
    for (i in 1..31) {
        times.add(
            TimeDate(
                dayNumber = i,
                nameDay = if (i == 7 || i == 14 || i == 21 || i == 28) "ج" else "ش",
                haveTask = false,
                yerNumber = 1403,
                monthNumber = 2,
                monthName = "اردیبهشت",
                isChecked = false,
                isToday = i == 21,
            ),
        )
    }
    YadinoTheme {
        DialogChoseDate(
            times = times,
            yearNumber = 1403,
            monthNumber = 2,
            dayNumber = 21,
            closeDialog = {},
            dayCheckedNumber = { yer, month, day -> },
            monthChange = { yer, month -> }
        )
    }
}

@Composable
@Preview(device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DialogChoseDateWrapperDark() {
    val times = ArrayList<TimeDate>()
    for (i in 1..31) {
        times.add(
            TimeDate(
                dayNumber = i,
                nameDay = if (i == 7 || i == 14 || i == 21 || i == 28) "ج" else "ش",
                haveTask = false,
                yerNumber = 1403,
                monthNumber = 2,
                monthName = "اردیبهشت",
                isChecked = false,
                isToday = i == 28,
            ),
        )
    }
    YadinoTheme {
        DialogChoseDate(
            times = times,
            yearNumber = 1403,
            monthNumber = 2,
            dayNumber = 22,
            closeDialog = {},
            dayCheckedNumber = { yer, month, day -> },
            monthChange = { yer, month -> }
        )
    }
}