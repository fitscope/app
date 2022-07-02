package com.mobulous.fragments.myLibraryFrgs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.libraryAptrs.SingleViewChaptersAptr
import com.mobulous.Repo.library.LibraryRepo
import com.mobulous.ViewModelFactory.LibraryVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentSingleViewChapterFrgBinding
import com.mobulous.helper.*
import com.mobulous.pojo.library.FavChaptersItem

import com.mobulous.viewModels.library.LibraryViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class SingleViewChapterFrg : Fragment() {
    lateinit var bin: FragmentSingleViewChapterFrgBinding
    lateinit var viewModel: LibraryViewModel
    lateinit var adptr: SingleViewChaptersAptr
    private var chapterLst = ArrayList<FavChaptersItem?>()
    val args: SingleViewChapterFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentSingleViewChapterFrgBinding.inflate(layoutInflater)


        return bin.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
        listnr()
    }

    private fun listnr(){
        bin.ViewAllToolbar.ivDots.setOnClickListener {
            requireContext().showMoreOption(
                isSave = Constants.isAlreadyAddedToSave,
                isFav = Constants.isAlreadyAddedToFav
            )
        }
    }

    private fun viewmodelInit() {
        viewModel = ViewModelProvider(
            this,
            LibraryVMFactory(
                LibraryRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(LibraryViewModel::class.java).apply {
            Uitls.showProgree(true, requireContext())
            getFavChapterLst(
                PrefUtils.with(requireContext()).getString(Enums.UserID.toString(), "") ?: "",
                if (args.type) args.programID else "", if (args.type) "" else args.programID
            )
            /*programID is being used for both fields for api*/
        }


    }

    private fun initview() {
        bin.ViewAllToolbar.ivback.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainLogo.visibility = View.GONE
        bin.ViewAllToolbar.ivDrawer.visibility = View.GONE
        bin.ViewAllToolbar.tvTitle.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainNotification.visibility = View.GONE
        bin.ViewAllToolbar.ivDots.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivback.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
            // Navigation.findNavController(it).navigate(R.id.actionToProgram)
        }

        adptr = SingleViewChaptersAptr(
            requireContext(),
            chapterLst
        ).apply {
            bin.rv.layoutManager = LinearLayoutManager(requireContext())
            bin.rv.adapter = this
        }


    }

    private fun observer() {
        viewModel.getFavChapterLst.observe(viewLifecycleOwner, {
            println("---view---${bin.ViewAllToolbar.tvTitle.text}")
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let { dataObj ->
                            if (dataObj.status == 200) {
                                chapterLst.clear()
                                bin.ViewAllToolbar.tvTitle.text = ""
                                println("---itemCount--->${bin.rv.adapter?.itemCount}")
                                if (dataObj.data?.size!! > 0) {
                                    bin.ViewAllToolbar.tvTitle.text = dataObj.data[0]?.id ?: ""
                                    chapterLst.addAll(dataObj.data[0]?.chapters ?: arrayListOf())
                                }
                                adptr.notifyDataSetChanged()

                            } else {
                                requireActivity().showToast(dataObj.message ?: "")
                            }

                        }
                    }
                    is NetworkReponse.Error -> {
                        requireActivity().showToast(it.errorMessage)
                    }
                    else -> {

                    }

                }
            } ?: requireActivity().showToast(getString(R.string.no_able_to_process_api))
        })
    }

    override fun onResume() {
        super.onResume()
        viewmodelInit()
        observer()
    }

}