package com.mobulous.fragments.classesFrgs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.mobulous.Adapter.ClassesAdptrs.home.HomeParent_ClassAdptr
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.ViewModelFactory.dashboard.classes.ClassHomeVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentClassesHomeFrgBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.NetworkReponse
import com.mobulous.helper.Uitls
import com.mobulous.helper.showToast
import com.mobulous.listner.homeClassViewAllListnr
import com.mobulous.pojo.dashboard.classes.ClassHome.ClassHomeDataItems
import com.mobulous.pojo.homePojo
import com.mobulous.viewModels.dashboard.classVMs.ClassHomeViewModel
import com.mobulous.viewPagers.dashboardViewPager.BannerVPAdptr
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import kotlinx.coroutines.launch


class ClassesHomeFrg : Fragment(), homeClassViewAllListnr {
    lateinit var listnr: homeClassViewAllListnr
    lateinit var bin: FragmentClassesHomeFrgBinding
    var dotcount: Int = 0
    private var classHomeDataLst = ArrayList<ClassHomeDataItems?>()
    lateinit var mInterface: ApiInterface
    lateinit var home_viewmodel: ClassHomeViewModel
    private var dots: Array<ImageView?>? = null
    private var lst = ArrayList<homePojo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentClassesHomeFrgBinding.inflate(layoutInflater)
        initViews()
        listnr()
        observer()



        return bin.root
    }


    fun initViews() {
        mInterface =
            ServiceBuilder.mobulousBuildServiceToken(ApiInterface::class.java, requireContext())

        home_viewmodel =
            ViewModelProvider(requireActivity(), ClassHomeVMFactory(ClassRepo(mInterface))).get(
                ClassHomeViewModel::class.java
            )

        bin.tvViewAllClassHomeFrg.setOnClickListener {
            listnr.onViewAllClick(Constants.Featured, data = Constants.feauredViewAllJson)
        }
    }

    private fun observer() {
        lifecycleScope.launch {
            home_viewmodel.classHomeData.observe(viewLifecycleOwner) {
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    classHomeDataLst.clear()
                                    dotcount = 0
                                    if (classHomeDataLst.addAll(dataObj.data ?: arrayListOf())) {
                                        //bike_viewmodel.getClassBikeData()
                                        if (classHomeDataLst.size > 1) {
                                            println("------home_viewmodel.getClassHomeData()------>")
                                            Constants.feauredViewAllJson =
                                                Gson().toJson(classHomeDataLst[0]?.chapters)
                                            Constants.isAlreadyAddedToSave =
                                                classHomeDataLst[0]?.isSave ?: false
                                            Constants.isAlreadyAddedToFav =
                                                classHomeDataLst[0]?.isFav ?: false
                                            Constants.programID =
                                                classHomeDataLst[0]?.categoryId.toString()
                                            setHomeData()
                                        }
                                    } else {
                                        requireContext().showToast(dataObj.message ?: "")
                                    }
                                }
                            }
                        }
                        is NetworkReponse.Error -> {
                            Uitls.showProgree(false, requireContext())
                            requireContext().showToast(it.errorMessage)
                        }

                        else -> {

                        }

                    }

                } ?: run {
                    Uitls.showProgree(false, requireContext())
                    requireContext().showToast(getString(R.string.no_able_to_process_api))
                }
            }

//            bike_viewmodel.bikedata.observe(requireActivity(), {
//                it?.let {
//                    it.data?.let { dataItems ->
////                        classHomeLst.clear()
////                        if (classHomeLst.addAll(dataItems)) {
////                            Constants.classHomeData = Gson().toJson(classHomeLst)
////                            bike_viewmodel.getClassBikeData()
////                        }
//
//                    }
//                }
//            })
        }


    }

    fun listnr() {
        bin.vpBanner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                updateDot(position)
            }

            override fun onPageSelected(position: Int) {
            }
        })

//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true /* enabled by default */) {
//                override fun handleOnBackPressed() {
//                    // Handle the back button event
//                }
//            }
//        requireActivity().onBackPressedDispatcher.addCallback(this.requireActivity(), callback)


    }


    private fun setHomeData() {
        classHomeDataLst[0]?.chapters?.let {
            BannerVPAdptr(requireContext(), it).apply {
                bin.vpBanner.adapter = this
                dotcount = count
                dots = arrayOfNulls(dotcount)  //initialise
                bin.SliderDots.removeAllViews()
                for (i in 0 until dotcount) {
                    dots!![i] = ImageView(this@ClassesHomeFrg.requireContext())
                    dots!![i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ClassesHomeFrg.requireContext(),
                            R.drawable.graydot
                        )
                    )
                    val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(10, 0, 10, 0)
                    bin.SliderDots.addView(dots!![i], params)

                }
                /*image drawable for the first dot*/
                dots!![0]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@ClassesHomeFrg.requireContext(),
                        R.drawable.ic_dark_dot
                    )
                )
            }
        }

        classHomeDataLst.removeAt(0)
        HomeParent_ClassAdptr(
            requireContext(),
            classHomeDataLst,
            this@ClassesHomeFrg
        ).apply {
            bin.homeRv.layoutManager =
                LinearLayoutManager(this@ClassesHomeFrg.requireContext())
            bin.homeRv.adapter = this
        }
        Uitls.showProgree(false, requireContext())
        bin.tvViewAllClassHomeFrg.visibility = View.VISIBLE
        bin.tvFEATUREDClassHomeFrg.visibility = View.VISIBLE

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listnr = activity as homeClassViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onViewAllClick(lbl: String, data: String) {
        listnr.onViewAllClick(lbl, data)
    }

    fun updateDot(position: Int) {
        for (i in 0 until dotcount) {
            dots!![i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this@ClassesHomeFrg.requireContext(),
                    R.drawable.graydot
                )
            )
        }
        dots!![position]?.setImageDrawable(
            ContextCompat.getDrawable(
                this@ClassesHomeFrg.requireContext(),
                R.drawable.ic_dark_dot,

                )
        )
    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true, requireContext())
        home_viewmodel.getClassHomeData()
    }
}