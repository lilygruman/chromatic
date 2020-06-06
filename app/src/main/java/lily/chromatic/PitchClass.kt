package lily.chromatic

import kotlin.math.pow

class PitchClass(val name: Char, val verticality: Verticality, on: Boolean = false) {
    private val oscillator = PitchOscillator(midi2hz())
    var on: Boolean = on
        set(value) {
            field = value
            if (value and verticality.playing) play() else if (!value) stop()
        }
    fun toggle() {
        on = !on
    }
    fun play() {
        if (on) oscillator.play()
    }
    fun stop() {
        oscillator.stop()
    }
    fun transpose(n: Int) = verticality.pitches[mod(pitchIndex(name) + n)]
    private fun midi2hz(): Float {
        val aFrequency = 440f
        val aMidi = 69
        val cMidi = 60
        val midiOffset = pitchIndex(name) + cMidi - aMidi
        val fractionOfOctave = midiOffset.toFloat() / pitchNames.length.toFloat()
        return aFrequency * octaveRatio.toFloat().pow(fractionOfOctave)
    }

}
