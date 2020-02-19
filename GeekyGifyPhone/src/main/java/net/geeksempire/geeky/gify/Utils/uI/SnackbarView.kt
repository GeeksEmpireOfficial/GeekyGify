/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 5:54 PM
 * Last modified 2/18/20 5:51 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.uI

import android.os.Handler
import android.view.View
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
            }
        })
    }

    fun snackBarViewSuccess(appCompatActivity: AppCompatActivity, viewGroup: ViewGroup, successMessage: String) {
        val snackbarSuccess = Snackbar.make(viewGroup, successMessage, Snackbar.LENGTH_LONG)
        snackbarSuccess.show()
        snackbarSuccess.setAction(appCompatActivity.getString(android.R.string.ok), object : View.OnClickListener{
            override fun onClick(view: View?) {

                Handler().postDelayed({
                    appCompatActivity.finish()
                }, 500)

            }
        })
        snackbarSuccess.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onShown(transientBottomBar: Snackbar?) {
                super.onShown(transientBottomBar)
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
            }
        })
    }
}