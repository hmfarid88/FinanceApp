package com.example.financedpc.device

/** How this app is currently related to the DevicePolicyManager. */
enum class OwnershipState {
    DEVICE_OWNER,
    PROFILE_OWNER,
    ADMIN_ACTIVE_NOT_OWNER,
    NOT_ADMIN
}

data class DeviceOwnerStatus(
    val state: OwnershipState,
    val packageName: String,
    val sdkInt: Int,
    val release: String
) {
    val isDeviceOwner: Boolean get() = state == OwnershipState.DEVICE_OWNER

    companion object {
        /**
         * Pure function (no Android dependencies) so it can be unit-tested on the JVM.
         * Precedence: Device Owner > Profile Owner > active admin > nothing.
         */
        fun evaluate(
            isDeviceOwnerApp: Boolean,
            isProfileOwnerApp: Boolean,
            isAdminActive: Boolean
        ): OwnershipState = when {
            isDeviceOwnerApp -> OwnershipState.DEVICE_OWNER
            isProfileOwnerApp -> OwnershipState.PROFILE_OWNER
            isAdminActive -> OwnershipState.ADMIN_ACTIVE_NOT_OWNER
            else -> OwnershipState.NOT_ADMIN
        }
    }
}
