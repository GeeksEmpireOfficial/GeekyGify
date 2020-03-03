/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 4:54 AM
 * Last modified 3/3/20 3:58 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.UI

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SnackbarView {

    fun snackBarViewFail(context: Context, viewGroup: ViewGroup, errorMessage: String, snackbarInteraction: SnackbarInteraction) {
        val snackbarError = Snackbar.make(viewGroup, errorMessage, Snackbar.LENGTH_LONG)
        snackbarError.show()
        snackbarError.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onShown(transientBottomBar: Snackbar?) {
                super.onShown(transientBottomBar)
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                snackbarInteraction.onSnackbarRemoved()
            }
        })
    }

    fun snackBarViewSuccess(context: Context, viewGroup: ViewGroup, successMessage: String, snackbarInteraction: SnackbarInteraction) {
        val snackbarSuccess = Snackbar.make(viewGroup, successMessage, Snackbar.LENGTH_INDEFINITE)
        snackbarSuccess.show()
        snackbarSuccess.setAction(context.getString(android.R.string.ok), object : View.OnClickListener{
            override fun onClick(view: View?) {
                snackbarInteraction.onActionClick()
            }
        })
        snackbarSuccess.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onShown(transientBottomBar: Snackbar?) {
                super.onShown(transientBottomBar)
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                snackbarInteraction.onSnackbarRemoved()
            }
        })
    }
}

interface SnackbarInteraction {
    fun onActionClick() {}
    fun onSnackbarRemoved() {}
}