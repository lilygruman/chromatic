package lily.chromatic

import kotlin.math.pow

class PitchClass(val name: Char, val verticality: Verticality, on: Boolean = false) {
    companion object {
        const val names = "cndseftglahb"
        fun get(index: Int): Char = names[mod(index)]
        fun indexOf(name: Char): Int = names.indexOf(name)
        private fun normalize(interval: Int): Int = when {
            interval > names.length -> normalize(interval - names.length)
            interval > (names.length / 2) -> names.length - interval
            interval < -names.length -> normalize(interval + names.length)
            interval < (-names.length / 2) -> -names.length - interval
            else -> interval
        }
        private fun mod(n: Int, modulus: Int = names.length): Int {
            var x = n
            while (x < 0) x += modulus
            while (x >= modulus) x -= modulus
            return x
        }
    }
    private val oscillator = PitchOscillator(midi2hz())
    var on = on
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
    private fun midi2hz(): Float {
        val aFrequency = 440f
        val aMidi = 69
        val cMidi = 60
        val midiOffset = indexOf(name) + cMidi - aMidi
        val fractionOfOctave = midiOffset.toFloat() / names.length.toFloat()
        return aFrequency * 2f.pow(fractionOfOctave)
    }
    operator fun minus(other: PitchClass): Int = normalize(indexOf(name) - indexOf(other.name))
    operator fun plus(n: Int) = verticality.pitches[mod(indexOf(name) + n)]
}
