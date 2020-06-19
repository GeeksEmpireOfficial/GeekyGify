/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 6/19/20 3:00 PM
 * Last modified 6/19/20 2:58 PM
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

    fun snackBarViewSuccess(context: Context, viewGroup: ViewGroup,
                            actionText: String,
                            successMessage: String, snackbarInteraction: SnackbarInteraction) {

        val snackbarSuccess = Snackbar.make(viewGroup, successMessage, Snackbar.LENGTH_INDEFINITE)
        snackbarSuccess.show()
        snackbarSuccess.setAction(actionText, object : View.OnClickListener{
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