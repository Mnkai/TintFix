package moe.minori.tintfix.utils.screenbrightness

import androidx.annotation.IntRange

data class ScreenBrightness(
    @IntRange(from = 0, to = 100)
    val percentage: Int
)

val maxBrightness = ScreenBrightness(percentage = 100)
val minBrightness = ScreenBrightness(percentage = 0)