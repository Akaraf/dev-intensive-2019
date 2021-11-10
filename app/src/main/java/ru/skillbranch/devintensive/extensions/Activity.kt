package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    var inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Activity.isKeyboardOpen() : Boolean {
    val rect = Rect()
    val rootView = this.getRootView()
    rootView.getWindowVisibleDisplayFrame(rect)
    val hDiff = rootView.height - rect.height()
    return hDiff > 0.25 * rootView.height
}

fun Activity.isKeyboardClosed() : Boolean {
    return !this.isKeyboardOpen()
}

fun Activity.getRootView() : View {
    return findViewById<View>(android.R.id.content)
}