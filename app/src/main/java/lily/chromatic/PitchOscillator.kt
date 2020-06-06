package lily.chromatic

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlin.math.PI
import kotlin.math.sin

class PitchOscillator(frequency: Float) {
    private val audioTrack = createAudioTrack()
    private val bufferSize = (audioTrack.sampleRate.toFloat() / frequency).toInt()
    init {
        val buffer = FloatArray(bufferSize) {
                t -> sin(2f * PI.toFloat() * t.toFloat() / bufferSize.toFloat())
        }
        audioTrack.write(buffer, 0, bufferSize, AudioTrack.WRITE_BLOCKING)
        audioTrack.setLoopPoints(0, bufferSize, -1)

        audioTrack.setVolume(1f / PitchClass.names.length.toFloat())
    }
    fun play() {
        audioTrack.play()
    }
    fun stop() {
        audioTrack.pause()
    }
    private fun createAudioTrack(): AudioTrack {
        val attributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        val format = AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_FLOAT)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()
        return AudioTrack.Builder()
            .setAudioAttributes(attributes)
            .setAudioFormat(format)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .setBufferSizeInBytes(48000 * 8)   // TODO: Switch to format.frameSizeInBytes
            .build()
    }
}
