package com.acuon.moengage.data.pref

class MoEngagePreferences : Preferences() {

    var notificationPermissionGranted by booleanPref(
        MoEngagePreferenceKeyEnums.NOTIFICATION_PERMISSION.value, false
    )
}