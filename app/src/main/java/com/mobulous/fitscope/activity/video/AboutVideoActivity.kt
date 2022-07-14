package com.mobulous.fitscope.activity.video

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import androidx.work.Data
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.SessionManagerListener
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.mobulous.BaseAc
import com.mobulous.Controllers.VideoViewController
import com.mobulous.Repo.videoDetail.VideoDetailRepo
import com.mobulous.ViewModelFactory.VideoDetailVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.Schedule.ScheduleActivity
import com.mobulous.fitscope.activity.fragment.AboutVideo.CommentFragment
import com.mobulous.fitscope.databinding.ActivityAboutVideoBinding
import com.mobulous.fragments.VideoFrgs.AboutFragment
import com.mobulous.fragments.VideoFrgs.UpNextFragment
import com.mobulous.helper.*
import com.mobulous.listner.commentItemCountLisntr
import com.mobulous.pojo.CommonChapterIDPojo
import com.mobulous.pojo.PreviouslySchedulePojo
import com.mobulous.pojo.saveChapter.*
import com.mobulous.pojo.video.VideoDataObj
import com.mobulous.pojo.video.VideoDetailVersions
import com.mobulous.room.AppDatabase
import com.mobulous.room.UserDownloads
import com.mobulous.viewModels.videodetail.VideoDetailViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import java.text.SimpleDateFormat
import java.util.*

class AboutVideoActivity : BaseAc<ActivityAboutVideoBinding>(), TabLayout.OnTabSelectedListener,
    commentItemCountLisntr {

    private val TAG = "AboutVideoActivity"
    lateinit var viewmodel: VideoDetailViewModel
    var fragment: Fragment? = null
    private var mduration = ""
    lateinit var mVideoObj: VideoDataObj
    private var knownTagIndex = 0
    private var isAlreadyDownloaded = false
    private var userName = ""
    private var userId = ""
    private var token = ""
    private var shortDesc = ""
    private var isFav = false
    private var mVideoUrl = ""
    private var isSave = false
    private var isDownload = false
    private var isComplete = false
    private var isSchedule = false
    private var enrollImageUrl = ""
    lateinit var mInterface: ApiInterface
    private var chapterID = ""
    private var isInPortrait = true
    private var isCategoryType = false
    private var upNextJsonObj = ""
    lateinit var mCastSession: CastSession
    lateinit var mCastContext: CastContext
    lateinit var mSessionManagerListener: SessionManagerListener<CastSession>
    private var mResolutionType:String?="hd"
    private var mVideoDisplay:String? =""
    private var scheduleResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data?.getBooleanExtra(
                        Constants.isPreviouslyComplete,
                        false) == true) {
                    UpdateIconState(isChecked = true, position = 3)
                }
            }
        }

    private fun intentDataCase() {
        if (intent.getStringExtra(Constants.Data) != null && chapterID.isEmpty()) {
            chapterID = intent.getStringExtra(Constants.Data) ?: "2536729"
        }
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     * @return Data which contains the Image Uri as a String
     */
    private fun createInputDataForUri(): Data {
        return Data.Builder().apply {
            putString(
                "videoUrl",
                mVideoUrl
            )
            putString("ChapterID", chapterID)
            putString(Enums.Obj.toString(), Gson().toJson(mVideoObj))
            putString(Enums.EnrollImageUrl.toString(), enrollImageUrl)
        }.build()
//        builder.putString(
//            "videoUrl",
//            mVideoUrl
//        )
//        builder.putString("ChapterID", chapterID)

    }


    private fun openScheduleScreen() {
        scheduleResultContract.launch(
            Intent(this, ScheduleActivity::class.java).putExtra(
                Constants.Case,
                Constants.Schedule
            ).putExtra(
                Constants.Data,
                Gson().toJson(
                    PreviouslySchedulePojo(
                        userName,
                        userId,
                        chapterID, chapterName = binding.tvTitleAboutVideo.text.toString(),
                        token,
                        mduration
                    )
                )
            )
        )
    }

    private fun makeFavBtnHit() {
        Uitls.showProgree(true, this)
        if (!isFav) {
            viewmodel.addChatperToFav(userId, CommonChapterIDPojo(chapterID))
        } else {
            viewmodel.removeChatperToFav(userId, CommonChapterIDPojo(chapterID))
            //removeFav(chapterID)
        }
    }

    private fun makeSaveBtnHit() {
        Uitls.showProgree(true, this)
//        runOnUiThread {
        if (!isSave) {
            viewmodel.addChatperToSave(userId, CommonChapterIDPojo(chapterID))
        } else {
            viewmodel.removeChapterFromSave(userId, CommonChapterIDPojo(chapterID))
        }
//        }
        // UpdateIconState(isChecked = isSave, position = 1)
    }

    private fun showCompletePopup() {
        val dialog = Dialog(this, R.style.CustomBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dailog_complete_lay)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val cancel = dialog.findViewById<TextView>(R.id.tvCancel_DialogCompleteItem)
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        val now = dialog.findViewById<TextView>(R.id.tvYesNow_DialogCompleteItem)
        now.setOnClickListener {
            dialog.dismiss()
            Uitls.showProgree(true, this)
            viewmodel.makeChapterAsCompleteOrSchedule(
                userName = userName,
                chapterId = chapterID,
                authorizationToken = token,
                date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()),
                timeInMint = mduration, type = Constants.Complete)
//            runOnUiThread {
//                markChapterAsComplete(true)
//            }
        }
        dialog.findViewById<TextView>(R.id.yes_previously).setOnClickListener {
            dialog.dismiss()
            scheduleResultContract.launch(
                Intent(this, ScheduleActivity::class.java).putExtra(Constants.Case, Constants.Complete).putExtra(
                    Constants.Data,
                    Gson().toJson(
                        PreviouslySchedulePojo(
                            userName,
                            userId,
                            chapterID, chapterName = binding.tvTitleAboutVideo.text.toString(),
                            token,
                            mduration
                        )
                    )
                )
            )

        }
        if (!isComplete) {
            dialog.show()
        }

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> fragment = AboutFragment(
                if (intent.getStringExtra(Constants.From) != Enums.FromOfflineDownloads.toString()) Gson().toJson(
                    mVideoObj
                ) else intent.getStringExtra(Constants.Data) ?: ""
            )
            1 -> fragment = CommentFragment(chapterID)
            2 -> fragment = UpNextFragment(
                upNextJsonObj,
                isCategoryType = isCategoryType
            )

        }

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        fragment?.let { ft.replace(R.id.fragment_AboutVideo, it) }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }


    private fun UpdateIconState(isChecked: Boolean, position: Int) {
        when (position) {
            0 -> {
                if (isChecked) {
                    binding.IconLay.ivFav.setImageResource(R.drawable.ic_red_star)
                    binding.IconLay.tvFav.text = "Favorited"
                    binding.IconLay.tvFav.setTextColor(
                        ContextCompat.getColor(
                            this@AboutVideoActivity,
                            R.color.red
                        )
                    )

                    binding.customVideoPlayer.ivFav.setImageResource(R.drawable.ic_red_star)
                    binding.customVideoPlayer.ivFav.setColorFilter(Color.RED)

                    isFav = isChecked
                } else {
                    isFav = isChecked
                    binding.IconLay.ivFav.setImageResource(R.drawable.ic_star_img)
                    binding.customVideoPlayer.ivFav.setImageResource(R.drawable.ic_star_img)
                    binding.customVideoPlayer.ivFav.setColorFilter(Color.WHITE)
                    binding.IconLay.tvFav.text = "Favorite"
                    binding.IconLay.tvFav.setTextColor(
                        ContextCompat.getColor(
                            this@AboutVideoActivity,
                            R.color.new_black
                        )
                    )
                }
            }
            1 -> {
                if (isChecked) {
                    binding.IconLay.ivSave.setImageResource(R.drawable.ic_red_group_53)
                    binding.customVideoPlayer.ivSave.setImageResource(R.drawable.ic_red_group_53)
                    binding.customVideoPlayer.ivSave.setColorFilter(Color.RED)
                    binding.IconLay.tvSave.setTextColor(ContextCompat.getColor(this, R.color.red))
                    binding.IconLay.tvSave.text = "Saved"
                    isSave = isChecked

                } else {
                    isSave = isChecked
                    binding.IconLay.ivSave.setImageResource(R.drawable.ic_group_53)
                    binding.customVideoPlayer.ivSave.setImageResource(R.drawable.ic_group_53)
                    binding.customVideoPlayer.ivSave.setColorFilter(Color.WHITE)
                    binding.IconLay.tvSave.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.new_black
                        )
                    )
                    binding.IconLay.tvSave.text = "Save"
                }
            }
            2 -> {
                if (isChecked) {
                    binding.IconLay.ivCompelete.setImageResource(R.drawable.ic_check_circle_img)
                    binding.customVideoPlayer.ivCompelete.setImageResource(R.drawable.ic_check_circle_img)
                    binding.customVideoPlayer.ivCompelete.setColorFilter(Color.RED)
                    binding.IconLay.tvCompelete.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.new_black
                        )
                    )
                    isDownload = true
                }
            }
            3 -> {
                if (isChecked) {
                    binding.IconLay.ivCompelete.setImageResource(R.drawable.ic_redcheck_circle_img)
                    binding.customVideoPlayer.ivCompelete.setImageResource(R.drawable.ic_redcheck_circle_img)
                    binding.customVideoPlayer.ivCompelete.setColorFilter(Color.RED)
                    binding.IconLay.tvCompelete.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.red
                        )
                    )
                    isComplete = isChecked
                } else {
                    isComplete = isChecked
                    binding.IconLay.ivCompelete.setImageResource(R.drawable.ic_check_circle_img)
                    binding.customVideoPlayer.ivCompelete.setImageResource(R.drawable.ic_check_circle_img)
                    binding.customVideoPlayer.ivCompelete.setColorFilter(Color.WHITE)
                    binding.IconLay.tvCompelete.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.new_black
                        )
                    )
                }
            }
        }

    }


    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        println("~~~${newConfig.orientation}~~")
        // Checks the orientation of the screen
        isInPortrait = newConfig.orientation != Configuration.ORIENTATION_LANDSCAPE
        Uitls.hideUnhideVideoOptions(!isInPortrait, binding.customVideoPlayer)
        if (resources.getBoolean(R.bool.isTablet)) {
//            binding.constraintLayout4.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (!isInPortrait) ViewGroup.LayoutParams.MATCH_PARENT else 400.dpToPix(this)
//            )

//            if (!isInPortrait) {
//                (binding.customVideoPlayer.videoView.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio =
//                    "16:9"
//            } else {
//                (binding.customVideoPlayer.videoView.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio =
//                    ""
//            }
//
//            binding.customVideoPlayer.panel.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (!isInPortrait) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
//            )

//
//            binding.customVideoPlayer.panel.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (!isInPortrait) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            binding.customVideoPlayer.videoView.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (!isInPortrait) ViewGroup.LayoutParams.MATCH_PARENT else 400.dpToPix(this)
//            )
        } else {

            binding.customVideoPlayer.videoView

//            binding.customVideoPlayer.videoView.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (!isInPortrait) ViewGroup.LayoutParams.MATCH_PARENT else if (resources.getBoolean(
//                        R.bool.isTablet
//                    )
//                ) 400.dpToPix(
//                    this
//                ) else ViewGroup.LayoutParams.MATCH_PARENT
//            )
//
//            try {
//                binding.customVideoPlayer.videoViewRootLay.updateLayoutParams<ConstraintLayout.LayoutParams> {
//                    marginStart = if (!isInPortrait) 100 else 0
//                    marginEnd = if (!isInPortrait) 100 else 0
//                }
//                Uitls.hideStatusBar(flag = isInPortrait, this)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }

//            binding.constraintLayout4.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (!isInPortrait) ViewGroup.LayoutParams.MATCH_PARENT else 240.dpToPix(this)
//            )
//            binding.customVideoPlayer.panel.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (!isInPortrait) ViewGroup.LayoutParams.MATCH_PARENT else 240.dpToPix(this)
//            )
//            binding.customVideoPlayer.videoView.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                if (!isInPortrait) ViewGroup.LayoutParams.MATCH_PARENT else 240.dpToPix(this)
//            )
        }

        binding.customVideoPlayer.enterExitFullscreenButton.setImageResource(if (!isInPortrait) R.drawable.zoom_out_ic else R.drawable.enlarge)

    }

    override fun onCommentCount(count: Int) {
        binding.tabAboutVideo.getTabAt(1)?.text = if (count > 0) "Comments(${count})" else "Comment"
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.run {
            upNextJsonObj = getStringExtra(Constants.NewChapterDataLst) ?: ""
            chapterID = getStringExtra(Constants.Data) ?: ""
            isCategoryType =
                intent.getStringExtra(Constants.Type) == Enums.CATEGORY_DATA_ITEM.toString()
            initViews()
            listnr()
            observers()
        }
    }

    override fun onResume() {
        super.onResume()

        val editor = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)
        mResolutionType = editor?.getString(Constants.RESOLUTIONTYPE,"")

        Log.d(TAG, "onResume: "+mResolutionType)
//        mCastContext.sessionManager.addSessionManagerListener(
//            mSessionManagerListener, CastSession::class.java
//        )
        // binding.IconLay.downloadPd.visibility = View.GONE
        if (upNextJsonObj.isEmpty()) {
            /*no upnext found..*/
            binding.tabAboutVideo.removeTabAt(2)
        }
        WorkManager.getInstance(this)
            .getWorkInfosByTagLiveData(binding.tvTitleAboutVideo.text.toString()).observe(this, {
                if (!it.isNullOrEmpty()) {
                    println("AllProcess---->${it[0]}")
                    if (knownTagIndex == 0) {
                        knownTagIndex = getIndex(it)
                    }
                    if (it[knownTagIndex].tags.contains(binding.tvTitleAboutVideo.text.toString()) && it[knownTagIndex].state == WorkInfo.State.RUNNING) {
                        println("RunningProcess--->${it[knownTagIndex]}")
                        binding.IconLay.constraintLayoutDownloading.isEnabled = true
                        binding.IconLay.ivDownloding.visibility = View.GONE
                        binding.IconLay.tvDownloading.visibility = View.GONE
                        binding.IconLay.downloadPd.visibility = View.VISIBLE
                        binding.IconLay.downloadPauseBtn.visibility = View.VISIBLE
                        binding.IconLay.downloadPd.progress =
                            it[knownTagIndex].progress.getInt("Progress", 0)

                    }

                    if (it[knownTagIndex].tags.contains(binding.tvTitleAboutVideo.text.toString()) && it[knownTagIndex].state == WorkInfo.State.SUCCEEDED) {
                        println("CompleteProcess--->${it[knownTagIndex]}")
                        alreadyDownloaded()
                        Uitls.showToast(this, "AlreadyDownloaded!!!!!!")
                        binding.IconLay.ivDownloding.setImageResource(R.drawable.ic_download_com)
                        try {
//                            runOnUiThread {
//                                AppDatabase.getInstance(this@AboutVideoActivity)?.userDao()?.apply {
//                                    if (!isChapterAlreadyDownload(chapterID)) {
//                                        println("=======Chapter Added====")
//                                        addDownload(
//                                            UserDownloads(
//                                                data = Gson().toJson(
//                                                    VideoDetailDataObj(
//                                                        duration = mVideoObj.duration,
//                                                        title = binding.tvTitleAboutVideo.text.toString(),
//                                                        subtitles = mVideoObj.subtitles,
//                                                        shortDescription = mVideoObj.shortDescription,
//                                                        isComplete = mVideoObj.isComplete,
//                                                        isSchedule = mVideoObj.isSchedule,
//                                                        versions = mVideoObj.versions,
//                                                        previewImageUrl = mVideoObj.previewImageUrl,
//                                                        isFav = mVideoObj.isFav,
//                                                        durationInSeconds = mVideoObj.durationInSeconds,
//                                                        isSave = mVideoObj.isSave,
//                                                        videoOfflineUrl = it[knownTagIndex].outputData.getString(
//                                                            "OfflineUrl"
//                                                        ),
//
//                                                        )
//                                                ),
//                                                chapterID = chapterID
//                                            )
//                                        )
//                                    }
//                                }
//                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
            })
    }

    private fun getIndex(workInfoLst: List<WorkInfo>): Int {
        workInfoLst.forEachIndexed { index, workInfo ->
            if (workInfo.tags.contains(binding.tvTitleAboutVideo.text.toString()) && workInfo.state == WorkInfo.State.RUNNING) {
                knownTagIndex = index
                return@forEachIndexed
            }
        }
        return knownTagIndex
    }

    private fun alreadyDownloaded() {
        binding.IconLay.ivDownloding.visibility = View.VISIBLE
        binding.IconLay.tvDownloading.visibility = View.VISIBLE
        binding.IconLay.tvDownloading.text = "Downloaded"
        isAlreadyDownloaded = true
        binding.IconLay.downloadPd.visibility = View.GONE
        binding.IconLay.downloadPauseBtn.visibility = View.GONE
    }

    override fun getViewBinding(): ActivityAboutVideoBinding =
        ActivityAboutVideoBinding.inflate(layoutInflater)

    override fun initViews() {
        PrefUtils.with(this).apply {
            userName = getString(Enums.UserName.toString(), "") ?: ""
            userId = getString(Enums.UserID.toString(), "") ?: ""
            token = getString(Enums.UserToken.toString(), "") ?: ""
        }

        /*https://stream.mux.com/oNx3007UMwcGCPx8F00HzHbwl600uV1LftX/low.mp4*/
        /*http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4*/
        Uitls.hideUnhideVideoOptions(false, binding.customVideoPlayer)
        mInterface = ServiceBuilder.mobulousBuildServiceToken(ApiInterface::class.java, this)
        binding.tabAboutVideo.addOnTabSelectedListener(this)
        binding.tabAboutVideo.getTabAt(0)?.select()
        intentDataCase()
        if (resources.getBoolean(R.bool.isTablet)) {
//            binding.customVideoPlayer.videoView.updateLayoutParams<ConstraintLayout.LayoutParams> {
//                width = ViewGroup.LayoutParams.MATCH_PARENT
//            }
//            binding.customVideoPlayer.mainImgView.updateLayoutParams<ConstraintLayout.LayoutParams> {
//                width = ViewGroup.LayoutParams.MATCH_PARENT
//                height = ViewGroup.LayoutParams.MATCH_PARENT
//            }
//            (binding.customVideoPlayer.panel.layoutParams as ConstraintLayout.LayoutParams).height =
//                700
//            (binding.customVideoPlayer.videoView.layoutParams as ConstraintLayout.LayoutParams).height =
//                700

            //  (binding.customVideoPlayer.mainImgView.layoutParams as ConstraintLayout.LayoutParams).height =
//                800


        }
        try {
            viewmodel =
                ViewModelProvider(this, VideoDetailVMFactory(VideoDetailRepo(mInterface))).get(
                    VideoDetailViewModel::class.java
                ).apply {

                }
            if (upNextJsonObj.isEmpty()) {
                intent.getStringExtra(Constants.InnerObj)?.let {
                    if (it.isNotEmpty()) {
                        upNextJsonObj = it
                    } else {
                        binding.tabAboutVideo.removeTabAt(2)
                    }
                }
            }
            if (intent.getStringExtra(Constants.Data) != null && intent.getStringExtra(Constants.From) != Enums.FromOfflineDownloads.toString()) {
                Uitls.showProgree(true, this@AboutVideoActivity)
                viewmodel.getVideoDetail(videoId = chapterID, userId)
                if (AppDatabase.getInstance(applicationContext)?.userDao()
                        ?.isChapterAlreadyDownload(chapterID) == true
                ) {
                    alreadyDownloaded()
                }

            } else if (intent.getStringExtra(Constants.From) == Enums.FromOfflineDownloads.toString()) {
                Uitls.showToast(this@AboutVideoActivity, "offline")
                binding.tabAboutVideo.removeTabAt(2)
                Gson().fromJson<VideoDataObj>(
                    intent.getStringExtra(Constants.Data),
                    VideoDataObj::class.java
                ).apply {
                    binding.tvTitleAboutVideo.text = title
                    VideoViewController(
                        this@AboutVideoActivity,
                        binding
                    ).mVideoSetup(
                        videoUri = videoOfflineUrl ?: "",
                        mduration = duration.toString(),
                        versions = null,
                        chapterTitle = title ?: ""
                    )
                    binding.customVideoPlayer.mainImgView.loadNormalPhoto_Dimens300(enrollImage)
                    binding.customVideoPlayer.mainImgView.visibility = View.VISIBLE

                    AboutFragment(intent.getStringExtra(Constants.Data) ?: "").let {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_AboutVideo, it).apply {
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                commit()
                            }
                    }

                }

            }
//            if (AppDatabase.getInstance(applicationContext)?.userDao()
//                    ?.isChapterAlreadyDownload(chapterID) == true && intent.getStringExtra(Constants.From) != Enums.FromOfflineDownloads.toString()
//            ) {
//
//            }
            /*https://github.com/mradzinski/Caster/blob/master/example/src/main/java/com/mradzinski/casterexample/MainActivity.java*/
            setupCastListener()
            mCastContext = CastContext.getSharedInstance(this)
            mCastSession = mCastContext.sessionManager.currentCastSession
            CastButtonFactory.setUpMediaRouteButton(
                this,
                binding.customVideoPlayer.mediaRouteButton
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
        isCategoryType =
            intent.getStringExtra(Constants.Type) == Enums.CATEGORY_DATA_ITEM.toString()
    }

    override fun observers() {
        viewmodel.makeVideoAsComOrScheduleData.observe(this, {
            Uitls.showProgree(false, this)
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataobj ->
                        if (dataobj.status == 200) {
                            UpdateIconState(isChecked = true, position = 3)
                        }
                        Uitls.showToast(this@AboutVideoActivity, dataobj.message ?: "")
                    }
                }
                is NetworkReponse.Error -> {
                    Uitls.showToast(this@AboutVideoActivity, it.errorMessage)
                }
            }
        })

        viewmodel.removeChapterFromSave.observe(this, {
            Uitls.showProgree(false, this)
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        if (it.data?.status == 200) {
                            UpdateIconState(isChecked = false, position = 1)
                        }
                        showToast(it.data?.message.toString())
                    }
                    is NetworkReponse.Error -> {
                        showToast(it.errorMessage)
                    }
                }
            } ?: showToast(getString(R.string.no_able_to_process_api))
        })
        viewmodel.addChapterToSave.observe(this, {
            Uitls.showProgree(false, this)
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        if (it.data?.status == 200) {
                            UpdateIconState(isChecked = true, position = 1)
                        }
                        showToast(it.data?.message.toString())
                    }
                    is NetworkReponse.Error -> {
                        showToast(it.errorMessage)
                    }
                }
            } ?: showToast(getString(R.string.no_able_to_process_api))
        })

        viewmodel.addChapterToFavData.observe(this, {
            Uitls.showProgree(false, this)
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        if (it.data?.status == 200) {
                            UpdateIconState(isChecked = true, position = 0)
                        }
                        showToast(it.data?.message.toString())
                    }
                    is NetworkReponse.Error -> {
                        showToast(it.errorMessage)
                    }

                }
            } ?: showToast(getString(R.string.no_able_to_process_api))
        })
        viewmodel.removeChapterToFavData.observe(this) {
            Uitls.showProgree(false, this)
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        if (it.data?.status == 200) {
                            UpdateIconState(isChecked = false, position = 0)
                        }
                        showToast(it.data?.message.toString())
                    }
                    is NetworkReponse.Error -> {
                        showToast(it.errorMessage)
                    }
                }
            } ?: showToast(getString(R.string.no_able_to_process_api))
        }

        viewmodel.videoDetailData.observe(this, {
            Uitls.showProgree(false, this)
            it?.let {
                if (it.status == 200) {
                    it.data?.let { dataObj ->
                        try {
                            binding.customVideoPlayer.mainImgView.loadNormalPhoto_Dimens600(
                                dataObj.previewImage ?: ""
                            )
                            enrollImageUrl = dataObj.previewImage ?: ""
                            binding.customVideoPlayer.mainImgView.visibility = View.VISIBLE
                            binding.tvTitleAboutVideo.text = dataObj.title ?: "nil"
                            mVideoObj = dataObj

                            dataObj.videoDetails?.versions?.let {
                                mduration = dataObj.videoDetails.durationInSeconds.toString()
                              //  setQuality(it)

                                VideoViewController(this@AboutVideoActivity, binding).mVideoSetup(
                                   /* videoUri = mVideoDisplay ?: ""*/
                                    videoUri = it.hd ?: "",
                                    mduration = dataObj.videoDetails?.durationInSeconds.toString(),
                                    versions = it,
                                    chapterTitle = binding.tvTitleAboutVideo.text.toString()
                                )
//                                mVideoUrl = mVideoDisplay ?: ""
                                mVideoUrl = it.hd ?: ""
                                shortDesc = dataObj.programs?.description ?: ""
                                UpdateIconState(dataObj.isFav ?: false, 0)
                                UpdateIconState(dataObj.isSave ?: false, 1)
                                UpdateIconState(dataObj.isComplete ?: false, 3)
                                AboutFragment(Gson().toJson(mVideoObj)).let {
                                    supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_AboutVideo, it).apply {
                                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                            commit()
                                        }
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                } else {
                    showToast(it.message.toString())
                }
            } ?: run {
                Uitls.onUnSuccessResponse(400, this@AboutVideoActivity)
            }
        })
    }

    private fun setQuality(it: VideoDetailVersions) {
        if(mResolutionType.equals("hls")){
            mVideoDisplay = it.hls
        }else if(mResolutionType.equals("hd")){
            mVideoDisplay = it.hd
        }else if(mResolutionType.equals("md")){
            mVideoDisplay = it.md
        }else if(mResolutionType.equals("sd")){
            mVideoDisplay = it.sd
        }else{
            mVideoDisplay = it.hd
        }

        Log.d(TAG, "setQuality: .."+mVideoDisplay)
    }

    override fun listnr() {
        requestedOrientation =
            if (resources.getBoolean(R.bool.isTablet)) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding.IconLay.constraintLayoutDownloading.setOnClickListener {
            if (binding.IconLay.ivDownloding.visibility == View.VISIBLE) {
                if (AppDatabase.getInstance(applicationContext)?.userDao()
                        ?.isChapterAlreadyDownload(chapterID) == false
                ) {
                    val workManager = WorkManager.getInstance(this)
                    val mReq = OneTimeWorkRequestBuilder<VideoDownloadWorker>()
                        .setInputData(createInputDataForUri())
                        .addTag(binding.tvTitleAboutVideo.text.toString())
                        .setConstraints(Constraints.Builder().apply {
                            setRequiredNetworkType(NetworkType.CONNECTED)
                        }.build())
                        .build()
                    workManager.enqueue(mReq)
                    println("~~~~${binding.tvTitleAboutVideo.text.toString()}")

                    workManager.getWorkInfosByTagLiveData(binding.tvTitleAboutVideo.text.toString())
                        .observe(this, {
                            if (!it.isNullOrEmpty()) {
                                println("AllProcess---->${it[0]}")
                                if (knownTagIndex == 0) {
                                    knownTagIndex = getIndex(it)
                                }
                                if (it[knownTagIndex].tags.contains(binding.tvTitleAboutVideo.text.toString()) && it[knownTagIndex].state == WorkInfo.State.RUNNING) {
                                    println("RunningProcess--->${it[knownTagIndex]}")
                                    binding.IconLay.constraintLayoutDownloading.isEnabled = true
                                    binding.IconLay.ivDownloding.visibility = View.GONE
                                    binding.IconLay.tvDownloading.visibility = View.GONE
                                    binding.IconLay.downloadPd.visibility = View.VISIBLE
                                    binding.IconLay.downloadPauseBtn.visibility = View.VISIBLE
                                    binding.IconLay.downloadPd.progress =
                                        it[knownTagIndex].progress.getInt("Progress", 0)

                                }

                                if (it[knownTagIndex].tags.contains(binding.tvTitleAboutVideo.text.toString()) && it[knownTagIndex].state == WorkInfo.State.SUCCEEDED) {
                                    println("CompleteProcess--->${it[knownTagIndex]}")
                                    alreadyDownloaded()
                                    Uitls.showToast(this, "AlreadyDownloaded!!!!!!")
                                    binding.IconLay.ivDownloding.setImageResource(R.drawable.ic_download_com)
                                    try {
                                        runOnUiThread {
                                            AppDatabase.getInstance(this@AboutVideoActivity)
                                                ?.userDao()?.apply {
                                                    if (!isChapterAlreadyDownload(chapterID)) {
                                                        println("=======Chapter Added====")
                                                        Gson().fromJson<VideoDataObj>(
                                                            it[knownTagIndex].outputData.getString(
                                                                Enums.Obj.toString()
                                                            ), VideoDataObj::class.java
                                                        ).apply {
                                                            addDownload(
                                                                UserDownloads(
                                                                    data = Gson().toJson(
                                                                        VideoDataObj(
                                                                            trainer = trainer,
                                                                            difficulty = difficulty,
                                                                            goal = goal,
                                                                            music = music,
                                                                            enrollImage = enrollImageUrl,
                                                                            duration = mVideoObj.duration,
                                                                            title = binding.tvTitleAboutVideo.text.toString(),
                                                                            shortDescription = longDescription
                                                                                ?: shortDescription,
                                                                            isComplete = mVideoObj.isComplete,
                                                                            isFav = mVideoObj.isFav,
                                                                            isSave = mVideoObj.isSave,
                                                                            videoOfflineUrl = it[knownTagIndex].outputData.getString(
                                                                                "OfflineUrl"
                                                                            ),


                                                                            )
                                                                    ),
                                                                    chapterID = chapterID
                                                                )
                                                            )
                                                        }

                                                    }
                                                }
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }

                                }
                            }
                        })


                } else {
                    if (AppDatabase.getInstance(applicationContext)?.userDao()
                            ?.removeChapter(chapterID) == 1
                    ) {
                        /*able to remove*/
                        isAlreadyDownloaded = false
                        binding.IconLay.tvDownloading.text = "Download"
                        binding.IconLay.ivDownloding.setImageResource(R.drawable.download_img)
                    } else {
                        /*no able to remove*/
                        Uitls.showToast(applicationContext, "Not able to process your request")
                    }
                }

            } else {
                println("--------worker_cancelled------")
                WorkManager.getInstance(this)
                    .cancelAllWorkByTag(binding.tvTitleAboutVideo.text.toString())
                binding.IconLay.ivDownloding.visibility = View.VISIBLE
                binding.IconLay.downloadPd.visibility = View.GONE
                binding.IconLay.tvDownloading.visibility = View.VISIBLE
                binding.IconLay.downloadPauseBtn.visibility = View.GONE
                println("~~OnWorkerCancelByTag::${binding.tvTitleAboutVideo.text.toString()}")
            }

        }

        binding.IconLay.constraintLayoutComplete.setOnClickListener {
            showCompletePopup()
        }
        binding.customVideoPlayer.ivCompelete.setOnClickListener {
            showCompletePopup()
        }
        binding.icBack.setOnClickListener {
            onBackPressed()
        }
        binding.tabAboutVideo.addOnTabSelectedListener(this)

        binding.IconLay.constraintLayoutSave.setOnClickListener {
            makeSaveBtnHit()
        }

        binding.customVideoPlayer.ivSave.setOnClickListener {
            makeSaveBtnHit()
        }

        binding.IconLay.constraintLayoutSchedule.setOnClickListener {
            openScheduleScreen()
        }
        binding.customVideoPlayer.ivSchedule.setOnClickListener {
            openScheduleScreen()
        }

        binding.IconLay.constraintLayoutFavorite.setOnClickListener {
            makeFavBtnHit()
        }
        binding.customVideoPlayer.ivFav.setOnClickListener {
            makeFavBtnHit()
        }

    }

    private fun setupCastListener() {
        mSessionManagerListener = object : SessionManagerListener<CastSession> {
            override fun onSessionEnded(session: CastSession, error: Int) {
                onApplicationDisconnected()
            }

            override fun onSessionResumed(session: CastSession, wasSuspended: Boolean) {
                onApplicationConnected(session)
            }

            override fun onSessionResumeFailed(session: CastSession, error: Int) {
                onApplicationDisconnected()
            }

            override fun onSessionStarted(session: CastSession, sessionId: String) {
                onApplicationConnected(session)
            }

            override fun onSessionStartFailed(session: CastSession, error: Int) {
                onApplicationDisconnected()
            }

            override fun onSessionStarting(session: CastSession) {}
            override fun onSessionEnding(session: CastSession) {}
            override fun onSessionResuming(session: CastSession, sessionId: String) {}
            override fun onSessionSuspended(session: CastSession, reason: Int) {}
            private fun onApplicationConnected(castSession: CastSession) {
                mCastSession = castSession
//                if (null != mSelectedMedia) {
//                    if (mPlaybackState == PlaybackState.PLAYING) {
//                        mVideoView.pause()
//                        loadRemoteMedia(mSeekbar.getProgress(), true)
//                        return
//                    } else {
//                        mPlaybackState = PlaybackState.IDLE
//                        updatePlaybackLocation(com.google.sample.cast.refplayer.mediaplayer.LocalPlayerActivity.PlaybackLocation.REMOTE)
//                    }
//                }
//                updatePlayButton(mPlaybackState)
//                invalidateOptionsMenu()
            }

            private fun onApplicationDisconnected() {
//                updatePlaybackLocation(com.google.sample.cast.refplayer.mediaplayer.LocalPlayerActivity.PlaybackLocation.LOCAL)
//                mPlaybackState = PlaybackState.IDLE
//                mLocation =
//                    com.google.sample.cast.refplayer.mediaplayer.LocalPlayerActivity.PlaybackLocation.LOCAL
//                updatePlayButton(mPlaybackState)
//                invalidateOptionsMenu()
            }
        }
    }

    private fun loadRemoteMedia(position: Int, autoPlay: Boolean) {
        if (mCastSession == null) {
            return
        }
        val remoteMediaClient: RemoteMediaClient = mCastSession.remoteMediaClient ?: return
        remoteMediaClient.registerCallback(object : RemoteMediaClient.Callback() {
            override fun onStatusUpdated() {
//                val intent = Intent(this@LocalPlayerActivity, ExpandedControlsActivity::class.java)
//                startActivity(intent)
//                remoteMediaClient.unregisterCallback(this)
            }
        })
        remoteMediaClient.load(
            MediaLoadRequestData.Builder()
                .setMediaInfo(buildMediaInfo())
                .setAutoplay(autoPlay)
                .setCredentials("user-credentials")
                .setAtvCredentials("atv-user-credentials")
                .setCurrentTime(position.toLong())
                .build()
        )
    }

    private fun buildMediaInfo(): MediaInfo? {
        val movieMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
        movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, " mSelectedMedia.getStudio()")
        movieMetadata.putString(MediaMetadata.KEY_TITLE, "mSelectedMedia.getTitle()")
//        movieMetadata.addImage(WebImage(Uri.parse(mSelectedMedia.getImage(0))))
//        movieMetadata.addImage(WebImage(Uri.parse(mSelectedMedia.getImage(1))))
        return MediaInfo.Builder("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4")
            .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
            .setContentType("videos/mp4")
            .setMetadata(movieMetadata)
            /*.setStreamDuration(mSelectedMedia.getDuration() * 1000)*/
            .build()

    }


}



