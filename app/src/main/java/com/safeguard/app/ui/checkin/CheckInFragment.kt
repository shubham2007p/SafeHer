package com.safeguard.app.ui.checkin

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.safeguard.app.R
import com.safeguard.app.databinding.FragmentCheckInBinding

class CheckInFragment : Fragment() {

    private var _binding: FragmentCheckInBinding? = null
    private val binding get() = _binding!!

    private var countdownTimer: CountDownTimer? = null
    private var timerActive = false

    private val durations = mapOf(
        "15 Minutes" to 15 * 60 * 1000L,
        "30 Minutes" to 30 * 60 * 1000L,
        "1 Hour" to 60 * 60 * 1000L,
        "2 Hours" to 2 * 60 * 60 * 1000L
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            durations.keys.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDuration.adapter = adapter

        binding.btnSetTimer.setOnClickListener {
            val key = binding.spinnerDuration.selectedItem as String
            val millis = durations[key] ?: 15 * 60 * 1000L
            startTimer(millis)
        }

        binding.btnImSafe.setOnClickListener { resetTimer() }
    }

    private fun startTimer(millis: Long) {
        countdownTimer?.cancel()
        timerActive = true
        binding.tvTimerStatus.text = getString(R.string.timer_active)
        binding.btnImSafe.visibility = View.VISIBLE
        binding.spinnerDuration.isEnabled = false

        countdownTimer = object : CountDownTimer(millis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = millisUntilFinished / 3_600_000
                val minutes = (millisUntilFinished % 3_600_000) / 60_000
                val seconds = (millisUntilFinished % 60_000) / 1000
                binding.tvCountdown.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }

            override fun onFinish() {
                binding.tvCountdown.text = "00:00:00"
                binding.tvTimerStatus.text = "Time's up! Alert sent."
                binding.tvTimerStatus.setTextColor(resources.getColor(R.color.red_sos, null))
                timerActive = false
            }
        }.start()
    }

    private fun resetTimer() {
        countdownTimer?.cancel()
        timerActive = false
        binding.tvTimerStatus.text = getString(R.string.timer_inactive)
        binding.tvTimerStatus.setTextColor(resources.getColor(R.color.text_secondary, null))
        binding.tvCountdown.text = ""
        binding.btnImSafe.visibility = View.GONE
        binding.spinnerDuration.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countdownTimer?.cancel()
        _binding = null
    }
}
