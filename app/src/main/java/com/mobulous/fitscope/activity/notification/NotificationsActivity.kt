package com.mobulous.fitscope.activity.notification

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.NotificationAdapter
import com.mobulous.Repo.NotificationRepo
import com.mobulous.ViewModelFactory.NotificationVMFactory
import com.mobulous.fitscope.databinding.ActivityNotificationsBinding
import com.mobulous.helper.NetworkReponse
import com.mobulous.helper.Uitls
import com.mobulous.helper.showToast
import com.mobulous.model.NotificationModel
import com.mobulous.viewModels.NotificationViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import java.util.*

class NotificationsActivity : AppCompatActivity() {
    lateinit var bin: ActivityNotificationsBinding
    lateinit var viewmodel: NotificationViewModel
    private var notilst = ArrayList<NotificationModel>()
    lateinit var notiadptr: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(bin.root)
        initView()
        listnr()
        observers()
    }

    private fun observers() {
        viewmodel.notificationData.observe(this, {
            Uitls.showProgree(false, this)
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataobj ->
                        if (dataobj.status == 200) {
                            NotificationAdapter(this, dataobj.data?.docs ?: arrayListOf()).apply {
                                bin.rcyNotification.layoutManager =
                                    LinearLayoutManager(this@NotificationsActivity)
                                bin.rcyNotification.adapter = this

                            }
                        }
                    }
                }
                is NetworkReponse.Error -> {
                    showToast(it.errorMessage)
                }
            }
        })


    }

    private fun initView() {
        viewmodel = ViewModelProvider(
            this,
            NotificationVMFactory(
                NotificationRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        this
                    )
                )
            )
        ).get(NotificationViewModel::class.java).apply {
            Uitls.showProgree(true, this@NotificationsActivity)
            getNotifications()
        }


//        notiadptr = NotificationAdapter(this, notilst).apply {
//            bin.rcyNotification.layoutManager = LinearLayoutManager(this@NotificationsActivity)
//            bin.rcyNotification.adapter = this
//
//        }


        bin.notificationTolbar.ivMainNotification.visibility = View.GONE
        bin.notificationTolbar.ivMainLogo.visibility = View.GONE
        bin.notificationTolbar.ivCorner.visibility = View.VISIBLE
        bin.notificationTolbar.tvTitle.text = "Notifications"
        bin.notificationTolbar.tvTitle.visibility = View.VISIBLE
        bin.notificationTolbar.ivDrawer.visibility = View.GONE


    }

    private fun listnr() {
        bin.notificationTolbar.ivCorner.setOnClickListener {
            onBackPressed()
        }

    }
}