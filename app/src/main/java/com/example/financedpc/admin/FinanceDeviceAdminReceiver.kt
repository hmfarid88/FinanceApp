package com.example.financedpc.admin

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.financedpc.R

/**
 * Device Policy Controller entry point.
 *
 * Keep this class thin: receivers run on the main thread with a very short
 * execution window. Heavy work (policy sync, network) belongs in WorkManager (Stage 4+).
 */
class FinanceDeviceAdminReceiver : DeviceAdminReceiver() {

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.i(TAG, "Device admin enabled")
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Log.i(TAG, "Device admin disabled")
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
        return context.getString(R.string.admin_disable_requested)
    }

    private companion object {
        const val TAG = "FinanceDeviceAdmin"
    }
}
