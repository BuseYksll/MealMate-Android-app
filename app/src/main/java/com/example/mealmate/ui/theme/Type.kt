package com.example.mealmate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mealmate.R

// Set of Material typography styles to start with
val Poppins = FontFamily(
    Font(R.font.poppins_thin, FontWeight.Thin),
    Font(R.font.poppins_extralight, FontWeight.ExtraLight),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_italic, FontWeight.Normal, style = androidx.compose.ui.text.font.FontStyle.Italic),
    Font(R.font.poppins_thin_italic, FontWeight.Thin, style = androidx.compose.ui.text.font.FontStyle.Italic),
    Font(R.font.poppins_extralight_italic, FontWeight.ExtraLight, style = androidx.compose.ui.text.font.FontStyle.Italic),
    Font(R.font.poppins_light_italic, FontWeight.Light, style = androidx.compose.ui.text.font.FontStyle.Italic),
    Font(R.font.poppins_medium_italic, FontWeight.Medium, style = androidx.compose.ui.text.font.FontStyle.Italic),
    Font(R.font.poppins_semibold_italic, FontWeight.SemiBold, style = androidx.compose.ui.text.font.FontStyle.Italic),
    Font(R.font.poppins_bold_italic, FontWeight.Bold, style = androidx.compose.ui.text.font.FontStyle.Italic),
    Font(R.font.poppins_extrabold_italic, FontWeight.ExtraBold, style = androidx.compose.ui.text.font.FontStyle.Italic),
    Font(R.font.poppins_black_italic, FontWeight.Black, style = androidx.compose.ui.text.font.FontStyle.Italic)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)