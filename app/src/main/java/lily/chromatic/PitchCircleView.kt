package lily.chromatic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PitchCircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    val verticality = Verticality()
    private val circleOfFifths = PitchCircle(verticality)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)
        circleOfFifths.draw(canvas)
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleOfFifths.update(w, h)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        circleOfFifths.onTouch(event)
        invalidate()
        return true
    }
}
