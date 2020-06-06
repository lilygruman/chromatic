package lily.chromatic

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import kotlin.math.hypot

class PitchDot(private val pitchClass: PitchClass, private val pitchCircle: PitchCircle) {
    private var center = pitchCircle.getDotCenter(pitchClass)
    private var radius: Float = pitchCircle.radius * 0.08f
    fun update() {
        center = pitchCircle.getDotCenter(pitchClass)
        radius = pitchCircle.radius * 0.08f
    }
    private fun drawDot(canvas: Canvas) {
        val paint = Paint().apply {
            color = if (pitchClass.on) Color.GREEN else Color.WHITE
        }
        canvas.drawCircle(center.x, center.y, radius, paint)
    }
    private fun drawName(canvas: Canvas) {
        val paint = Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = radius
        }
        val text = pitchClass.name.toString().toUpperCase()
        canvas.drawText(
            text,
            center.x,
            center.y - ((paint.descent() + paint.ascent()) / 2f),
            paint
        )
    }
    private fun drawIntervalLines(canvas: Canvas) {
        if (!pitchClass.on) return
        for (otherPitchClass in pitchClass.verticality.pitches) {
            if (!otherPitchClass.on) continue
            val interval = normalize(
                pitchIndex(otherPitchClass.name) - pitchIndex(pitchClass.name)
            )
            if ((interval < 1) or (interval > intervalColors.lastIndex)) continue
            val paint = Paint().apply {
                color = intervalColors[interval]
                strokeWidth = radius * 0.1f
            }
            val otherPitchDot = pitchCircle.dots[pitchIndex((otherPitchClass.name))]
            canvas.drawLine(
                center.x,
                center.y,
                otherPitchDot.center.x,
                otherPitchDot.center.y,
                paint
            )
        }
    }
    fun draw(canvas: Canvas) {
        drawIntervalLines(canvas)
        drawDot(canvas)
        drawName(canvas)
    }
    fun onTouch(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val distance = hypot(center.x - event.x, center.y - event.y)
                if (distance < radius) pitchClass.toggle()
            }
        }
    }
}
