package lily.chromatic

class Verticality {
    var pitches = Array(pitchNames.length) {
            i -> PitchClass(pitchName(i), this)
    }
    var playing: Boolean = false
        set(value) {
            field = value
            for (pitch in pitches) if (value) pitch.play() else pitch.stop()
        }
    fun size(): Int {
        var count = 0
        for (pitch in pitches) if (pitch.on) count++
        return count
    }
    fun clear() {
        for (pitch in pitches) pitch.on = false
    }
    fun set(vararg verticality: Char) {
        clear()
        for (pitch in pitches) if (pitch.name in verticality) pitch.on = true
    }
    fun get(): ArrayList<Char> {
        val verticality = ArrayList<Char>()
        for (pitch in pitches) if (pitch.on) verticality.add(pitch.name)
        return verticality
    }
    private fun getPitch(name: Char) = pitches[pitchIndex(name)].on
    private fun setPitch(name: Char, value: Boolean) {
        pitches[pitchIndex(name)].on = value
    }
    fun togglePitch(name: Char) {
        pitches[pitchIndex(name)].toggle()
    }
    fun togglePlay() {
        playing = !playing
    }
    private fun transform(mapping: (PitchClass) -> PitchClass) {
        val verticality = get()
        clear()
        for (pitch in pitches) if (verticality.contains(pitch.name)) mapping(pitch).on = true
    }
    fun transpose(n: Int) {
        transform {
            pitch: PitchClass -> pitch.transpose(n)
        }
    }
}
