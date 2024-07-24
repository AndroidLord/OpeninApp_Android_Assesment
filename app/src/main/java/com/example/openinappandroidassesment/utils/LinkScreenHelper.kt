package com.example.openinappandroidassesment.utils

import android.graphics.drawable.GradientDrawable
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.example.openinappandroidassesment.R
import com.example.openinappandroidassesment.models.DashBoardModel
import com.example.openinappandroidassesment.models.Link
import com.example.openinappandroidassesment.network.NetworkResponse
import com.example.openinappandroidassesment.ui.theme.BaseColor
import com.example.openinappandroidassesment.ui.theme.Blue
import com.example.openinappandroidassesment.ui.theme.FabColor
import com.example.openinappandroidassesment.ui.theme.GradientBlue
import com.example.openinappandroidassesment.ui.theme.GridColor
import com.example.openinappandroidassesment.ui.theme.LightBlue
import com.example.openinappandroidassesment.ui.theme.LightPurple
import com.example.openinappandroidassesment.ui.theme.LightRed
import com.example.openinappandroidassesment.ui.theme.LightYellow
import com.example.openinappandroidassesment.ui.theme.Purple
import com.example.openinappandroidassesment.ui.theme.Red
import com.example.openinappandroidassesment.ui.theme.UnselectedColor
import com.example.openinappandroidassesment.ui.theme.Yellow
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.Calendar


@Composable
public fun LinkTabList(topLinkList: List<Link>, recentLinkList: List<Link>) {

    var topLink by remember { mutableStateOf(true) }
    val list = remember {
        mutableStateListOf<Link>().apply { addAll(topLinkList) }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            // First, Top Links
            Box(
                modifier = selectedTabLink(topLink).clickable {
                    if (topLink) return@clickable
                    topLink = !topLink
                    list.clear()
                    list.addAll(topLinkList)
                }
            ) {
                Text(
                    "Top Links", fontSize = 16.sp,
                    color = if (topLink) Color.White else UnselectedColor,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

            }

            // Second, Recent Links
            Box(
                modifier = selectedTabLink(!topLink)
                    .clickable {
                        if (!topLink) return@clickable
                        topLink = !topLink
                        list.clear()
                        list.addAll(recentLinkList)
                    }
            ) {
                Text(
                    "Recent Links", fontSize = 16.sp,
                    color = if (!topLink) Color.White else UnselectedColor,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

        }



        Box(
            modifier = Modifier
                .size(36.dp)
                .background(UnselectedColor.copy(alpha = 0.2f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = UnselectedColor
            )

        }


    }

    Spacer(modifier = Modifier.height(16.dp))

    Column {
        list.take(4).forEach { link ->
            LinkItem(link, rememberCustomImageLoader())
        }
    }

}


@Composable
public fun selectedTabLink(link: Boolean): Modifier {
    val modifier = if (link) {
        Modifier
            .background(FabColor, RoundedCornerShape(20.dp))
            .padding(8.dp)
    } else {
        Modifier.padding(8.dp)
    }
    return modifier
}

@Composable
fun LinkItem(link: Link, customImageLoader: ImageLoader) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .clip(SharpBottomRoundedTopShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {

            Column( // Wrap Card contents with Column for vertical alignment
                verticalArrangement = Arrangement.Center, // Center contents vertically
                modifier = Modifier.fillMaxHeight() // Ensure Column fills Card's height
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        AsyncImage(
                            model = link.original_image,
                            contentDescription = link.title,
                            contentScale = ContentScale.Crop,
                            imageLoader = customImageLoader,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp, BaseColor, RoundedCornerShape(8.dp)
                                )
                                .size(48.dp)

                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        HeaderWithSubheader(
                            headerText = link.title,
                            subheaderText = formatDate_Day_Month_Year(link.created_at),
                            limiter = true
                        )
                    }

                    HeaderWithSubheader(
                        headerText = link.total_clicks,
                        subheaderText = "clicks",
                        limiter = false
                    )

                }
            }
        }

        // Web Link Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clip(SharpTopRoundedBottomShape(cornerRadius = 8.dp))
                .background(LightBlue)
                .drawBehind {
                    drawRoundRect(
                        color = FabColor,
                        style = DOTTED_STROKE,
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )
                }
                .padding(8.dp)
        ) {
            Text(
                text = limitText(link.web_link, 30),
                color = FabColor,
                fontSize = 14.sp,
                modifier = Modifier
            )
            Icon(
                painter = painterResource(id = R.drawable.copy),
                contentDescription = null,
                tint = FabColor,
                modifier = Modifier.size(14.dp)
            )
        }
    }

}

@Composable
fun HeaderWithSubheader(headerText: String, subheaderText: String, limiter: Boolean = true) {

    Column {
        Text(
            text = limitText(headerText, 20, limiter),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
        )
        Text(
            text = subheaderText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier
        )
    }
}


@Composable
fun rememberCustomImageLoader(): ImageLoader {
    val context = LocalContext.current
    return remember {
        ImageLoader.Builder(context).apply {
            memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25) // Example: Use 25% of available memory for image caching
                    .build()
            }
            diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache")) // Custom cache directory
                    .maxSizeBytes(1024L * 1024L * 100) // Example: 100 MB max disk cache size
                    .build()
            }
        }.build()
    }
}

@Composable
fun IconWithText(
    icon: Int,
    text: String,
    tint: Color = Color.Black,
    center: Boolean = false,
    rowModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = if (center) Arrangement.Center else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = rowModifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = tint,
            modifier = iconModifier
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = textModifier
        )
    }
}

@Composable
fun GreetingMessage() {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when (hour) {
        in 0..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..23, in 0..4 -> "Good Evening"
        else -> "Hello"
    }

    Text(
        text = greeting,
        fontWeight = FontWeight.Light,
        modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        fontSize = 16.sp,
        color = Color.Gray
    )
}

@Composable
fun HorizontalAnalytics(dashBoardModel: DashBoardModel) {

    LazyRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
    ) {
        item {
            AnalyticItem(
                icon = R.drawable.clicks,
                value = dashBoardModel.total_clicks,
                text = "Total Clicks",
                tint = Purple,
                bgColor = LightPurple
            )
            AnalyticItem(
                icon = R.drawable.location1,
                value = dashBoardModel.top_location,
                text = "Total Location",
                tint = Blue,
                bgColor = LightBlue
            )
            AnalyticItem(
                icon = R.drawable.internet,
                value = dashBoardModel.top_source,
                text = "Top Source",
                tint = Red,
                bgColor = LightRed
            )
            AnalyticItem(
                icon = R.drawable.clock,
                value = dashBoardModel.startTime,
                text = "Best Time",
                tint = Yellow,
                bgColor = LightYellow
            )


        }
    }

}

@Composable
fun AnalyticItem(
    icon: Int,
    value: String,
    text: String,
    tint: Color,
    bgColor: Color
) {

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .width(120.dp)
            .clip(SharpBottomRoundedTopShape(8.dp)),

        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 2.dp)
                    .size(35.dp)
                    .background(bgColor.copy(0.8f), RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon), contentDescription = null, tint = tint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
            )
            Text(
                text = text, fontSize = 14.sp, fontWeight = FontWeight.Light,
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
            )

        }
    }


}


@Composable
fun GraphWidget(modifier: Modifier = Modifier) {

    // b'coz the overal_data_chart was returning null, so I had to create a dummy data
    val xAxis = listOf(
        0f, 1f, 2f, 2f, 3f, 4f, 5f, 6f, 7f, 8f
    )
    val yAxis = listOf(
        10f, 25f, 30f, 50f, 80f, 75f, 100f, 50f, 25f, 100f, 75f
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
            ) {

                Text(
                    text = "Overview", fontSize = 14.sp, color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                TextWithIcon(
                    text = "22 Aug - 23 Sept",
                    textSize = 12.sp,
                    R.drawable.clock,
                    tint = Color.Gray
                )
            }

            LineGraph(
                xData = xAxis,
                yData = yAxis,
                dataLabel = "Experiment",
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


    }


}

@Composable
fun TextWithIcon(text: String, textSize: TextUnit=14.sp, icon: Int, tint: Color) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(text = text, fontSize = textSize, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        Icon(
            painter = painterResource(id = icon), contentDescription = null, tint = tint,
            modifier = Modifier.size(16.dp)
        )
    }

}

@Composable
fun LineGraph(
    xData: List<Float>,
    yData: List<Float>,
    dataLabel: String,
    modifier: Modifier = Modifier,
    lineColor: Color = FabColor, // Line color
    fillColor: Color = FabColor, // Fill color
    drawValues: Boolean = false, // Don't draw data values on points
    drawMarkers: Boolean = false, // Draw markers (circles) on points
    xAxisPosition: XAxis.XAxisPosition = XAxis.XAxisPosition.BOTTOM // X-axis position at the bottom
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            val lineChart = LineChart(context)

            val entries: List<Entry> = xData.zip(yData) { x, y -> Entry(x - 1, y) }
            val dataSet = LineDataSet(entries, dataLabel).apply {
                color = lineColor.toArgb()
                setDrawValues(false)
                setDrawCircles(false)
                setDrawFilled(false)
                lineWidth = 3f
                //fillColor = fillColor.toArgb()
                fillAlpha = 0
                setDrawFilled(true)
                fillDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(GradientBlue.toArgb(), Color.Transparent.toArgb())
                )
            }

            lineChart.data = LineData(dataSet)

            // Enable touch gestures
            lineChart.setTouchEnabled(true)

            lineChart.isScaleXEnabled = true
            lineChart.isScaleYEnabled = true

            // Additional styling
            lineChart.description.isEnabled = false
            lineChart.legend.isEnabled = false
            lineChart.setDrawBorders(false)

            lineChart.axisRight.isEnabled = false
            lineChart.setDrawGridBackground(false)

            lineChart.axisLeft.textColor = Color.Gray.toArgb()
            lineChart.xAxis.textColor = Color.Gray.toArgb()
            lineChart.xAxis.position = xAxisPosition
            lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(MONTH_LIST)


            // Remove X and Y axis lines
            lineChart.xAxis.setDrawAxisLine(false)
            lineChart.axisLeft.setDrawAxisLine(false)
            lineChart.axisRight.setDrawAxisLine(false)

            lineChart.xAxis.granularity = 1f
            lineChart.xAxis.axisMinimum = -0.5f
            lineChart.axisLeft.granularity = 25f
            lineChart.axisLeft.axisMinimum = 0f
            lineChart.axisLeft.axisMaximum = 100f
            lineChart.axisLeft.labelCount = 5

            lineChart.xAxis.gridLineWidth = 0.5f
            lineChart.axisLeft.gridLineWidth = 0.5f

            lineChart.xAxis.gridColor = GridColor.toArgb()
            lineChart.axisLeft.gridColor = GridColor.toArgb()

            lineChart.invalidate()
            lineChart
        }
    )
}

@Composable
public fun ErrorScreen(result: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text(text = result, color = Color.Red, fontSize = 24.sp)

    }

}
