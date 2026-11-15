package com.example.financedpc.device

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import com.example.financedpc.BuildConfig
import com.example.financedpc.admin.FinanceDeviceAdminReceiver

/** Thin wrapper over DevicePolicyManager. All policy calls go through here in later stages. */
class DeviceOwnerManager(private val context: Context) {

    private val dpm: DevicePolicyManager =
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

    val adminComponent: ComponentName =
        ComponentName(context, FinanceDeviceAdminReceiver::class.java)

    fun currentStatus(): DeviceOwnerStatus {
        val pkg = context.packageName
        val state = DeviceOwnerStatus.evaluate(
            isDeviceOwnerApp = dpm.isDeviceOwnerApp(pkg),
            isProfileOwnerApp = dpm.isProfileOwnerApp(pkg),
            isAdminActive = dpm.isAdminActive(adminComponent)
        )
        return DeviceOwnerStatus(
            state = state,
            packageName = pkg,
            sdkInt = Build.VERSION.SDK_INT,
            release = Build.VERSION.RELEASE
        )
    }

    /**
     * Test-only escape hatch so you can re-provision a test device without a factory reset.
     * Hard-gated to debug builds. The production exit/recovery path is designed in a later stage.
     */
    @Suppress("DEPRECATION")
    fun debugReleaseOwnership(): Boolean {
        if (!BuildConfig.DEBUG) return false
        if (!dpm.isDeviceOwnerApp(context.packageName)) return false
        dpm.clearDeviceOwnerApp(context.packageName)
        return true
    }
}
