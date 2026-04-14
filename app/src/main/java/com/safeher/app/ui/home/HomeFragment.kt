package com.safeher.app.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.*
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.safeher.app.R
import com.safeher.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var holdHandler: Handler? = null
    private var holdRunnable: Runnable? = null
    private val HOLD_DURATION = 1500L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe contact count
        viewModel.contactCount.observe(viewLifecycleOwner) { count ->
            binding.tvContactCount.text = "$count Emergency Contacts"
        }

        setupSosButton()
    }

    private fun setupSosButton() {
        holdHandler = Handler(Looper.getMainLooper())
        holdRunnable = Runnable { triggerSOS() }

        binding.btnSos.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Scale animation
                    v.animate().scaleX(0.93f).scaleY(0.93f).setDuration(200).start()
                    holdHandler?.postDelayed(holdRunnable!!, HOLD_DURATION)
                    vibrate(50)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
                    holdHandler?.removeCallbacks(holdRunnable!!)
                    true
                }
                else -> false
            }
        }
    }

    private fun triggerSOS() {
        vibrate(500)
        val contacts = viewModel.allContacts.value
        if (contacts.isNullOrEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.no_contacts_sos), Toast.LENGTH_LONG).show()
            return
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            val message = "🆘 SOS! I need help! Please contact me immediately. - SafeHer Alert"
            contacts.forEach { contact ->
                try {
                    SmsManager.getDefault().sendTextMessage(contact.phone, null, message, null, null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            Toast.makeText(requireContext(), getString(R.string.sos_sent), Toast.LENGTH_LONG).show()
        } else {
            requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 101)
            Toast.makeText(requireContext(), "SMS permission required for SOS alert", Toast.LENGTH_LONG).show()
        }
    }

    private fun vibrate(duration: Long) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = requireContext().getSystemService(android.os.VibratorManager::class.java)
            vm?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            requireContext().getSystemService(android.content.Context.VIBRATOR_SERVICE) as? Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(duration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        holdHandler?.removeCallbacks(holdRunnable!!)
        _binding = null
    }
}
