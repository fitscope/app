package com.mobulous.fragments.dashboardBottomNavFrgs

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobulous.Adapter.searchAptrs.SearchResultLstAdapter
import com.mobulous.Adapter.searchAptrs.filtersAptrs.FilterParentAdapter
import com.mobulous.Repo.SearchRepo
import com.mobulous.ViewModelFactory.SearchVmFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FilterBottomSheetLayBinding
import com.mobulous.fitscope.databinding.FragmentSearchFrgBinding
import com.mobulous.helper.*
import com.mobulous.listner.FilterListnr
import com.mobulous.listner.SearchedProgramListnr
import com.mobulous.pojo.filterPojo
import com.mobulous.pojo.search.FilterApplyPostPojo
import com.mobulous.pojo.search.FilterItem
import com.mobulous.pojo.search.SearchedChaptersItem
import com.mobulous.pojo.search.SearchedProgramsItem
import com.mobulous.viewModels.search.SearchViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import java.util.*
import kotlin.collections.ArrayList


class SearchFrg : Fragment(), SearchedProgramListnr, FilterListnr {
    lateinit var searchAdptr: SearchResultLstAdapter
    lateinit var filterAdptr: FilterParentAdapter
    lateinit var view_by_crd: FilterBottomSheetLayBinding
    lateinit var bottomSheetDialog: BottomSheetDialog
    var selectedFilterLstParams = ArrayList<String>()
    private var filterHashMap = HashMap<String, String>()

    // private var authorLst = ArrayList<AuthorItem?>()
    private var filterLst = ArrayList<FilterItem?>()

    // private var searchResultsLst = ArrayList<SearchedResultDataItem>()
    private var searchedChapterLst = ArrayList<SearchedChaptersItem?>()
    private var searchedProgramLst = ArrayList<SearchedProgramsItem?>()
    private var query = ""
    lateinit var bin: FragmentSearchFrgBinding
    lateinit var viewmodel: SearchViewModel
    private var filter_lst = ArrayList<filterPojo>()
    lateinit var searchedtemLisntr: SearchedProgramListnr
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentSearchFrgBinding.inflate(layoutInflater)
        initViews()
        lisnr()
        observers()


        return bin.root


    }

    private fun observers() {
        viewmodel.filterParameterLst.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { res ->
                        if (res.status == 200) {
                            //authorLst.clear()
                            filterLst.clear()
                            // authorLst.addAll(res.data?.author ?: arrayListOf())
                            /** appending the author obj into filter list*/
                            filterLst.add(
                                FilterItem(
                                    createdAt = null,
                                    V = null,
                                    id = null,
                                    sort = null,
                                    value = res.data?.author?.let { authorLst ->
                                        authorLst.map { it?.title }
                                    } ?: arrayListOf(),
                                    key = "Author",
                                    updatedAt = null
                                )
                            )
                            filterLst.addAll(res.data?.filter ?: arrayListOf())
                            filterAdptr.notifyDataSetChanged()
                            view_by_crd.progressBar.visibility = View.GONE
                            println("----filterList------>${filterLst}")
                        } else {
                            requireContext().showToast(res.message ?: "")
                        }

                    }
                }

                is NetworkReponse.Error -> {
                    requireContext().showToast(it.errorMessage)
                }

            }
        }

        viewmodel.searchResults.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let {
                        if (it.status == 200) {
                            bin.resultsLbl.visibility = View.VISIBLE
                            searchedChapterLst.clear()
                            searchedProgramLst.clear()
                            searchedChapterLst.addAll(it.data?.chapters ?: arrayListOf())
                            searchedProgramLst.addAll(it.data?.programs ?: arrayListOf())

                            // searchResultsLst.addAll(response.body()!!)
                            bin.resultsLbl.text =
                                (searchedChapterLst.size + searchedProgramLst.size).toString()
                                    .plus(" Results")
                            //  searchAdptr.notifyItemRangeInserted(0, searchResultsLst.size)
                            searchAdptr.notifyDataSetChanged()
                        } else {
                            requireContext().showToast(it.message ?: "Something went wrong.")
                        }

                    } ?: requireContext().showToast(getString(R.string.no_able_to_process_api))
                }

                is NetworkReponse.Error -> {
                    requireContext().showToast(it.errorMessage)
                }
                else -> {

                }
            }

        })

        viewmodel.searchFilterResults.observe(viewLifecycleOwner, {
            bin.progressBar.visibility = View.GONE
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let {
                        if (it.status == 200) {
                            bin.resultsLbl.visibility = View.VISIBLE
                            searchedChapterLst.clear()
                            searchedProgramLst.clear()
                            searchedChapterLst.addAll(it.data?.chapters ?: arrayListOf())
                            searchedProgramLst.addAll(it.data?.programs ?: arrayListOf())

                            // searchResultsLst.addAll(response.body()!!)
                            bin.resultsLbl.text =
                                it.data?.totalLength.plus(" Results")
                            //  searchAdptr.notifyItemRangeInserted(0, searchResultsLst.size)
                            searchAdptr.notifyDataSetChanged()
                        } else {
                            requireContext().showToast(it.message ?: "Something went wrong.")
                        }

                    } ?: requireContext().showToast(getString(R.string.no_able_to_process_api))
                }

                is NetworkReponse.Error -> {
                    requireContext().showToast(it.errorMessage)
                }
                else -> {

                }
            }

        })
    }

    fun initViews() {
        view_by_crd =
            FilterBottomSheetLayBinding.inflate(LayoutInflater.from(this@SearchFrg.requireContext()))
        bottomSheetDialog = BottomSheetDialog(
            this@SearchFrg.requireContext(),
            R.style.CustomBottomSheetDialogTheme
        ).apply {
            setContentView(view_by_crd.root)
            view_by_crd.applyFiltrs.setOnClickListener {
                // println("----range-->${view_by_crd.rangeSlider.values[0].toInt()}")
                bin.progressBar.visibility = View.VISIBLE
                viewmodel.getFilterSearchResults(
                    FilterApplyPostPojo(
                        difficulty = filterHashMap.filter { it.value == Constants.DifficultyLevel }.keys.toList(),
                        goal = filterHashMap.filter { it.value == Constants.ExerciseGoal }.keys.toList(),
                        music = filterHashMap.filter { it.value == Constants.MusicGenre }.keys.toList(),
                        trainer = filterHashMap.filter { it.value == Constants.Trainer }.keys.toList(),
                        fromTime = if (view_by_crd.rangeSlider.values[0].toInt() == 0) null else view_by_crd.rangeSlider.values[0].toInt() * 60,
                        toTime = if (view_by_crd.rangeSlider.values[1].toInt() == 90) null else view_by_crd.rangeSlider.values[1].toInt() * 60,
                        authorTitle = filterHashMap.filter { it.value == Constants.Author }.keys.toList()
                    )
                )
                dismiss()
            }
            view_by_crd.clrFiltrs.setOnClickListener {
                view_by_crd.progressBar.visibility = View.VISIBLE
                view_by_crd.rangeSlider.setValues(0f, 90f)
                filterHashMap.clear()
                viewmodel.getFilterParameterLst()
                // filterAdptr.notifyDataSetChanged()
            }
        }

        searchAdptr =
            SearchResultLstAdapter(
                chaptLst = searchedChapterLst, programLst = searchedProgramLst, this
            ).apply {
                bin.rv.layoutManager =
                    LinearLayoutManager(this@SearchFrg.requireContext())
                bin.rv.adapter = this

            }

        filterAdptr = FilterParentAdapter(filterLst, this).apply {
            view_by_crd.rv.layoutManager =
                LinearLayoutManager(this@SearchFrg.requireContext())
            view_by_crd.rv.adapter = this
        }

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
        ).get(SearchViewModel::class.java)

    }

    fun lisnr() {
        view_by_crd.rangeSlider.addOnChangeListener { slider, value, fromUser ->
            view_by_crd.lngthRgneLbl.text = slider.values[0].toInt().toString().plus("-")
                .plus(slider.values[1].toInt().toString().plus(" min"))

        }
        bin.filterBtn.setOnClickListener {
            requireActivity().hideKeyboard()
            bottomSheetDialog.show()
            if (filterHashMap.isNullOrEmpty())
                viewmodel.getFilterParameterLst()
        }

        bin.SearchToolbar.lbl.text = "Search"
        bin.searcbarEditxt.afterTextChanged {
            if (it.length > 3 || it.length == 3 && query != it) {
                query = it
                viewmodel.getSearchResults(query = it)
            } else if (it.isEmpty()) {
                Handler(Looper.getMainLooper()).post {
                    query = ""
                    searchAdptr.notifyItemRangeRemoved(
                        0,
                        searchedChapterLst.size + searchedProgramLst.size
                    )
                    searchedProgramLst.clear()
                    searchedChapterLst.clear()
                    bin.resultsLbl.visibility = View.INVISIBLE
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            searchedtemLisntr = activity as SearchedProgramListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onFilterSelect(filter: String) {
//        if (selectedFilterLstParams.contains(filter)) {
//            selectedFilterLstParams.remove(filter)
//        } else {
//            selectedFilterLstParams.add(filter)
//        }

        when (filter) {
            Enums.All.toString() -> {
                val allParentLbl =
                    filterLst.find { it?.value!!.contains(Constants.AllNextValue) }?.key.toString()
                filterHashMap.entries.removeAll { it.value == allParentLbl }
                //  filterHashMap[Enums.All.toString()] = allParentLbl
            }
            else -> {
                if (filterHashMap.containsKey(filter)) {
                    filterHashMap.remove(filter)
                } else {
                    filterHashMap[filter] =
                        filterLst.find { it?.value!!.contains(filter) }?.key.toString()
                }
            }
        }
        println("-----filer----${filterHashMap}")


    }

    override fun onProgramClick(id: String) {
        searchedtemLisntr.onProgramClick(id)
    }

}