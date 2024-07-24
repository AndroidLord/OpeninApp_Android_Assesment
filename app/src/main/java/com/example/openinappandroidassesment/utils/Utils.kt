package com.example.openinappandroidassesment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


fun formatDate_Day_Month_Year(inputDate: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return try {
        formatter.format(parser.parse(inputDate)!!)
    } catch (e: java.text.ParseException) {
        // Return the original input if parsing fails
        inputDate
    }
}

fun limitText(text: String, limit: Int, limiter : Boolean = true): String {


    return if (text.length > limit && limiter) {
        text.take(limit) + "..."
    } else {
        text
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}


class SharpBottomRoundedTopShape(private val topCornerRadius: Dp) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val cornerRadiusPx = with(density) { topCornerRadius.toPx() }
        val path = Path().apply {
            moveTo(0f, cornerRadiusPx)
            quadraticBezierTo(0f, 0f, cornerRadiusPx, 0f)
            lineTo(size.width - cornerRadiusPx, 0f)
            quadraticBezierTo(size.width, 0f, size.width, cornerRadiusPx)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

class SharpTopRoundedBottomShape(private val cornerRadius: Dp) : Shape {
    override fun createOutline(size: androidx.compose.ui.geometry.Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val pxRadius = with(density) { cornerRadius.toPx() }
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - pxRadius)
            quadraticBezierTo(size.width, size.height, size.width - pxRadius, size.height)
            lineTo(pxRadius, size.height)
            quadraticBezierTo(0f, size.height, 0f, size.height - pxRadius)
            close()
        }
        return Outline.Generic(path)
    }
}

