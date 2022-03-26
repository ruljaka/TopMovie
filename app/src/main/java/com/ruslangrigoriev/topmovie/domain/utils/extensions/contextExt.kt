package com.ruslangrigoriev.topmovie.domain.utils.extensions

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.ruslangrigoriev.topmovie.domain.utils.*

fun Context.onBoardingFinished() {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putBoolean(OB_FINISHED, true)
    editor.apply()
}

fun Context.onBoardingIsFinished(): Boolean {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(OB_FINISHED, false)
}

fun Context.saveSessionID(sessionID: String) {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString(SESSION, sessionID)
    editor.apply()
}

fun Context.getSessionID(): String? {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return sharedPref.getString(SESSION, "")
}

fun Context.saveUserID(userID: Int) {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putInt(USER_ID, userID)
    editor.apply()
}

fun Context.getUserID(): Int {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return sharedPref.getInt(USER_ID, 0)

}

fun Context.saveColorMode(id: Int) {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putInt(MODE_NIGHT, id)
    editor.apply()
}

fun Context.getColorMode(): Int {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return sharedPref.getInt(MODE_NIGHT, 0)
}

fun changeColorMode(id: Int) {
    when (id) {
        0 -> AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
        1 -> AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
        2 -> AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        )
        3 -> AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        )
    }
}