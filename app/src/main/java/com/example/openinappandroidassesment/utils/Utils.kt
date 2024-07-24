package com.example.openinappandroidassesment.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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

class SharpTopRoundedBottomShape(private val bottomCornerRadius: Dp) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val cornerRadiusPx = with(density) { bottomCornerRadius.toPx() }
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - cornerRadiusPx)
            quadraticBezierTo(size.width, size.height, size.width - cornerRadiusPx, size.height)
            lineTo(cornerRadiusPx, size.height)
            quadraticBezierTo(0f, size.height, 0f, size.height - cornerRadiusPx)
            close()
        }
        return Outline.Generic(path)
    }
}

// Custom DrawBehind of SharpTopRoundedBottomShape
fun DrawScope.customSharpTopRoundedBottomShape(cornerRadius: Dp, color : Color) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    val cornerRadiusPx = cornerRadius.toPx()

    // Draw top rectangular dotted line
    drawPath(
        path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - cornerRadiusPx)
            quadraticBezierTo(size.width, size.height, size.width - cornerRadiusPx, size.height)
            lineTo(cornerRadiusPx, size.height)
            quadraticBezierTo(0f, size.height, 0f, size.height - cornerRadiusPx)
            close()
        },
        color = color,
        style = Stroke(width = 1f, pathEffect = pathEffect)
    )
}

// Custom DrawBehind of SharpBottomRoundedTopShape
fun DrawScope.customSharpBottomRoundedTopShape(cornerRadius: Dp, color : Color) {

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    val cornerRadiusPx = cornerRadius.toPx()

    // Draw top rounded dotted line
    drawPath(
        path = Path().apply {
            moveTo(0f, cornerRadiusPx)
            quadraticBezierTo(0f, 0f, cornerRadiusPx, 0f)
            lineTo(size.width - cornerRadiusPx, 0f)
            quadraticBezierTo(size.width, 0f, size.width, cornerRadiusPx)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        },
        color = color,
        style = Stroke(width = 1f, pathEffect = pathEffect)
    )
}


fun copyToClipboard(text: String, context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
}