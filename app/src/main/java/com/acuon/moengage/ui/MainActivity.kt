package com.acuon.moengage.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.acuon.moengage.ui.home.viewmodel.HomeViewModel
import com.acuon.moengage.BR
import com.acuon.moengage.R
import com.acuon.moengage.data.pref.MoEngagePreferences
import com.acuon.moengage.databinding.ActivityMainBinding
import com.acuon.moengage.utils.dialogs.showPermissionNecessaryDialog
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()
    private val pref by lazy { MoEngagePreferences() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setVariable(BR.vm, viewModel)
        initFirebase()
        checkPermissions()
    }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.e("FirebaseToken", "failed to receive token")
            }
            Log.e("FirebaseToken", "${it.result}")
        }
    }

    private fun checkPermissions() {
        if (!pref.notificationPermissionGranted) {
            notificationPermission.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
    }

    private var notificationPermissionGranted = true

    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if (!it.value) notificationPermissionGranted = false
            }
            if (!notificationPermissionGranted) {
                pref.notificationPermissionGranted = false
                showPermissionNecessaryDialog(
                    this,
                    getString(R.string.notification_permission_needed)
                )
            } else pref.notificationPermissionGranted = true
        }
}