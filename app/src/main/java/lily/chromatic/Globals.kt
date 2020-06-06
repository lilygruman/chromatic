package lily.chromatic

import android.graphics.Color
import kotlin.math.pow

const val pitchNames = "cndseftglahb"
const val octaveRatio = 2
const val fifthInterval = 7
const val semitoneInterval = 1
val intervalColors = intArrayOf(
    Color.TRANSPARENT,
    Color.YELLOW,
    Color.MAGENTA,
    Color.GREEN,
    Color.CYAN,
    Color.BLUE,
    Color.RED
)

fun normalize(interval: Int): Int {
    when {
        interval > pitchNames.length -> {
            return normalize(interval - pitchNames.length)
        }
        interval > (pitchNames.length / 2) -> {
            return pitchNames.length - interval
        }
        interval < -pitchNames.length -> {
            return normalize(interval + pitchNames.length)
        }
        interval < (-pitchNames.length / 2) -> {
            return -pitchNames.length - interval
        }
        else -> {
            return interval
        }
    }
}

fun mod(n: Int, modulus: Int = pitchNames.length): Int {
    var x = n
    while(x < 0) {
        x += modulus
    }
    return x % modulus
}

fun mod(n: Float, modulus: Float): Float {
    var x = n
    while (x < 0) x += modulus
    while (x >= modulus) x -= modulus
    return x
}

fun pitchName(index: Int): Char = pitchNames[mod(index)]
fun pitchIndex(name: Char): Int = pitchNames.indexOf(name)
