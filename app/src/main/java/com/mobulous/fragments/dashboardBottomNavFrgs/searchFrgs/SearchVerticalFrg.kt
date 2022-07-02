package com.mobulous.fragments.dashboardBottomNavFrgs.searchFrgs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.libraryAptrs.SingleViewChaptersAptr
import com.mobulous.Repo.SearchRepo
import com.mobulous.ViewModelFactory.SearchVmFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentSearchVerticalFrgBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.NetworkReponse
import com.mobulous.helper.Uitls
import com.mobulous.helper.showToast
import com.mobulous.viewModels.search.SearchViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class SearchVerticalFrg : Fragment() {
    lateinit var bin: FragmentSearchVerticalFrgBinding
    val agrs: SearchVerticalFrgArgs by navArgs()
    lateinit var viewmodel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentSearchVerticalFrgBinding.inflate(layoutInflater)
        initviews()
        listnr()
        observers()
        try {


//            val nestedChildObj =
//                Gson().fromJson<ArrayList<SearchNestedChaptersItem>>(
//                    agrs.searchChildrenData,
//                    object : TypeToken<ArrayList<SearchNestedChaptersItem>>() {}.type
//                )
//
//            SearchSingleViewAptr(requireContext(), nestedChildObj).apply {
//                bin.singleViewRv.layoutManager =
//                    if (!resources.getBoolean(R.bool.isTablet)) LinearLayoutManager(this@SearchVerticalFrg.requireContext()) else GridLayoutManager(
//                        this@SearchVerticalFrg.requireContext(), 2
//                    )
//                bin.singleViewRv.adapter = this
//            }
//
//            println("--------${nestedChildObj[0].title}")

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bin.root
    }

    private fun observers() {
        viewmodel.chapterLstData.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataObj ->
                        if (dataObj.status == 200) {
                            if (dataObj.data?.size!! > 0) {
                                bin.ViewAllToolbar.tvTitle.text = dataObj.data[0]?.id ?: ""
                                SingleViewChaptersAptr(
                                    requireContext(),
                                    dataObj.data[0]?.chapters ?: arrayListOf()
                                ).apply {
                                    bin.singleViewRv.layoutManager =
                                        if (!resources.getBoolean(R.bool.isTablet)) LinearLayoutManager(
                                            this@SearchVerticalFrg.requireContext()
                                        ) else GridLayoutManager(
                                            this@SearchVerticalFrg.requireContext(), 2
                                        )
                                    bin.singleViewRv.adapter = this
                                }
                            }

                        } else {
                            requireContext().showToast(dataObj.message ?: "")
                        }
                    }
                }
                is NetworkReponse.Error -> {
                    requireContext().showToast(it.errorMessage)
                }

            }

        })
    }

    private fun listnr() {
        bin.ViewAllToolbar.ivback.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

    private fun initviews() {
        bin.ViewAllToolbar.ivback.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainLogo.visibility = View.GONE
        bin.ViewAllToolbar.ivDrawer.visibility = View.GONE
        bin.ViewAllToolbar.tvTitle.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainNotification.visibility = View.GONE
        bin.ViewAllToolbar.ivDots.visibility = View.VISIBLE
        // bin.ViewAllToolbar.tvTitle.text = agrs.searchParentLbl ?: ""

        viewmodel = ViewModelProvider(
            this,
            SearchVmFactory(
                SearchRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(SearchViewModel::class.java).apply {
            Uitls.showProgree(true, requireContext())
            getChapterLst(Constants.savedUserID, programID = agrs.programID)
        }

    }

}