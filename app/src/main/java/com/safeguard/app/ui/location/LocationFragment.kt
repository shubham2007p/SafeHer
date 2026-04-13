package com.safeguard.app.ui.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.safeguard.app.R
import com.safeguard.app.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!
    private var isSharing = false

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) startSharing()
            else Toast.makeText(requireContext(), "Location permission required", Toast.LENGTH_LONG).show()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToggleLocation.setOnClickListener {
            if (isSharing) stopSharing()
            else checkPermissionAndStart()
        }
    }

    private fun checkPermissionAndStart() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> startSharing()
            else -> requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun startSharing() {
        isSharing = true
        binding.tvLocationStatus.text = getString(R.string.sharing_active)
        binding.tvLocationStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_active))
        binding.btnToggleLocation.text = getString(R.string.stop_sharing)
        binding.btnToggleLocation.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.red_sos)
    }

    private fun stopSharing() {
        isSharing = false
        binding.tvLocationStatus.text = getString(R.string.sharing_inactive)
        binding.tvLocationStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        binding.btnToggleLocation.text = getString(R.string.start_sharing)
        binding.btnToggleLocation.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.purple_primary)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
