package com.mobulous.Controllers

import android.app.Activity
import android.app.DownloadManager
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.work.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.ActivityAboutVideoBinding
import com.mobulous.fitscope.databinding.VideoPlayerQualityLayBinding
import com.mobulous.helper.Uitls
import com.mobulous.pojo.video.VideoDetailVersions
import java.io.*


class VideoViewController(
    val context: Activity,
    val bin: ActivityAboutVideoBinding
) {

    private var preQuality = 0
    private var isFullScreen = false
    private var preVideoDuration = 0
    private var isPotraitMode = true
    var manager: DownloadManager? = null
    private var mVideoUrl: String? = null
    private var mduration: String? = null
    private var chapterTitle: String? = null
    private var mVersions: VideoDetailVersions? = null

    /*fun mVideoSetup(videoUri: String, mduration: String, versions: Versions, chapterTitle: String)*/
    fun mVideoSetup(
        videoUri: String,
        mduration: String,
        versions: VideoDetailVersions? = null,
        chapterTitle: String
    ) {
        bin.customVideoPlayer.playPauseButton.visibility = View.INVISIBLE
        if (videoUri.contains(context.filesDir.path)) {
            bin.customVideoPlayer.videoView.setVideoPath(videoUri)
        } else {
            println("-----videoUri---${videoUri}")
            bin.customVideoPlayer.videoView.setVideoURI(Uri.parse(videoUri))

        }
        this.mduration = mduration
        this.mVersions = versions
        mVideoUrl = videoUri
        this.chapterTitle = chapterTitle
        videoBtnListnrs()

    }

    private fun videoBtnListnrs() {

        bin.customVideoPlayer.videoView.setOnPreparedListener {
            //  DownloadVideoAsyncTask(context).execute("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
            println("onVideoStart......")
            bin.customVideoPlayer.panel.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.video_black_bg
                )
            )
            // bin.customVideoPlayer.mainImgView.visibility = View.INVISIBLE
            bin.customVideoPlayer.rangeSlider.max = it.duration
            bin.customVideoPlayer.playPauseButton.visibility = View.VISIBLE
            bin.customVideoPlayer.progressbar.visibility = View.INVISIBLE
            bin.customVideoPlayer.videoDuration.text =
                Uitls.getTimeStampHMS(mduration?.toInt() ?: 0)
            //  hideController()
//            println("===canForward=>${bin.mVideoPlayer.videoView.canSeekBackward()}")
//            bin.mVideoPlayer.videoView.seekTo(100000)
            bin.customVideoPlayer.playPauseButton.setBackgroundResource(R.drawable.ic_play)
            // bin.customVideoPlayer.videoView.start()


            context.runOnUiThread {
                setHandler()
            }
            initalizeSeekBars()
        }


        bin.customVideoPlayer.enterExitFullscreenButton.setOnClickListener {
            println("=====orientation=========>${context.requestedOrientation}")


//            bin.constraintLayout4.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (isPotraitMode) ViewGroup.LayoutParams.MATCH_PARENT else if (context.resources.getBoolean(
//                        R.bool.isTablet
//                    )
//                ) 400.dpToPix(
//                    context
//                ) else 240.dpToPix(context)
//            )


//            bin.customVideoPlayer.panel.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (isPotraitMode) ViewGroup.LayoutParams.MATCH_PARENT else if (context.resources.getBoolean(
//                        R.bool.isTablet
//                    )
//                ) 400.dpToPix(
//                    context
//                ) else 240.dpToPix(context)
//            )


            if (context.resources.getBoolean(
                    R.bool.isTablet
                )
            ) {
                println("``````${isPotraitMode}")
                if (!isPotraitMode) {
                    (bin.customVideoPlayer.videoView.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio =
                        "16:9"
                } else {
                    (bin.customVideoPlayer.videoView.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio =
                        ""
                }
                bin.customVideoPlayer.panel.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    if (!isPotraitMode) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.MATCH_PARENT
                )

                bin.customVideoPlayer.videoView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    if (!isPotraitMode) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.MATCH_PARENT
                )

                bin.videoPlyerLy.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    if (isPotraitMode) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
                )


                bin.tvTitleAboutVideo.visibility = if (isPotraitMode) View.GONE else View.VISIBLE
                bin.IconLay.root.visibility = if (isPotraitMode) View.GONE else View.VISIBLE
                bin.divider9.visibility = if (isPotraitMode) View.GONE else View.VISIBLE
                bin.tabAboutVideo.visibility = if (isPotraitMode) View.GONE else View.VISIBLE
                bin.fragmentAboutVideo.visibility = if (isPotraitMode) View.GONE else View.VISIBLE


            } else {
                context.requestedOrientation =
                    if (isPotraitMode) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }


//            bin.customVideoPlayer.videoView.updateLayoutParams<ConstraintLayout.LayoutParams> {
//                marginStart = 200
//            }

            if (!isPotraitMode) {
                //context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
            bin.customVideoPlayer.enterExitFullscreenButton.setImageResource(if (isPotraitMode) R.drawable.zoom_out_ic else R.drawable.enlarge)
            isPotraitMode = !isPotraitMode
            try {
                Uitls.hideStatusBar(flag = isPotraitMode, context)
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }


        bin.customVideoPlayer.videoView.setOnInfoListener { mediaPlayer, i, i2 ->
            when (i) {
                MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                    bin.customVideoPlayer.progressbar.visibility = View.INVISIBLE
                }
                MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                    bin.customVideoPlayer.progressbar.visibility = View.VISIBLE
                }
                MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                    bin.customVideoPlayer.progressbar.visibility = View.INVISIBLE
                }
            }
            false

        }


        bin.customVideoPlayer.playPauseButton.setOnClickListener {
            changeVideoBtnState(isPlaying = bin.customVideoPlayer.videoView.isPlaying)
        }
        bin.customVideoPlayer.videoView.setOnErrorListener { mediaPlayer, i, i2 ->
            println("Some error occur")
            false
        }
        bin.customVideoPlayer.videoView.setOnCompletionListener {
            changeVideoBtnState(isPlaying = bin.customVideoPlayer.videoView.isPlaying)

        }

        bin.customVideoPlayer.videoView.setOnTouchListener { view, motionEvent ->
            hideController()
            false
        }


        bin.customVideoPlayer.mVideoQuality.setOnClickListener {
            BottomSheetDialog(
                context,
                R.style.CustomBottomSheetDialogTheme
            ).apply {
                val mView =
                    VideoPlayerQualityLayBinding.inflate(LayoutInflater.from(context))
                setContentView(mView.root)
                updateQualityLbl(preQuality, mView)
                if (preQuality != 0) {
                    mView.hd1080Optn.setOnClickListener {
                        updateQualityLbl(0, mView)
                        /*change video quality*/
                        changeVideoQuality(mduration, mVersions?.hd)
                        dismiss()
                    }

                }
                mView.hd1080Optn.setOnClickListener {
                    updateQualityLbl(0, mView)
                    /*change video quality*/
                    changeVideoQuality(mduration, mVersions?.hd)
                    dismiss()
                }
                mView.sd360Optn.setOnClickListener {
                    updateQualityLbl(1, mView)
                    /*change video quality*/
                    changeVideoQuality(mduration, mVersions?.sd)
                    dismiss()
                }
                mView.mdOptn.setOnClickListener {
                    updateQualityLbl(2, mView)
                    /*change video quality*/
                    changeVideoQuality(mduration, mVersions?.md)
                    dismiss()
                }
                mView.hlsOptn.setOnClickListener {
                    updateQualityLbl(3, mView)
                    /*change video quality*/
                    changeVideoQuality(mduration, mVersions?.hls)
                    dismiss()
                }
                show()

            }

        }
    }

    private fun changeVideoQuality(mduration: String?, url: String?) {
        bin.customVideoPlayer.playPauseButton.visibility = View.INVISIBLE
        bin.customVideoPlayer.videoView.setVideoURI(Uri.parse(url ?: ""))
        bin.customVideoPlayer.videoView.setOnPreparedListener {
            println("onQualityChangeVideoStart......")
            bin.customVideoPlayer.rangeSlider.max = it.duration
            bin.customVideoPlayer.panel.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.video_black_bg
                )
            )
            bin.customVideoPlayer.playPauseButton.visibility = View.VISIBLE
            bin.customVideoPlayer.progressbar.visibility = View.INVISIBLE
            bin.customVideoPlayer.videoDuration.text =
                Uitls.getTimeStampHMS(mduration?.toInt() ?: 0)
            //  hideController()
//            println("===canForward=>${bin.mVideoPlayer.videoView.canSeekBackward()}")
            if (bin.customVideoPlayer.videoView.canSeekForward()) {
                bin.customVideoPlayer.videoView.seekTo(preVideoDuration)
                bin.customVideoPlayer.rangeSlider.progress = preVideoDuration
            } else {
                println("----->cannotSeekForward")
            }
            bin.customVideoPlayer.videoView.start()
            this@VideoViewController.context.runOnUiThread {
                setHandler()
            }
            initalizeSeekBars()
        }
    }

    private fun updateQualityLbl(qualityIndex: Int, view: VideoPlayerQualityLayBinding) {
        when (qualityIndex) {
            0 -> {
                showTickToQualityLevel(view.hd1080Optn.id, view.root)
                preQuality = 0
            }
            1 -> {
                showTickToQualityLevel(view.sd360Optn.id, view.root)
                preQuality = 1
            }
            2 -> {
                showTickToQualityLevel(view.mdOptn.id, view.root)
                preQuality = 2
            }
            3 -> {
                showTickToQualityLevel(view.hlsOptn.id, view.root)
                preQuality = 3
            }
        }

    }

    private fun showTickToQualityLevel(viewID: Int, rootView: ConstraintLayout) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(rootView)
        constraintSet.connect(
            R.id.tick,
            ConstraintSet.TOP,
            viewID,
            ConstraintSet.TOP,
            0
        )
        constraintSet.connect(
            R.id.tick,
            ConstraintSet.BOTTOM,
            viewID,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.applyTo(rootView)
    }

    private fun setHandler() {
        val handler = Handler(Looper.getMainLooper())
        val runnable: Runnable = object : Runnable {
            override fun run() {
                if (bin.customVideoPlayer.videoView.duration > 0) {
                    bin.customVideoPlayer.videoView.currentPosition.apply {
                        bin.customVideoPlayer.videoCurrentTime.text = Uitls.getVideoDuration(
                            this.toLong()
                        )
                        bin.customVideoPlayer.rangeSlider.progress = this
                        preVideoDuration = this
                    }
                }
                handler.postDelayed(this, 0)
            }
        }
        handler.postDelayed(runnable, 1000)
    }

    private fun changeVideoBtnState(isPlaying: Boolean) {
        if (bin.customVideoPlayer.mainImgView.visibility == View.VISIBLE) {
            bin.customVideoPlayer.mainImgView.visibility = View.INVISIBLE
        }
        bin.customVideoPlayer.playPauseButton.setBackgroundResource(if (isPlaying) R.drawable.ic_play else R.drawable.ic_pause)
        if (isPlaying) {
            bin.customVideoPlayer.videoView.pause()
        } else {
            bin.customVideoPlayer.videoView.start()
        }
    }


    private fun hideController() {
        object : CountDownTimer(3000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                bin.customVideoPlayer.panel.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.video_black_bg
                    )
                )
                bin.customVideoPlayer.playPauseButton.visibility = View.VISIBLE
                bin.customVideoPlayer.videoDuration.visibility = View.VISIBLE
                bin.customVideoPlayer.videoCurrentTime.visibility = View.VISIBLE
                bin.customVideoPlayer.enterExitFullscreenButton.visibility = View.VISIBLE
                bin.icBack.visibility = View.VISIBLE
                bin.customVideoPlayer.rangeSlider.visibility = View.VISIBLE
            }

            override fun onFinish() {
                bin.customVideoPlayer.panel.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.transparent
                    )
                )
                bin.customVideoPlayer.videoDuration.visibility = View.INVISIBLE
                bin.customVideoPlayer.videoCurrentTime.visibility = View.INVISIBLE
                bin.customVideoPlayer.enterExitFullscreenButton.visibility = View.INVISIBLE
                bin.icBack.visibility = View.INVISIBLE
                bin.customVideoPlayer.playPauseButton.visibility = View.INVISIBLE
                bin.customVideoPlayer.rangeSlider.visibility = View.INVISIBLE
            }
        }.start()
    }


    private fun initalizeSeekBars() {
        bin.customVideoPlayer.rangeSlider.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    bin.customVideoPlayer.videoView.seekTo(progress)
                    bin.customVideoPlayer.videoView.start()
                    preVideoDuration = bin.customVideoPlayer.videoView.currentPosition
                    // endTime.setText("" + convertIntoTime(videoView.getDuration() - currentPosition))
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }


}