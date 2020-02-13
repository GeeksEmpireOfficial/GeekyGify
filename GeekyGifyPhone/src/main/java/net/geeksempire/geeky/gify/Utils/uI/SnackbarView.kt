/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 12:51 PM
 * Last modified 2/13/20 12:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.uI

import android.os.Handler
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SnackbarView {

    fun snackBarViewFail(appCompatActivity: AppCompatActivity, viewGroup: ViewGroup, errorMessage: String) {
        val snackbarError = Snackbar.make(viewGroup, errorMessage, Snackbar.LENGTH_LONG)
        snackbarError.show()
        snackbarError.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onShown(transientBottomBar: Snackbar?) {
                super.onShown(transientBottomBar)
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)

                Handler().postDelayed({
                    appCompatActivity.finish()
                }, 500)
            }
        })
    }

    fun snackBarViewSuccess(context: AppCompatActivity, viewGroup: ViewGroup, successMessage: String) {
        val snackbarError = Snackbar.make(viewGroup, successMessage, Snackbar.LENGTH_LONG)
        snackbarError.show()
        snackbarError.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onShown(transientBottomBar: Snackbar?) {
                super.onShown(transientBottomBar)
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)

                Handler().postDelayed({
                    context.finish()
                }, 500)
            }
        })
    }
}