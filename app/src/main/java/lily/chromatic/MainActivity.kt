package lily.chromatic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        clearButton.setOnClickListener {
            circleOfFifths.verticality.clear()
            circleOfFifths.invalidate()
        }

        playSwitch.setOnCheckedChangeListener { _, isChecked ->
            circleOfFifths.verticality.playing = isChecked
        }
    }
}
