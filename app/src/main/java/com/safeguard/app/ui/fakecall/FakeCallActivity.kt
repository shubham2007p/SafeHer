package com.safeguard.app.ui.fakecall

import android.os.*
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.safeguard.app.databinding.ActivityFakeCallBinding

class FakeCallActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFakeCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show on lock screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }

        binding = ActivityFakeCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val callerName = intent.getStringExtra("caller_name") ?: "Mom"
        binding.tvCallerName.text = callerName

        // Vibrate like a real phone ringing
        vibrateRing()

        binding.btnDecline.setOnClickListener { finish() }
        binding.btnAccept.setOnClickListener {
            // Stop vibration on accept - just stay on a blank "call" for a few seconds
            stopVibration()
            Handler(Looper.getMainLooper()).postDelayed({ finish() }, 3000)
        }
    }

    private var vibrator: Vibrator? = null

    private fun vibrateRing() {
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (getSystemService(VIBRATOR_MANAGER_SERVICE) as android.os.VibratorManager).defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        val pattern = longArrayOf(0, 400, 800, 400, 800)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createWaveform(pattern, 0))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(pattern, 0)
        }
    }

    private fun stopVibration() = vibrator?.cancel()

    override fun onDestroy() {
        super.onDestroy()
        stopVibration()
    }
}
