package lily.chromatic

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import kotlin.math.*

class PitchCircle(
    private val verticality: Verticality,
    private val interval: Int = fifthInterval
) {
    private var center = CartesianVector(0f, 0f)
        set(value) {
            field = value
            for (dot in dots) dot.update()
        }
    var radius: Float = 0f
        set(value) {
            field = value
            for (dot in dots) dot.update()
        }
    val dots = Array(pitchNames.length) {
            i -> PitchDot(verticality.pitches[i], this)
    }
    private var dragStartAngle: Float? = null
    private val pitchDotOffsetAngle: Float = 2f * PI.toFloat() / pitchNames.length.toFloat()
    fun update(canvasWidth: Int, canvasHeight: Int) {
        center = CartesianVector((canvasWidth / 2).toFloat(), (canvasHeight / 2).toFloat())
        radius = min(center.x, center.y) * 0.9f
    }
    fun draw(canvas: Canvas) {
        val paint = Paint().apply {
            color = Color.GRAY
            style = Paint.Style.STROKE
            strokeWidth = radius * 0.01f
        }
        canvas.drawCircle(center.x, center.y, radius, paint)
        for (dot in dots) dot.draw(canvas)
    }
    fun getDotCenter(pitchClass: PitchClass): CartesianVector {
        val angle: Float = (pitchIndex(pitchClass.name) * interval).toFloat() * pitchDotOffsetAngle
        return CartesianVector(
            center.x + (radius * cos(angle)),
            center.y + (radius * sin(angle))
        )
    }
    fun onTouch(event: MotionEvent) {
        for (dot in dots) dot.onTouch(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val location = CartesianVector(event.x, event.y).toPolar(center)
                dragStartAngle = if (location.r < radius) location.theta else null
            }
            MotionEvent.ACTION_MOVE -> {
                if (dragStartAngle == null) return
                val location = CartesianVector(event.x, event.y).toPolar(center)
                if (location.r > radius) {
                    dragStartAngle = null
                    return
                }
                val dtheta = location.theta - dragStartAngle!!
                if (abs(dtheta) < pitchDotOffsetAngle) return
                val rotation = (dtheta / pitchDotOffsetAngle).toInt()
                rotate(rotation)
                dragStartAngle = dragStartAngle!! + (rotation.toFloat() * pitchDotOffsetAngle)
            }
        }
    }
    private fun rotate(n: Int) {
        verticality.transpose(interval * n)
    }
}
