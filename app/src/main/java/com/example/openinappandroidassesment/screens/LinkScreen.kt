package com.example.openinappandroidassesment.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.openinappandroidassesment.R
import com.example.openinappandroidassesment.models.DashBoardModel
import com.example.openinappandroidassesment.network.NetworkResponse
import com.example.openinappandroidassesment.ui.theme.BaseColor
import com.example.openinappandroidassesment.ui.theme.Blue
import com.example.openinappandroidassesment.ui.theme.BorderGreen
import com.example.openinappandroidassesment.ui.theme.FabColor
import com.example.openinappandroidassesment.ui.theme.Green
import com.example.openinappandroidassesment.ui.theme.LightGreen
import com.example.openinappandroidassesment.ui.theme.LightBlue
import com.example.openinappandroidassesment.ui.theme.UnselectedColor
import com.example.openinappandroidassesment.utils.GreetingMessage
import com.example.openinappandroidassesment.utils.HorizontalAnalytics
import com.example.openinappandroidassesment.utils.IconWithText
import com.example.openinappandroidassesment.utils.LinkTabList
import com.example.openinappandroidassesment.viewModel.MainViewModel

@Composable
fun LinkScreen(navController: NavController, viewModel: MainViewModel) {

    val dashboardResult = viewModel.dashboardResult.observeAsState()

    when (val result = dashboardResult.value) {
        is NetworkResponse.Error -> {
            Text(text = result.message)
        }

        is NetworkResponse.Loading -> {
            LoadingScreen()
        }

        is NetworkResponse.Success -> {
            LinkScreen_Success(result.value)
        }

        null -> {
            Text(text = "Intializing...")
        }
    }


}

@Composable
fun LoadingScreen() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            CircularProgressIndicator()
            Text(text = "Loading")
        }
    }

}

@Composable
fun LinkScreen_Success(dashBoard: DashBoardModel) {

    val scrollState = rememberScrollState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(124.dp)
                .background(FabColor)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "DashBoard",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(BaseColor.copy(alpha = 0.2f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wrench),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

        }

        GreetingMessage()
        Text(
            text = "Ajay Manav 👋",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            fontSize = 24.sp
        )

        // Horizontal Scroll Analytics
        HorizontalAnalytics(dashBoard)

        // View Analytics
        IconWithText(
            icon = R.drawable.priceboost,
            text = "View Analytics",
            center = true,
            rowModifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, UnselectedColor, RoundedCornerShape(10.dp))
                .align(Alignment.CenterHorizontally)
            ,
            iconModifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp)
        )

        // Link Tab List
        LinkTabList(dashBoard.data.top_links, dashBoard.data.recent_links)

        // View All Links

        IconWithText(
            icon = R.drawable.baseline_link_24,
            text = "View All Links",
            center = true,
            rowModifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(10.dp))

                .border(1.dp, UnselectedColor, RoundedCornerShape(10.dp))
            ,
            iconModifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp)
                .graphicsLayer { rotationZ = -50f }
        )

        // Whatsapp and FAQ
        IconWithText(
            icon = R.drawable.whatsapp,
            text = "Talk with us",
            tint = Green,
            rowModifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(LightGreen)
                .border(1.dp, BorderGreen, RoundedCornerShape(10.dp))
            ,
            iconModifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp)
        )
        IconWithText(
            icon = R.drawable.questionmark,
            text = "Frequency Asked Questions",
            tint = Blue,
            rowModifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(LightBlue)
                .clip(RoundedCornerShape(10.dp)),
            iconModifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp)
        )

    }

}
