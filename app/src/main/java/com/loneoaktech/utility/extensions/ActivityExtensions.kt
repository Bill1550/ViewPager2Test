@file:Suppress("unused")

package com.loneoaktech.utility.extensions

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle

/**
 * Generally useful extensions for Activity class
 * Created by BillH on 3/15/2018.
 */
//fun Activity.hideKeyboard() {
//    (currentFocus ?: View(this)).let{ v ->
//        inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
//    }
//}

/**
 * Disable the form auto fill functionality. Good for api >= 26
 */
@TargetApi(Build.VERSION_CODES.O)
fun Activity.disableAutoFill() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        window.decorView.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
}


fun AppCompatActivity.fragmentTransact(block: FragmentTransaction.()->Unit ) {
    supportFragmentManager.beginTransaction().apply {
        block.invoke(this)
    }.commit()
}


//
//fun View.showKeyboard() {
//        context?.inputMethodManager?.showSoftInput(this, 0)
//}
//
//fun View.hideKeyboard() {
//    rootView?.let { v ->
//        v.context.inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
//    }
//}

/**
 * Returns true if the activity is in the CREATED lifecycle location.
 */
val AppCompatActivity.isCreated: Boolean
        get() = lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)