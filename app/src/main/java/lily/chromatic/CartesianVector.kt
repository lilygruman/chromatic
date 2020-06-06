package lily.chromatic

import kotlin.math.atan2
import kotlin.math.hypot

class CartesianVector(val x: Float, val y: Float) {
    fun toPolar(origin: CartesianVector) = PolarVector(
        hypot(x - origin.x, y - origin.y),
        atan2(y - origin.y, x - origin.x)
    )
}
