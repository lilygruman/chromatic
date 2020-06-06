package lily.chromatic

import kotlin.math.cos
import kotlin.math.sin

class PolarVector(val r: Float, val theta: Float) {
    fun toCartesian(origin: CartesianVector) = CartesianVector(
        origin.x + (r * cos(theta)),
        origin.y + (r * sin(theta))
    )
}
