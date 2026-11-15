package com.example.financedpc.provisioning

import android.app.Activity
import android.os.Bundle

/**
 * Called by the system at the end of managed-device provisioning (Android 11+).
 * Stage 1: nothing to configure yet, so report success immediately.
 * Later stages: show the consent/terms screen here before returning RESULT_OK.
 */
class PolicyComplianceActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_OK)
        finish()
    }
}
