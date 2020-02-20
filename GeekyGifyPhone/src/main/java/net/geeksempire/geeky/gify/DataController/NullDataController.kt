/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/19/20 6:19 PM
 * Last modified 2/19/20 6:11 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.DataController

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.null_data_controller.*
import net.geeksempire.geeky.gify.DataController.Extension.setupClickNullDataControllerAdsApp
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView

class NullDataController : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.null_data_controller, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SnackbarView()
            .snackBarViewSuccess((activity as AppCompatActivity?)!!, mainView, getString(R.string.nullData))

        Glide.with(context!!)
            .asGif()
            .load("https://media0.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif")
            .into(waitingView)

        setupClickNullDataControllerAdsApp()
    }
}