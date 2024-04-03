package com.example.android_widget_calendar

import HomeWidgetGlanceState
import HomeWidgetGlanceStateDefinition
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext

import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import es.antonborri.home_widget.actionStartActivity
import java.lang.reflect.Modifier
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters


class CalendarGlanceWidget : GlanceAppWidget() {

    override val stateDefinition = HomeWidgetGlanceStateDefinition()


    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            CalendarView()
        }
    }
    @Composable
    private fun CalendarView() {


        val today = LocalDate.now()
        val startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth())
        val firstDayOfWeek = startOfMonth.dayOfWeek.value
        val previousMonthEnd = startOfMonth.minusDays(firstDayOfWeek.toLong())
        var currentDays = previousMonthEnd
        Box(modifier = GlanceModifier.fillMaxSize()) {
            Column(modifier = GlanceModifier.padding(horizontal = 8.dp).background(Color.White)) {
                CalendarHeaderYearMonth(today)
                WeekOfDays(today)

                for (week in 0 until 6) {
                    Row(modifier = GlanceModifier.fillMaxWidth().height(56.dp)) {
                        for (day in 0 until 7) {
                            val textColor = if (currentDays.dayOfWeek == DayOfWeek.SUNDAY) Color.Red else Color.Black
                            Box(modifier = GlanceModifier.defaultWeight().padding(2.dp), contentAlignment = Alignment.Center) {
                                Text(
                                    text = currentDays.dayOfMonth.toString().padStart(2,'0'),
                                    style = TextStyle(color = ColorProvider(textColor), textAlign = TextAlign.Center, fontSize = 16.sp),

                                    )
                            }
                            currentDays = currentDays.plusDays(1)
                        }
                    }
                }
            }
        }
//        Box(modifier = GlanceModifier.fillMaxSize()) {
//            Column(modifier = GlanceModifier.padding(horizontal = 8.dp).background(Color.White)) {
//                CalendarHeaderYearMonth(today)
//                WeekOfDays(today)
//
//                for (week in 0 until 6) {
//                    Row(modifier = GlanceModifier.fillMaxWidth().height(56.dp)) {
//                        for (day in 0 until 7) {
//                            val textColor = if (currentDays.dayOfWeek == DayOfWeek.SUNDAY) Color.Red else Color.Black
//                            Box(modifier = GlanceModifier.defaultWeight().background(Color.Green).padding(2.dp), contentAlignment = Alignment.Center) {
//                                Text(
//                                    text = currentDays.dayOfMonth.toString().padStart(2,'0'),
//                                    style = TextStyle(color = ColorProvider(textColor), textAlign = TextAlign.Center, fontSize = 16.sp),
//
//                                    )
//                            }
//                            currentDays = currentDays.plusDays(1)
//                        }
//                    }
//                }
//            }
//        }
    }

}

@Composable
private fun CalendarHeaderYearMonth(today : LocalDate) {
    ///
    /// @ 현재 년월 과 플러스 버튼
    ///
    Row(modifier = GlanceModifier.padding(vertical = 12.dp, horizontal = 8.dp)) {
        Text(
            text = "${today.year}.${today.monthValue.toString().padStart(2,'0')}",
            style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold)
        )
        Text(
            modifier = GlanceModifier.fillMaxWidth(),
            text = "버튼 들어갈 자리", style = TextStyle(textAlign = TextAlign.End))
    }
}


@Composable
private fun WeekOfDays(today : LocalDate) {
    ///
    /// @ 요일
    ///
    val weekDays = arrayOf("일", "월", "화", "수", "목", "금", "토")

    Row (modifier = GlanceModifier.fillMaxWidth()){
        weekDays.forEach { day ->
            val isWeekDay = if (day == "일")  Color.Red else Color.Black
            Box (modifier = GlanceModifier.defaultWeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorProvider(isWeekDay),
                        textAlign = TextAlign.Center
                    )
                )
            }

        }
    }
}