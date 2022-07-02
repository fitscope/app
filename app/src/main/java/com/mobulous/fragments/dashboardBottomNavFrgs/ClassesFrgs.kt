package com.mobulous.fragments.dashboardBottomNavFrgs

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mobulous.billingClient.MyBillingClient
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.BlutoothDevice.BluetoothDeviceActivity
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.activity.notification.NotificationsActivity
import com.mobulous.fitscope.activity.profile.ProfileActivity
import com.mobulous.fitscope.databinding.FragmentClassesFrgsBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Enums
import com.mobulous.helper.PrefUtils
import com.mobulous.listner.MyLViewAllListnr
import com.mobulous.listner.SignUpLstnr
import com.mobulous.listner.SubScriptionLstnr
import com.mobulous.viewPagers.dashboardViewPager.ClassesVPAdptr

val viewAllarry = arrayOf("Categories", "Comments")
val classesTabs = arrayOf("Home", "Bikes", "Ellipticals", "Rower", "Treadmill", "On The Floor")

class ClassesFrgs : Fragment(), MyLViewAllListnr, SubScriptionLstnr, SignUpLstnr {
    lateinit var lstrn: MyLViewAllListnr
    lateinit var Sublstrn: SubScriptionLstnr
    lateinit var broadcastReceiver: BroadcastReceiver
    lateinit var SignUpLstnr: SignUpLstnr
    lateinit var bin: FragmentClassesFrgsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bin = FragmentClassesFrgsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        initview()
        listnr()
        return bin.root

    }


    fun initview() {
        val tabletSize = resources.getBoolean(R.bool.isTablet)
        //  bin.tabItemLay.root.visibility = View.VISIBLE
//        bin.classedTabLay.visibility = if (tabletSize) View.INVISIBLE else View.VISIBLE
        // println("======visi${bin.tabItemLay.root.visibility}")
        if (tabletSize) {
            bin.classedTabLay.tabMode = TabLayout.MODE_AUTO
            bin.classedTabLay.visibility = View.GONE
        } else {
            topTabLay(isTablet = false)
            bin.classedTabLay.tabMode = TabLayout.MODE_SCROLLABLE
        }
        ClassesVPAdptr(
            this.childFragmentManager, this
                .lifecycle
        ).apply {
            bin.labViewpagr.adapter = this
            bin.labViewpagr.isUserInputEnabled = false
            bin.labViewpagr.offscreenPageLimit = classesTabs.size
            TabLayoutMediator(bin.classedTabLay, bin.labViewpagr) { tab, position ->
                if (resources.getBoolean(R.bool.isTablet)) {
                    tab.text = classesTabs[position]
//                    val view = LayoutInflater.from(this@ClassesFrgs.requireContext())
//                        .inflate(R.layout.tab_item_lay, null, false)
//                    tab.customView = view
//                    view.findViewById<TextView>(R.id.homeTb).text = classesTabs[position]
                } else {
                    tab.text = classesTabs[position]
                }

            }.attach()
        }
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                bin.mainToolbar.ivMainNotification.setImageResource(R.drawable.notification_bell_ic)
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver,
            IntentFilter(Constants.explicitBroadCastAction)
        )

      //  MyBillingClient(requireActivity())

    }

    fun listnr() {
        PrefUtils.with(this.requireContext()).apply {
            if (getString(Enums.isLogin.toString(), "false") == "true") {
                bin.maindrw.tvAlreadySignMAinDrawer.visibility = View.GONE
                bin.maindrw.tv7TrailMainDrawer.text = getString(R.string.signOut)
                bin.maindrw.tv7TrailMainDrawer.setTextColor(
                    ContextCompat.getColorStateList(
                        this@ClassesFrgs.requireContext(),
                        R.color.red
                    )
                )
                bin.maindrw.tv7TrailMainDrawer.background = ContextCompat.getDrawable(
                    this@ClassesFrgs.requireContext(),
                    R.drawable.drawable_stroke_red
                )
            }
        }

        bin.mainToolbar.ivDrawer.setOnClickListener {
            bin.drawerlayout.openDrawer(GravityCompat.START)
        }

        bin.mainToolbar.ivMainNotification.setOnClickListener {
            startActivity(Intent(this.requireContext(), NotificationsActivity::class.java))
        }
        bin.maindrw.tvProfileMainDrawer.setOnClickListener {
            startActivity(Intent(this.requireContext(), ProfileActivity::class.java))
        }
        bin.maindrw.tvFAQsMainDrawer.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FAQ_URL))
            startActivity(intent)
        }
        bin.maindrw.tvBluetoothMainDrawer.setOnClickListener {
            startActivity(Intent(this.requireContext(), BluetoothDeviceActivity::class.java))
        }

        bin.maindrw.tvManageSubsciption.setOnClickListener {
            val dialog = Dialog(this.requireContext(), R.style.CustomBottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.manage_subscription_dialog)
            val website = dialog.findViewById<TextView>(R.id.tvWebsite_DialogManageItem)
            website.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WEBSITE_LOGIN_URL))
                startActivity(intent)
            }
            val app = dialog.findViewById<TextView>(R.id.tvApp_DialogManageItem)
            app.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }


        bin.maindrw.tvSupportMainDrawer.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SUPPORT_URL))
            startActivity(intent)
        }
        bin.maindrw.tvMyListMainDrawer.setOnClickListener {
            onMyLViewAllClick(1)
        }
        bin.maindrw.tvFavoritesMainDrawer.setOnClickListener {
            onMyLViewAllClick(0)
        }
        bin.maindrw.tvDownloadsMainDrawer.setOnClickListener {
            onMyLViewAllClick(2)
        }
        bin.maindrw.tv7TrailMainDrawer.setOnClickListener {
            Constants.isSignOutFromInside =
                bin.maindrw.tv7TrailMainDrawer.text == getString(R.string.signOut)
            startActivity(
                Intent(this.requireContext(), BaseActivity::class.java).putExtra(
                    Constants.Data,
                    if (bin.maindrw.tv7TrailMainDrawer.text != getString(R.string.signOut)) "subscription" else "login"
                )
            )

        }

        bin.maindrw.tvAlreadySignMAinDrawer.setOnClickListener {
            startActivity(
                Intent(this.requireContext(), BaseActivity::class.java).putExtra(
                    Constants.Data,
                    "login"
                )
            )
        }
        bin.tabItemLay.homeTb.setOnClickListener {
            updateTabItem(0, bin.tabItemLay.homeTb.id)
        }
        bin.tabItemLay.bikeTb.setOnClickListener {
            updateTabItem(1, bin.tabItemLay.bikeTb.id)
        }
        bin.tabItemLay.ellipitcalsTb.setOnClickListener {
            updateTabItem(2, bin.tabItemLay.ellipitcalsTb.id)
        }
        bin.tabItemLay.rowerTb.setOnClickListener {
            updateTabItem(3, bin.tabItemLay.rowerTb.id)
        }
        bin.tabItemLay.treadmilTb.setOnClickListener {
            updateTabItem(4, bin.tabItemLay.treadmilTb.id)
        }
        bin.tabItemLay.onTheFloorTb.setOnClickListener {
            updateTabItem(5, bin.tabItemLay.onTheFloorTb.id)
        }


//        bin.tabItemLay.ellipitcalsTb.setOnClickListener {
//            bin.labViewpagr.setCurrentItem(
//                Constants.homeTabletModeTabItemsMapping[bin.tabItemLay.ellipitcalsTb.text.toString()]
//                    ?: 0, true
//            )
//        }

//        bin.mainToolbar.ivback.setOnClickListener {
//            Uitls.updateAppBar(true, bin.mainToolbar)
//            onBackPressed()
//        }
    }

    private fun topTabLay(isTablet: Boolean) {
        bin.classedTabLay.visibility = if (isTablet) View.GONE else View.VISIBLE
        bin.tabItemLay.root.visibility = if (isTablet) View.VISIBLE else View.GONE

        if (!isTablet) {
            val constraintSet = ConstraintSet()
            constraintSet.clone(bin.classRoot)
            constraintSet.connect(
                R.id.lab_viewpagr,
                ConstraintSet.TOP,
                R.id.classed_tab_lay,
                ConstraintSet.BOTTOM,
                0
            )
            constraintSet.applyTo(bin.classRoot)
        }

    }

    private fun updateTabItem(postion: Int, viewID: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(bin.tabItemLay.root)
        constraintSet.connect(
            R.id.red_bar,
            ConstraintSet.START,
            viewID,
            ConstraintSet.START,
            0
        )
        constraintSet.connect(
            R.id.red_bar,
            ConstraintSet.END,
            viewID,
            ConstraintSet.END,
            0
        )
        constraintSet.applyTo(bin.tabItemLay.root)
        bin.labViewpagr.setCurrentItem(postion, true)

        Constants.topBarTabIndex = postion
        Constants.topBarTabIndexViewID = viewID
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            lstrn = activity as MyLViewAllListnr
            Sublstrn = activity as SubScriptionLstnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }


    override fun onMyLViewAllClick(postion: Int) {

        lstrn.onMyLViewAllClick(postion)

    }

    override fun onSubscription() {
        Sublstrn.onSubscription()
    }

    override fun onSignUp() {
        SignUpLstnr.onSignUp()
    }

    override fun onResume() {
        super.onResume()
        bin.mainToolbar.ivMainNotification.setImageResource(R.drawable.notification)
        if (resources.getBoolean(R.bool.isTablet)) {
            updateTabItem(
                Constants.topBarTabIndex,
                if (Constants.topBarTabIndexViewID != 0) Constants.topBarTabIndexViewID else bin.tabItemLay.homeTb.id
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
    }


}