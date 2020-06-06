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
    fun clear() {
        for (pitch in pitches) pitch.on = false
    }
    fun set(vararg verticality: Char) {
        clear()
        for (pitch in pitches) if (pitch.name in verticality) pitch.on = true
    }
    fun get(): ArrayList<Char> {
        return ArrayList<Char>().apply {
            for (pitch in pitches) if (pitch.on) add(pitch.name)
        }
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
