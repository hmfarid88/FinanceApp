package com.example.financedpc.device

import org.junit.Assert.assertEquals
import org.junit.Test

class DeviceOwnerStatusTest {

    @Test
    fun deviceOwner_winsOverEverything() {
        assertEquals(
            OwnershipState.DEVICE_OWNER,
            DeviceOwnerStatus.evaluate(
                isDeviceOwnerApp = true, isProfileOwnerApp = true, isAdminActive = true
            )
        )
    }

    @Test
    fun profileOwner_whenNotDeviceOwner() {
        assertEquals(
            OwnershipState.PROFILE_OWNER,
            DeviceOwnerStatus.evaluate(
                isDeviceOwnerApp = false, isProfileOwnerApp = true, isAdminActive = true
            )
        )
    }

    @Test
    fun adminOnly_whenActiveButNotOwner() {
        assertEquals(
            OwnershipState.ADMIN_ACTIVE_NOT_OWNER,
            DeviceOwnerStatus.evaluate(
                isDeviceOwnerApp = false, isProfileOwnerApp = false, isAdminActive = true
            )
        )
    }

    @Test
    fun notAdmin_whenNothingSet() {
        assertEquals(
            OwnershipState.NOT_ADMIN,
            DeviceOwnerStatus.evaluate(
                isDeviceOwnerApp = false, isProfileOwnerApp = false, isAdminActive = false
            )
        )
    }
}
