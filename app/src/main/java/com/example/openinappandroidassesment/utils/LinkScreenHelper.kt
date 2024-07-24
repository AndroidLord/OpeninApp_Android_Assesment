package com.example.openinappandroidassesment.utils

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.example.openinappandroidassesment.R
import com.example.openinappandroidassesment.models.DashBoardModel
import com.example.openinappandroidassesment.models.Link
import com.example.openinappandroidassesment.ui.theme.BaseColor
import com.example.openinappandroidassesment.ui.theme.Blue
import com.example.openinappandroidassesment.ui.theme.FabColor
import com.example.openinappandroidassesment.ui.theme.LightBlue
import com.example.openinappandroidassesment.ui.theme.LightPurple
import com.example.openinappandroidassesment.ui.theme.LightRed
import com.example.openinappandroidassesment.ui.theme.LightYellow
import com.example.openinappandroidassesment.ui.theme.Purple
import com.example.openinappandroidassesment.ui.theme.Red
import com.example.openinappandroidassesment.ui.theme.UnselectedColor
import com.example.openinappandroidassesment.ui.theme.Yellow
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
                    color = if (topLink) Color.White else UnselectedColor
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
                    color = if (!topLink) Color.White else UnselectedColor
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
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        fontSize = 16.sp
    )
}

@Composable
fun HorizontalAnalytics(dashBoardModel: DashBoardModel) {

    LazyRow {
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
            .height(120.dp)
            .clip(SharpBottomRoundedTopShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .size(30.dp)
                    .background(bgColor.copy(0.8f), RoundedCornerShape(15.dp))
                ,
                contentAlignment = Alignment.Center
            ){
                Icon(painter = painterResource(id = icon), contentDescription = null,tint=tint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
            )
            Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Light,
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
                )

        }
    }


}
