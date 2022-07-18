package com.mobulous.fitscope.activity.Schedule

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.mobulous.Controllers.CustomCalenderViewController
import com.mobulous.Repo.schedule.ScheduleRepo
import com.mobulous.ViewModelFactory.ScheduleVMFactory
import com.mobulous.fitscope.databinding.ActivityScheduleBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Enums
import com.mobulous.helper.NetworkReponse
import com.mobulous.helper.Uitls
import com.mobulous.listner.ScheduleDatesLisntr
import com.mobulous.listner.ScheduleSaveListnr
import com.mobulous.pojo.PreviouslySchedulePojo
import com.mobulous.viewModels.schedule.ScheduleViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import java.text.SimpleDateFormat
import java.util.*

class ScheduleActivity : AppCompatActivity(), ScheduleSaveListnr {
    lateinit var bin: ActivityScheduleBinding
    var selectedDate = ""
    lateinit var broadcastReceiver: BroadcastReceiver
    lateinit var customCalenderViewController: CustomCalenderViewController
    lateinit var scheduleDatesLisntr: ScheduleDatesLisntr
    lateinit var viewmodel: ScheduleViewModel
    lateinit var chapterObj: PreviouslySchedulePojo

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(bin.root)
        initView()
        listrn()
        observer()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observer() {
        viewmodel.rescheduleData.observe(this, {
            Uitls.showProgree(false, this)
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataObj ->
                        if (dataObj.status == 200) {
                            setResult(
                                Activity.RESULT_OK,
                                Intent().putExtra(Constants.isPreviouslyComplete, true)
                            )
                            finish()
                        }

                    }
                }

                is NetworkReponse.Error -> {
                    Uitls.showToast(this, it.errorMessage)
                }
            }
        })

        viewmodel.makeVideoAsComOrScheduleData.observe(this, {
            Uitls.showProgree(false, this)
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataobj ->
                        if (dataobj.status == 200) {
                            setResult(
                                Activity.RESULT_OK,
                                Intent().putExtra(Constants.isPreviouslyComplete, true)
                            )
                            finish()
                        }

                    }

                }

                is NetworkReponse.Error -> {
                    Uitls.showToast(this, it.errorMessage)
                }
            }

        })

        viewmodel.getScheduleLst.observe(this, {
            Uitls.showProgree(false, this)
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataObj ->
                        if (dataObj.status == 200) {
                            with(customCalenderViewController) {
                                scheduleDatesBindings(dataObj.data ?: arrayListOf())
                            }

                        } else {
                          //  Uitls.showToast(this, dataObj.message ?: "")
                        }
                    }
                }
                is NetworkReponse.Error -> {
                    Uitls.showToast(this, it.errorMessage)
                }

            }


        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initView() {
        bin.customCalenderLay.PreviousMonthImage.visibility = View.INVISIBLE
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                intent?.let {
                    selectedDate = it.getStringExtra(Enums.SelectedDate.toString()) ?: ""
                }
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(Constants.explicitBroadCastAction))
        viewmodel = ViewModelProvider(
            this,
            ScheduleVMFactory(
                ScheduleRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        this
                    )
                )
            )
        ).get(ScheduleViewModel::class.java)
        if (intent != null && intent.getStringExtra(Constants.Case) == Constants.Complete) {
            try {
                chapterObj = Gson().fromJson(
                    intent.getStringExtra(Constants.Data),
                    PreviouslySchedulePojo::class.java
                ).apply {
                    bin.textView33.text = "When would you like to schedule\n$chapterName"
                    bin.textView8.text = "Past Workout"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (intent != null && intent.getStringExtra(Constants.Case) == Constants.Schedule) {
            try {
                chapterObj = Gson().fromJson(
                    intent.getStringExtra(Constants.Data),
                    PreviouslySchedulePojo::class.java
                ).apply {
                    bin.textView33.text = "When would you like to schedule\n$chapterName"
                    bin.textView8.text = Constants.Schedule
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        selectedDate = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Date()).toString()
        customCalenderViewController =
            CustomCalenderViewController(this, bin.customCalenderLay).apply {
                calenderViewBindinds(selectedDate)
            }



        Uitls.showProgree(true, this)
        viewmodel.getScheduleLst()

    }

    var time: String = ""

    fun listrn() {
        bin.ivCrossSchedule.setOnClickListener {
            onBackPressed()
        }

        bin.tvSaveSchedule.setOnClickListener {
            if (intent != null) {
                when (intent.getStringExtra(Constants.Case)) {
                    Constants.Schedule -> {
                        try {
                            Gson().fromJson(
                                intent.getStringExtra(Constants.Data),
                                PreviouslySchedulePojo::class.java
                            ).apply {
                                Uitls.showProgree(true, this@ScheduleActivity)
                                viewmodel.makeChapterAsCompleteOrSchedule(
                                    userName = userName,
                                    chapterId = chapterID,
                                    authorizationToken = token,
                                    date = selectedDate,
                                    time = bin.textView40.selectedItem.toString().plus(":")
                                        .plus(bin.textView41.selectedItem.toString()).plus(":")
                                        .plus(
                                            bin.textView42.selectedItem.toString()
                                        ),
                                    timeInMint = chapterDuration, type = Constants.Schedule
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                    Constants.ReSchedule -> {
                        try {
                            Gson().fromJson(
                                intent.getStringExtra(Constants.Data),
                                PreviouslySchedulePojo::class.java
                            ).apply {
                                Uitls.showProgree(true, this@ScheduleActivity)
                                viewmodel.rescheduleChapter(
                                    hashMapOf(
                                        Pair("chapterId", chapterID),
                                        Pair("date", selectedDate),
                                        Pair(
                                            "time", bin.textView40.selectedItem.toString().plus(":")
                                                .plus(bin.textView41.selectedItem.toString())
                                                .plus(":")
                                                .plus(
                                                    bin.textView42.selectedItem.toString()
                                                )
                                        ),
                                        Pair("timeInMint", chapterDuration),
                                        Pair(
                                            "scheduleId",
                                            intent.getStringExtra(Enums.ID.toString()) ?: ""
                                        ),
                                    )
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    Constants.Complete -> {
                        try {
                            Gson().fromJson(
                                intent.getStringExtra(Constants.Data),
                                PreviouslySchedulePojo::class.java
                            ).apply {
                                Uitls.showProgree(true, this@ScheduleActivity)
                                viewmodel.makeChapterAsCompleteOrSchedule(
                                    userName = userName,
                                    chapterId = chapterID,
                                    authorizationToken = token,
                                    date = selectedDate,
                                    time = bin.textView40.selectedItem.toString().plus(":")
                                        .plus(bin.textView41.selectedItem.toString()).plus(":")
                                        .plus(
                                            bin.textView42.selectedItem.toString()
                                        ),
                                    timeInMint = chapterDuration, type = Constants.Complete
                                )

//                    markChapterAsComplete(
//                        obj = this
//                    )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

        }

    }

    override fun onChapterScheduleSaveClick(selectedDate: String) {

    }

//    private fun markChapterAsComplete(obj: PreviouslySchedulePojo) {
//        Uitls.showProgree(true, this)
//        val call = mInterface.makeChapterAsCompleteOrSchedule(
//            userName = obj.userName,
//            userId = obj.userID,
//            chapterId = obj.chapterID,
//            authorizationToken = obj.token,
//            date = selectedDate,
//            time = bin.textView40.selectedItem.toString().plus(":")
//                .plus(bin.textView41.selectedItem.toString()).plus(":").plus(
//                    bin.textView42.selectedItem.toString()
//                ),
//            timeInMint = obj.chapterDuration, type = Constants.Complete
//        )
//        call.enqueue(object : Callback<ChapterCompleteRes> {
//            override fun onResponse(
//                call: Call<ChapterCompleteRes>,
//                response: Response<ChapterCompleteRes>
//            ) {
//                Uitls.showProgree(false, this@ScheduleActivity)
//                if (response.body() != null && response.isSuccessful) {
//                    if (response.body()!!.status == 200) {
//                        setResult(
//                            Activity.RESULT_OK,
//                            Intent().putExtra(Constants.isPreviouslyComplete, true)
//                        )
//                        finish()
//                    } else {
//                        Uitls.showToast(
//                            this@ScheduleActivity,
//                            getString(R.string.no_able_to_process_api)
//                        )
//                    }
//                } else {
//                    Uitls.onUnSuccessResponse(response.code(), this@ScheduleActivity)
//                }
//            }
//
//            override fun onFailure(call: Call<ChapterCompleteRes>, t: Throwable) {
//                Uitls.showProgree(false, this@ScheduleActivity)
//                Uitls.handlerError(this@ScheduleActivity, t)
//            }
//        })
//    }
//
//    private fun markChapterAsSchedule(obj: PreviouslySchedulePojo) {
//        Uitls.showProgree(true, this)
//        val call = mInterface.makeChapterAsCompleteOrSchedule(
//            userName = obj.userName,
//            userId = obj.userID,
//            chapterId = obj.chapterID,
//            authorizationToken = obj.token,
//            date = selectedDate,
//            time = bin.textView40.selectedItem.toString().plus(":")
//                .plus(bin.textView41.selectedItem.toString()).plus(":").plus(
//                    bin.textView42.selectedItem.toString()
//                ),
//            timeInMint = obj.chapterDuration, type = Constants.Schedule
//        )
//        call.enqueue(object : Callback<ChapterCompleteRes> {
//            override fun onResponse(
//                call: Call<ChapterCompleteRes>,
//                response: Response<ChapterCompleteRes>
//            ) {
//                Uitls.showProgree(false, this@ScheduleActivity)
//                if (response.body() != null && response.isSuccessful) {
//                    if (response.body()!!.status == 200) {
//                        setResult(
//                            Activity.RESULT_OK,
//                            Intent().putExtra(Constants.isPreviouslySchedule, true)
//                        )
//                        finish()
//                    } else {
//                        Uitls.showToast(
//                            this@ScheduleActivity,
//                            getString(R.string.no_able_to_process_api)
//                        )
//                    }
//                } else {
//                    Uitls.onUnSuccessResponse(response.code(), this@ScheduleActivity)
//                }
//            }
//
//            override fun onFailure(call: Call<ChapterCompleteRes>, t: Throwable) {
//                Uitls.showProgree(false, this@ScheduleActivity)
//                Uitls.handlerError(this@ScheduleActivity, t)
//            }
//        })
//    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

}