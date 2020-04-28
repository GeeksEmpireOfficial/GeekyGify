/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 4/28/20 4:28 AM
 * Last modified 4/28/20 4:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifViewer

import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.GifViewer.Extension.setupGifViewClickListener
import net.geeksempire.geeky.gify.GifViewer.Extension.setupUserProfileInformation
import net.geeksempire.geeky.gify.GifViewer.Utils.GifViewerFragmentStateListener
import net.geeksempire.geeky.gify.GifViewer.Utils.ReloadData
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.SharedDataController.DataController
import net.geeksempire.geeky.gify.databinding.GifViewBinding

class GifViewer : Fragment() {

    var gifViewerFragmentStateListener: GifViewerFragmentStateListener? = null

    lateinit var gifViewBinding: GifViewBinding

    lateinit var linkToGif: String
    lateinit var gifPreviewLink: String
    lateinit var gifLinkToDownload: String

    var gifUserName: String? = null
    var gifUserAvatarUrl: String? = null
    var gifUserIsVerified: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linkToGif = arguments?.getString(GiphyJsonDataStructure.DATA_URL) ?: "https://media.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif"
        gifPreviewLink = arguments?.getString(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF) ?: "https://media.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif"
        gifLinkToDownload = arguments?.getString(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL) ?: "https://media.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vmBuilder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(vmBuilder.build())
    }

    override fun onCreateView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        gifViewBinding = GifViewBinding.inflate(layoutInflater, container, false)

        Glide.with(requireContext())
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(R.drawable.gradient_loading)
            .into(gifViewBinding.progressBarGifView)

        return gifViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(gifLinkToDownload)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(glideException: GlideException?, any: Any?, target: com.bumptech.glide.request.target.Target<GifDrawable>?, boolean: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(drawable: GifDrawable?, any: Any?, target: com.bumptech.glide.request.target.Target<GifDrawable>?, dataSource: DataSource?, boolean: Boolean): Boolean {
                    Handler().postDelayed({
                        gifViewBinding.progressBarGifView.visibility = View.GONE

                        setupGifViewClickListener()
                    }, 500)

                    return false
                }
            })
            .into(gifViewBinding.gifView)

        gifViewBinding.closeFragment.setOnClickListener {
            gifViewerFragmentStateListener?.onFragmentDetach(ReloadData.DataType_Collection)

            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(0, R.anim.slide_to_right)
                .remove(this@GifViewer)
                .commit()
        }

        gifUserName = arguments?.getString(GiphyJsonDataStructure.DATA_USER_NAME)
        gifUserAvatarUrl = arguments?.getString(GiphyJsonDataStructure.DATA_USER_AVATAR_URL)
        gifUserIsVerified = arguments?.getBoolean(GiphyJsonDataStructure.DATA_USER_IS_VERIFIED)

        setupUserProfileInformation()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        gifViewerFragmentStateListener?.onFragmentDetach(ReloadData.DataType_Collection)

        (requireActivity() as DataController).dataControllerBinding.fragmentPlaceHolderGifViewer.visibility = View.GONE
    }
}