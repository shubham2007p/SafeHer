package com.safeguard.app.ui.fakecall

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.safeguard.app.databinding.FragmentFakeCallBinding

class FakeCallFragment : Fragment() {

    private var _binding: FragmentFakeCallBinding? = null
    private val binding get() = _binding!!

    private val delayOptions = mapOf(
        "Immediate" to 0L,
        "1 Minute" to 60_000L,
        "2 Minutes" to 120_000L,
        "5 Minutes" to 300_000L
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFakeCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            delayOptions.keys.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDelay.adapter = adapter

        binding.btnStartFakeCall.setOnClickListener {
            val callerName = binding.etCallerName.text.toString().ifBlank { "Unknown" }
            val delayKey = binding.spinnerDelay.selectedItem as String
            val delayMs = delayOptions[delayKey] ?: 0L

            if (delayMs == 0L) {
                launchFakeCall(callerName)
            } else {
                Toast.makeText(requireContext(), "Fake call scheduled in $delayKey", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isAdded) launchFakeCall(callerName)
                }, delayMs)
            }
        }
    }

    private fun launchFakeCall(callerName: String) {
        val intent = Intent(requireContext(), FakeCallActivity::class.java)
        intent.putExtra("caller_name", callerName)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
