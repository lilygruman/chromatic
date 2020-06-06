package lily.chromatic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            val circleOfFifths = findViewById<PitchCircleView>(R.id.circleOfFifths)
            circleOfFifths.verticality.clear()
            circleOfFifths.invalidate()
        }
        findViewById<Switch>(R.id.playSwitch).setOnCheckedChangeListener { _, isChecked ->
            findViewById<PitchCircleView>(R.id.circleOfFifths).verticality.playing = isChecked
        }
    }
}
