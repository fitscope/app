package com.mobulous.fragments.myLibraryFrgs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.libraryAptrs.MySaveLstAdapter
import com.mobulous.Repo.library.LibraryRepo
import com.mobulous.ViewModelFactory.LibraryVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentMyListFrgBinding
import com.mobulous.helper.*
import com.mobulous.listner.LibraryProgramListnr
import com.mobulous.listner.SaveMoreOptionLisntr
import com.mobulous.pojo.library.SaveChaptersItem
import com.mobulous.pojo.library.SaveProgramsItem
import com.mobulous.viewModels.library.LibraryViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import kotlinx.coroutines.launch

class MyListFrg : Fragment(), SaveMoreOptionLisntr {
    private  val TAG = "MyListFrg"
    lateinit var bin: FragmentMyListFrgBinding
    lateinit var saveAdptr: MySaveLstAdapter
    lateinit var listnr: LibraryProgramListnr
    lateinit var broadcastReceiver: BroadcastReceiver
    private var userID = ""
    var saveChaptLst = ArrayList<SaveChaptersItem?>()
    var saveProgramLst = ArrayList<SaveProgramsItem?>()
    lateinit var viewmodel: LibraryViewModel
    private var isProgramChapterType = false
    private var isProgramCategoryType = false
    private var ID = ""  /*more option action id ----> categoryID or programID or chapterID*/
    private var removedPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentMyListFrgBinding.inflate(layoutInflater)
        initView()
        observer()
        return bin.root
    }

    fun initView() {
        userID = PrefUtils.with(requireContext()).getString(Enums.UserID.toString(), "") ?: ""

        viewmodel = ViewModelProvider(
            this,
            LibraryVMFactory(
                LibraryRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(LibraryViewModel::class.java)
        saveAdptr = MySaveLstAdapter(
            this@MyListFrg.requireContext(),
            saveChaptLst = saveChaptLst,
            saveProgramLst = saveProgramLst, listnr, this
        ).apply {
            bin.rv.layoutManager =
                LinearLayoutManager(this@MyListFrg.requireContext())
            bin.rv.adapter = this
        }


        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                println("--typeReceived-->${intent?.getStringExtra(Constants.Type)}")
                println("--ID-->${this@MyListFrg.ID}")
                println("--FavProgram-->${this@MyListFrg.isProgramChapterType}")
                intent?.let { i ->
                    when (i.getStringExtra(Constants.Type)) {
                        Enums.REMOVE_FROM_FAV.toString() -> {
                            if (ID != "null") {
                                when (isProgramChapterType) {
                                    true -> {
                                        /***removing program from favorite*/
                                        Uitls.showProgree(true, requireContext())
                                        viewmodel.removeProgramFromFav(
                                            userID,
                                            programID = if (!isProgramCategoryType) ID else null,
                                            categoryID = if (isProgramCategoryType) ID else null
                                        )
                                    }
                                    false -> {
                                        /***removing chapter from favorite*/
                                        Uitls.showProgree(true, requireContext())
                                        viewmodel.removeChapterFromFav(userID, chapterID = ID)
                                    }
                                }
                            }

                        }
                        Enums.REMOVE_FROM_SAVE.toString() -> {
                            if (ID != "null") {
                                when (isProgramChapterType) {
                                    true -> {
                                        /***removing program from save*/
                                        Uitls.showProgree(true, requireContext())
                                        viewmodel.removeProgramFromSave(
                                            userID,
                                            programID = if (!isProgramCategoryType) ID else null,
                                            categoryID = if (isProgramCategoryType) ID else null
                                        )
                                    }
                                    false -> {
                                        /***removing chapter from save*/
                                        Uitls.showProgree(true, requireContext())
                                        viewmodel.removeChapterFromSave(userID, chapterID = ID)
                                    }
                                }
                            }
                        }

                        Enums.ADD_TO_FAV.toString() -> {
                            if (ID != "null") {
                                when (isProgramChapterType) {
                                    true -> {
                                        /***adding program to save*/
                                        Uitls.showProgree(true, requireContext())
                                        viewmodel.addProgramToFav(
                                            userID,
                                            programID = if (!isProgramCategoryType) ID else null,
                                            categoryID = if (isProgramCategoryType) ID else null
                                        )
                                    }
                                    false -> {
                                        /***adding chapter to save*/
                                        Uitls.showProgree(true, requireContext())
                                        viewmodel.addChapterToFav(userID, chapterID = ID)
                                    }
                                }
                            }
                        }

//                        Enums.ADD_TO_SAVE.toString() -> {
//                            if (ID != "null") {
//                                when (isProgramChapterType) {
//                                    true -> {
//                                        /***adding program to save*/
//                                        Uitls.showProgree(true, requireContext())
//                                        viewmodel.addProgramToSave(
//                                            userID,
//                                            programID = if (!isProgramCategoryType) ID else null,
//                                            categoryID = if (isProgramCategoryType) ID else null
//                                        )
//                                    }
//                                    false -> {
//                                        /***adding chapter to save*/
//                                        Uitls.showProgree(true, requireContext())
//                                        viewmodel.addChapterToSave(userID, chapterID = ID)
//                                    }
//                                }
//                            }
//
//                        }
                    }
                }
            }
        }

    }

    fun observer() {
        lifecycleScope.launch {
            viewmodel.getsaveLst.observe(viewLifecycleOwner, {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    saveChaptLst.clear()
                                    saveProgramLst.clear()
                                    saveChaptLst.addAll(dataObj.data?.saveChapters ?: arrayListOf())
                                    saveProgramLst.addAll(
                                        dataObj.data?.savePrograms ?: arrayListOf()
                                    )
                                    saveAdptr.notifyDataSetChanged()

                                    if(saveChaptLst.size>0 || saveProgramLst.size>0){
                                        hideshowNoDataLay(makeHide = true)
                                    }else{
                                        hideshowNoDataLay(makeHide = false)
                                    }

                                } else {
                                    requireActivity().showToast(dataObj.message ?: "")
                                }
                            } ?: requireActivity().showToast(it.data?.message ?: "")
                        }

                        is NetworkReponse.Error -> {
                            requireActivity().showToast(it.errorMessage)
                        }
                        else -> {

                        }
                    }

                }
            })

            viewmodel.removeProgramFromFav.observe(viewLifecycleOwner, {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    saveProgramLst[removedPosition]?.isFav = false
//                                    saveProgramLst.removeAt(removedPosition)
//                                    saveAdptr.notifyItemRemoved(removedPosition)
//                                    saveAdptr.notifyItemRangeRemoved(
//                                        saveChaptLst.size + saveProgramLst.size,
//                                        removedPosition
//                                    )

                                    if(saveProgramLst.size>0){
                                        hideshowNoDataLay(makeHide = true)
                                    }else{
                                        hideshowNoDataLay(makeHide = false)
                                    }

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

            viewmodel.removeChapterFromFav.observe(viewLifecycleOwner, {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    saveChaptLst[removedPosition]?.isFav = false
//                                    println("-----removedIndex->${removedPosition - saveProgramLst.size}")
//                                    saveChaptLst.removeAt(removedPosition - saveProgramLst.size)
//                                    saveAdptr.notifyItemRemoved(removedPosition - saveProgramLst.size)
//                                    saveAdptr.notifyItemRangeRemoved(
//                                        saveChaptLst.size + saveProgramLst.size,
//                                        removedPosition - saveProgramLst.size
//                                    )
                                    if(saveChaptLst.size>0){
                                        hideshowNoDataLay(makeHide = true)
                                    }else{
                                        hideshowNoDataLay(makeHide = false)
                                    }

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

            viewmodel.removeChapterFromSave.observe(viewLifecycleOwner, {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataobj ->
                                if (dataobj.status == 200) {
//                                    saveChaptLst[removedPosition]?.isSave = false
//                                    println("---------------${saveChaptLst[removedPosition]?.isSave}-")

                                    saveChaptLst.removeAt(removedPosition - saveProgramLst.size)
                                    saveAdptr.notifyItemRemoved(removedPosition )
                                    saveAdptr.notifyItemRangeRemoved(
                                        saveChaptLst.size + saveProgramLst.size,
                                        removedPosition - saveProgramLst.size
                                    )

                                    if(saveChaptLst.size>0){
                                        hideshowNoDataLay(makeHide = true)
                                    }else{
                                        hideshowNoDataLay(makeHide = false)
                                    }


                                }
                                requireActivity().showToast(dataobj.message ?: "")
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

            viewmodel.removeProgramFromSave.observe(viewLifecycleOwner, {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataobj ->
                                if (dataobj.status == 200) {
                                    //saveProgramLst[removedPosition]?.isSave = false
                                    saveProgramLst.removeAt(removedPosition)
                                    saveAdptr.notifyItemRemoved(removedPosition)
                                    saveAdptr.notifyItemRangeRemoved(
                                        saveChaptLst.size + saveProgramLst.size,
                                        removedPosition
                                    )

                                    if(saveProgramLst.size>0){
                                        hideshowNoDataLay(makeHide = true)
                                    }else{
                                        hideshowNoDataLay(makeHide = false)
                                    }


                                    Log.d(TAG, "observer: +${saveProgramLst.size}")
                                }
                                requireActivity().showToast(dataobj.message ?: "")
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

            viewmodel.addChapterToSave.observe(viewLifecycleOwner, {
//                println("----addChapterToSave--------")
//                println("---nnn--->${favChaptLst[removedPosition]?.isSave}")
                Uitls.showProgree(false, requireActivity().applicationContext)
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    saveChaptLst[removedPosition]?.isSave = false
                                    saveAdptr.notifyItemChanged(removedPosition)
//                                    println("---nnn--->${favChaptLst[removedPosition]?.isSave}")
                                }
                                requireActivity().showToast(dataObj.message ?: "")
                            } ?: requireActivity().showToast("data is invalid")
                        }

                        is NetworkReponse.Error -> {
                            requireActivity().showToast(it.errorMessage)
                        }
                        else -> {

                        }
                    }

                } ?: requireActivity().showToast(getString(R.string.no_able_to_process_api))
            })

            viewmodel.addProgramToSave.observe(viewLifecycleOwner, {
                Uitls.showProgree(false, requireActivity().applicationContext)
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    saveProgramLst[removedPosition]?.isSave = false
                                    saveAdptr.notifyItemChanged(removedPosition)
                                }
                                requireActivity().showToast(dataObj.message ?: "")
                            } ?: requireActivity().showToast("data is invalid")
                        }

                        is NetworkReponse.Error -> {
                            requireActivity().showToast(it.errorMessage)
                        }
                        else -> {

                        }
                    }

                } ?: requireActivity().showToast(getString(R.string.no_able_to_process_api))
            })

            viewmodel.addProgramToFave.observe(viewLifecycleOwner,{

                Uitls.showProgree(false, requireActivity().applicationContext)
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    saveProgramLst[removedPosition]?.isFav = true
                                    saveAdptr.notifyItemChanged(removedPosition)
                                }
                                requireActivity().showToast(dataObj.message ?: "")
                            } ?: requireActivity().showToast("data is invalid")
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

    }

    private fun hideshowNoDataLay(makeHide: Boolean) {
        bin.imageView11.visibility = if (makeHide) View.GONE else View.VISIBLE
        bin.textView43.visibility = if (makeHide) View.GONE else View.VISIBLE
        bin.textView15.visibility = if (makeHide) View.GONE else View.VISIBLE
        bin.rv.visibility = if (makeHide) View.VISIBLE else View.GONE

    }

    override fun onMoreClick(
        isProgramChapterType: Boolean,
        id: String,
        isSave: Boolean,
        isFav:Boolean,
        position: Int,
        isCategoryType: Boolean
    ) {
        this.isProgramChapterType = isProgramChapterType
        this.isProgramCategoryType = isCategoryType
        this.ID = id
        removedPosition = position
        requireContext().showMoreOption(isSave, isFav = isFav)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listnr = activity as LibraryProgramListnr
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            broadcastReceiver,
            IntentFilter(Constants.explicitBroadCastAction)
        )
        Uitls.showProgree(true, requireContext())
        viewmodel.getSaveList(
            PrefUtils.with(requireContext()).getString(Enums.UserID.toString(), "") ?: ""
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(broadcastReceiver)
    }
}