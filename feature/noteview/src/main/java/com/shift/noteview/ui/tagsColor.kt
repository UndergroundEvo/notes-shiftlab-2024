package com.shift.noteview.ui


import androidx.compose.ui.graphics.Color

fun tagsColor(tag: String): Color{
    val colors = listOf(
        Color(0xFFE57373),
        Color(0xFFF06292),
        Color(0xFFBA68C8),
        Color(0xFF9575CD),
        Color(0xFF5C6BC0),
        Color(0xFF42A5F5),
        Color(0xFF29B6F6),
        Color(0xFF26C6DA),
        Color(0xFF26C6DA),
        Color(0xFF4DB6AC),
        Color(0xFF4DB6AC),
        Color(0xFF81C784),
        Color(0xFFAED581),
        Color(0xFFDCE775),
        Color(0xFFFFF176),
        Color(0xFFFFD54F),
        Color(0xFFFFB74D),
        Color(0xFFFF8A65)
    )
    var sum : Int = 0
    tag.toCharArray().map {sum += it.toInt()}
    return colors[sum.mod(colors.size)]
}