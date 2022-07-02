package com.mobulous.fitscope.activity.main

import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.mobulous.BaseAc
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.ActivityMainBinding
import com.mobulous.fragments.dashboardBottomNavFrgs.ClassesFrgsDirections
import com.mobulous.fragments.dashboardBottomNavFrgs.MyLibraryFrgDirections
import com.mobulous.fragments.dashboardBottomNavFrgs.ProgramsFrgDirections
import com.mobulous.fragments.dashboardBottomNavFrgs.SearchFrgDirections
import com.mobulous.fragments.viewAllFrgs.BikeViewAllFrgDirections
import com.mobulous.fragments.viewAllFrgs.OnTheFloorViewAllFrgDirections
import com.mobulous.fragments.viewAllFrgs.programs.seniorWorkout.RecumbentViewAllFrgDirections
import com.mobulous.helper.Constants
import com.mobulous.helper.Enums
import com.mobulous.helper.PrefUtils
import com.mobulous.helper.convertToString
import com.mobulous.listner.*
import com.mobulous.pojo.ChildHorizontalDataItem
import com.mobulous.pojo.dashboard.CommonChaptersItem
import com.mobulous.pojo.dashboard.classes.home.BannerPojo
import com.mobulous.pojo.homePojo
import com.mobulous.viewModels.dashboard.classVMs.ClassBikeViewModel
import com.mobulous.viewModels.dashboard.classVMs.ClassHomeViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class MainActivity : BaseAc<ActivityMainBinding>(), homeClassViewAllListnr,
    WeightLossViewAllListnr,
    PrenatalViewAllListnr,
    SeniorWrkoutViewAllListnr,
    SeniorWrkoutHorizontalViewAllListnr,
    BuildStrengthViewAllListnr,
    BikeViewAllListnr,
    EllipticalViewAllListnr,
    MyLViewAllListnr,
    SubScriptionLstnr,
    SignUpLstnr,
    EllipticalsHorizontalViewAllListnr,
    BikeHorizontalViewAllListnr,
    TreadmillViewAllListnr,
    RowerViewAllListnr,
    OnTheFloorViewAllListnr,
    OnTheFloorHorizontalViewAllListnr,
    RecumbentViewAllListnr,
    SearchedProgramListnr,
    RecumbentCategoryCommentListnr, LibraryProgramListnr,
    BottomNavigationView.OnNavigationItemReselectedListener {
    lateinit var mInterface: ApiInterface
    lateinit var listnr: OnClassDataLoad
    private var isLogin = false
    private var int: Int = 0
    private var homeMainHorizontalLst = ArrayList<ChildHorizontalDataItem>()
    private var rowingMainLst = ArrayList<homePojo>()
    private var rowingMainHorizontalLst = ArrayList<ChildHorizontalDataItem>()
    lateinit var home_viewmodel: ClassHomeViewModel
    lateinit var bike_viewmodel: ClassBikeViewModel

    private var bannerMainLst = ArrayList<BannerPojo>()
    private var homeCategoriesParentNameHashmap = LinkedHashMap<String, String>()
    private var homeParentTabsValidNames = arrayListOf<String>(
        "Featured",
        "Latest Cycling",
        "Latest Elliptical",
        "Latest Rowing",
        "Latest Walking",
        "Latest Running",
        "Latest Recumbent",
        "Latest Airbike",
        "Latest Elliptical Stepper"
    )


    /*bindingding bottom nav menu items with navHost graphs*/
    fun setupNavContrl() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment?
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(
                binding.bottomNavigationView,
                navHostFragment.navController
            )

        }
        binding.bottomNavigationView.setOnNavigationItemReselectedListener(this@MainActivity)
    }


    override fun onNavigationItemReselected(item: MenuItem) {

    }

    override fun onViewAllClick(lbl: String, data: String) {
//        Uitls.updateAppBar(false, binding.mainToolbar, lbl)

        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                if (lbl == Constants.Featured) ClassesFrgsDirections.actionToFeaturedViewAllFrg(
                    lbl
                ) else ClassesFrgsDirections.actionToViewAll(
                    dataToPopulate = data,
                    parentName = lbl
                )
            )
    }

    override fun onBikeViewAllClick(lbl: String, data: String) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ClassesFrgsDirections.actionToBikeViewAll(
                    dataToPopulate = data,
                    categoryName = lbl
                )
            )
    }

    override fun onEllipticalViewAllClick(lbl: String, data: String) {
        Constants.ellipticalData = data
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ClassesFrgsDirections.actionToEllipticalsViewAll(
                    dataToPopulate = "data",
                    categoryName = lbl
                )
            )
    }

    override fun onTreadMillViewAllClick(lbl: String, data: String) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ClassesFrgsDirections.actionToTreadmillViewAll(
                    dataToPopulate = data,
                    categoryName = lbl
                )
            )
    }


    override fun onWeightLossViewAllClick(lbl: String, data: String) {
//        Uitls.updateAppBar(false, binding.mainToolbar, lbl)
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ProgramsFrgDirections.actionToViewAll(
                    dataToPopulate = data,
                    categoryName = lbl
                )
            )

    }

    override fun onRecumbentViewAllClick(
        lbl: String, data: String
    ) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                RecumbentViewAllFrgDirections.actionRecumbentToSingleView(
                    categoryLbl = lbl,
                    objectString = data
                )
            )
    }

//    override fun onSearchedChildrenClick(
//        postion: Int,
//        obj: ArrayList<SearchNestedChaptersItem?>?,
//        parentLbl: String
//    ) {
//        binding.activityMainNavHostFragment.findNavController()
//            .navigate(
//                SearchFrgDirections.acionToSearchVerticalFrg(
//                    searchChildrenData = Gson().toJson(obj),
//                    searchParentLbl = parentLbl
//                )
//            )
//    }

    override fun onProgramClick(id: String) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                SearchFrgDirections.acionToSearchVerticalFrg(
                    programID = id
                )
            )
    }

    override fun onPrenatalViewAllClick(lbl: String, data: String) {
//        Uitls.updateAppBar(false, binding.mainToolbar, lbl)
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ProgramsFrgDirections.actionToViewAll(
                    dataToPopulate = data,
                    categoryName = lbl
                )
            )
    }

    override fun onSeniorWrkoutViewAllClick(lbl: String, data: String) {
//        Uitls.updateAppBar(false, binding.mainToolbar, lbl)
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ProgramsFrgDirections.actionToViewAll(
                    dataToPopulate = data,
                    categoryName = lbl
                )
            )
    }

    override fun onSeniorWrkoutHorizontalViewAllClick(lbl: String, data: String) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ProgramsFrgDirections.actionRecumbentToViewAll(
                    categoryLbl = lbl,
                    objectString = data
                )
            )
    }

    override fun onBuildStrengthViewAllClick(lbl: String, data: String) {
//        Uitls.updateAppBar(false, binding.mainToolbar, lbl)
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ProgramsFrgDirections.actionToViewAll(
                    dataToPopulate = data,
                    categoryName = lbl
                )
            )
    }


    override fun onMyLViewAllClick(postion: Int) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(ClassesFrgsDirections.actionClassesToLibrary(postion.toString()))
    }

    override fun onSubscription() {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(ClassesFrgsDirections.actionClassesToSubscriptionFragment2())
    }

    override fun onSignUp() {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(ClassesFrgsDirections.actionClassesToSignupFragment2())
    }

    override fun onBikeHorizontalViewAllClick(lbl: String, obj: ArrayList<CommonChaptersItem?>) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                BikeViewAllFrgDirections.actionToViewAll(
                    objectString = Gson().toJson(obj),
                    categoryLbl = lbl
                )
            )

    }

    override fun onRecumbentClick(data: String, parentLbl: String) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ProgramsFrgDirections.actionToRecumbentCategoriesCommentFrg(
                    dataToPopulate = data, categoryName = parentLbl
                )
            )
    }

    override fun onEllipticalsHorizontalViewAllClick(lbl: String, obj: homePojo) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                BikeViewAllFrgDirections.actionToViewAll(
                    objectString = Gson().toJson(obj),
                    categoryLbl = lbl
                )
            )
    }

    override fun onRowerViewAllClick(lbl: String, obj: ArrayList<CommonChaptersItem?>) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ClassesFrgsDirections.actionToCategoryComment(
                    objectString = Gson().toJson(obj),
                    categoryLbl = lbl
                )
            )
    }

    override fun onTheFloorViewAllClick(lbl: String, data: String) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                ClassesFrgsDirections.actionToOnTheFloorViewAll(
                    dataToPopulate = data,
                    categoryName = lbl
                )
            )

    }

    override fun onTheFloorHorizontalViewAllClick(
        lbl: String,
        obj: ArrayList<CommonChaptersItem?>
    ) {
        binding.activityMainNavHostFragment.findNavController()
            .navigate(
                OnTheFloorViewAllFrgDirections.actionToViewAll(
                    objectString = Gson().toJson(
                        obj
                    ), categoryLbl = lbl
                )
            )

    }

    override fun onProgramClick(id: String, isProgramObj: Boolean) {
        binding.activityMainNavHostFragment.findNavController().navigate(
            MyLibraryFrgDirections.singleViewChapterAction(
                programID = id, type = isProgramObj
            )
        )
    }

    override fun onResume() {
        super.onResume()

        PrefUtils.with(this).apply {
            isLogin = getString(Enums.isLogin.toString(), "false") == "true"
            Constants.savedUserID = getString(Enums.UserID.toString(), "") ?: ""
            if (getString(Enums.ProfileImage.toString(), "").isNullOrEmpty()) {
                Constants.isDefaultProfileSet = true
                save(
                    Enums.ProfileImage.toString(),
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_group_152)
                        ?.toBitmap(100, 100, null)
                        ?.convertToString() ?: ""
                )
            }

        }

//appsquare
    }


    override fun initViews() {
        mInterface = ServiceBuilder.mobulousBuildServiceToken(ApiInterface::class.java, this)
        setupNavContrl()
    }

    override fun observers() {

    }

    override fun listnr() {

    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}