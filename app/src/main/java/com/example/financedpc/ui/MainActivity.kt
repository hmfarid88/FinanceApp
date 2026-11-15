package com.example.financedpc.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.financedpc.BuildConfig
import com.example.financedpc.R
import com.example.financedpc.device.DeviceOwnerManager
import com.example.financedpc.device.OwnershipState

class MainActivity : AppCompatActivity() {

    private lateinit var manager: DeviceOwnerManager
    private lateinit var tvStatus: TextView
    private lateinit var tvDetail: TextView
    private lateinit var tvEnv: TextView
    private lateinit var tvHelpTitle: TextView
    private lateinit var tvHelp: TextView
    private lateinit var btnRelease: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = DeviceOwnerManager(applicationContext)
        tvStatus = findViewById(R.id.tvStatus)
        tvDetail = findViewById(R.id.tvDetail)
        tvEnv = findViewById(R.id.tvEnv)
        tvHelpTitle = findViewById(R.id.tvHelpTitle)
        tvHelp = findViewById(R.id.tvHelp)
        btnRelease = findViewById(R.id.btnRelease)

        findViewById<Button>(R.id.btnRefresh).setOnClickListener { render() }
        btnRelease.setOnClickListener { confirmRelease() }
    }

    override fun onResume() {
        super.onResume()
        render()
    }

    private fun render() {
        val status = manager.currentStatus()

        tvStatus.setText(
            when (status.state) {
                OwnershipState.DEVICE_OWNER -> R.string.status_device_owner
                OwnershipState.PROFILE_OWNER -> R.string.status_profile_owner
                OwnershipState.ADMIN_ACTIVE_NOT_OWNER -> R.string.status_admin_only
                OwnershipState.NOT_ADMIN -> R.string.status_not_admin
            }
        )
        tvDetail.setText(
            if (status.isDeviceOwner) R.string.detail_device_owner else R.string.detail_not_owner
        )
        tvEnv.text = getString(
            R.string.env_format,
            status.packageName,
            status.release,
            status.sdkInt,
            if (BuildConfig.DEBUG) "debug" else "release"
        )

        val helpVisibility = if (status.isDeviceOwner) View.GONE else View.VISIBLE
        tvHelpTitle.visibility = helpVisibility
        tvHelp.visibility = helpVisibility
        tvHelp.text = getString(R.string.help_body, manager.adminComponent.flattenToShortString())

        btnRelease.visibility =
            if (BuildConfig.DEBUG && status.isDeviceOwner) View.VISIBLE else View.GONE
    }

    private fun confirmRelease() {
        AlertDialog.Builder(this)
            .setTitle(R.string.release_confirm_title)
            .setMessage(R.string.release_confirm_msg)
            .setPositiveButton(R.string.release_confirm_yes) { _, _ ->
                val ok = manager.debugReleaseOwnership()
                Toast.makeText(
                    this,
                    if (ok) R.string.release_done else R.string.release_failed,
                    Toast.LENGTH_SHORT
                ).show()
                render()
            }
            .setNegativeButton(R.string.release_confirm_no, null)
            .show()
    }
}
