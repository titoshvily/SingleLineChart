package com.hfad.singlelinechart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.hfad.singlelinechart.ui.theme.SingleLineChartTheme
import kotlin.random.Random

const val steps = 10

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SingleLineChartTheme {
                val pointsData = getPointList()


                val minY = pointsData.minOf { it.y }
                val maxY = pointsData.maxOf { it.y }
                val yRange = maxY - minY
                val yStepSize = yRange / steps

                val xAxisData = AxisData.Builder()
                    .axisStepSize(30.dp)
                    .backgroundColor(Color.Transparent)
                    .steps(pointsData.size - 1)
                    .labelData { i ->
                        if (i < pointsData.size) {
                            pointsData[i.toInt()].x.toInt().toString()
                        } else {
                            ""
                        }
                    }
                    .build()

                val yAxisData = AxisData.Builder()
                    .steps(steps)
                    .backgroundColor(Color.Transparent)
                    .labelAndAxisLinePadding(10.dp)
                    .labelData { i ->

                        val value = minY + (i * yStepSize)
                        "%.1f".format(value)
                    }.build()

                val lineChartData = LineChartData(
                    linePlotData = LinePlotData(
                        lines = listOf(
                            Line(
                                dataPoints = pointsData,
                                lineStyle = LineStyle(
                                    color = Color.Blue,

                                ),
                                intersectionPoint = IntersectionPoint(
                                    color = Color.Red,

                                ),
                                selectionHighlightPoint = SelectionHighlightPoint(
                                    color = Color.Green
                                ),
                                shadowUnderLine = ShadowUnderLine(
                                    color = Color.Blue,
                                    alpha = 0.2f
                                ),
                                selectionHighlightPopUp = SelectionHighlightPopUp()
                            )
                        ),
                    ),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    gridLines = GridLines(
                        color = Color.LightGray.copy(alpha = 0.3f)
                    ),
                    backgroundColor = Color.White
                )

                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    lineChartData = lineChartData
                )
            }
        }
    }

    private fun getPointList(): List<co.yml.charts.common.model.Point> {
        val list = mutableListOf<co.yml.charts.common.model.Point>()
        for (i in 1..30) {
            list.add(
                co.yml.charts.common.model.Point(
                    x = i.toFloat(),
                    y = Random.nextInt(50, 110).toFloat()
                )
            )
        }
        return list
    }
}