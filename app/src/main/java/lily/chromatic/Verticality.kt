package lily.chromatic

class Verticality {
    var pitches = Array(PitchClass.names.length) {
            i -> PitchClass(PitchClass.get(i), this)
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
    private fun getPitch(name: Char) = pitches[PitchClass.indexOf(name)].on
    private fun setPitch(name: Char, value: Boolean) {
        pitches[PitchClass.indexOf(name)].on = value
    }
    fun togglePitch(name: Char) {
        pitches[PitchClass.indexOf(name)].toggle()
    }
    fun togglePlay() {
        playing = !playing
    }
    private fun transform(mapping: (PitchClass) -> PitchClass) {
        val verticality = get()
        clear()
        for (pitch in pitches) if (verticality.contains(pitch.name)) mapping(pitch).on = true
    }
    operator fun plusAssign(n: Int) {
        transform {
                pitch: PitchClass -> pitch + n
        }
    }
}
