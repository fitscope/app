package com.mobulous.fragments.myLibraryFrgs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.libraryAptrs.FavoriteAdapter
import com.mobulous.Repo.library.LibraryRepo
import com.mobulous.ViewModelFactory.LibraryVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentFavoritiesFrgBinding
import com.mobulous.helper.*
import com.mobulous.listner.FavoriteMoreOptionLisntr
import com.mobulous.listner.LibraryProgramListnr
import com.mobulous.pojo.fav.FavChaptersItem
import com.mobulous.pojo.fav.FavProgramsItem
import com.mobulous.viewModels.library.LibraryViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import kotlinx.coroutines.launch

class FavoritiesFrg : Fragment(), FavoriteMoreOptionLisntr {
    lateinit var mInterface: ApiInterface
    private var userID = ""
    lateinit var favAdptr: FavoriteAdapter
    var favChaptLst = ArrayList<FavChaptersItem?>()
    var favProgramLst = ArrayList<FavProgramsItem?>()
    private var isProgramChapterType = false
    private var isProgramCategoryType = false

    private var ID = ""  /*more option action id ----> categoryID or programID or chapterID*/
    private var removedPosition = 0
    lateinit var broadcastReceiver: BroadcastReceiver
    lateinit var listnr: LibraryProgramListnr
    lateinit var viewmodel: LibraryViewModel
    lateinit var bin: FragmentFavoritiesFrgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentFavoritiesFrgBinding.inflate(layoutInflater)
        initView()
        observer()
        return bin.root
    }

    fun observer() {
        lifecycleScope.launch {
            viewmodel.getFavLst.observe(viewLifecycleOwner) {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    favChaptLst.clear()
                                    favProgramLst.clear()
                                    favChaptLst.addAll(dataObj.data?.favChapters ?: arrayListOf())
                                    favProgramLst.addAll(dataObj.data?.favPrograms ?: arrayListOf())
                                    favAdptr.notifyDataSetChanged()
//                                FavoriteAdapter(
//                                    requireContext(),
//                                    favChaptLst = dataObj.data?.favChapters ?: arrayListOf(),
//                                    favProgramLst = dataObj.data?.favPrograms ?: arrayListOf(),
//                                    listnr, this
//                                ).apply {
//                                    bin.rv.layoutManager = LinearLayoutManager(requireContext())
//                                    bin.rv.adapter = this
//
//                                }
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

                }?.run {
                   // Uitls.showProgree(false, requireContext())
                   // requireActivity().showToast(getString(R.string.no_able_to_process_api))
                }
            }

            viewmodel.removeProgramFromFav.observe(viewLifecycleOwner) {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    favProgramLst.removeAt(removedPosition)
                                    favAdptr.notifyItemRemoved(removedPosition)
                                    favAdptr.notifyItemRangeRemoved(
                                        favChaptLst.size + favProgramLst.size,
                                        removedPosition
                                    )
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
                }?.run {
                    Uitls.showProgree(false, requireContext())
                    requireActivity().showToast(getString(R.string.no_able_to_process_api))
                }
            }

            viewmodel.removeChapterFromFav.observe(viewLifecycleOwner) {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    println("-----removedIndex->${removedPosition - favProgramLst.size}")
                                    favChaptLst.removeAt(removedPosition - favProgramLst.size)
                                    favAdptr.notifyItemRemoved(removedPosition)
                                    favAdptr.notifyItemRangeRemoved(
                                        favChaptLst.size + favProgramLst.size,
                                        removedPosition - favProgramLst.size
                                    )
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
            }

            viewmodel.removeChapterFromSave.observe(viewLifecycleOwner) {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataobj ->
                                if (dataobj.status == 200) {
                                    println("--------------------------------------")
                                    favChaptLst[removedPosition]?.isSave = false
                                    println("---------------${favChaptLst[removedPosition]?.isSave}-")
//                                favChaptLst.removeAt(removedPosition - favProgramLst.size)
//                                favAdptr.notifyItemRemoved(removedPosition - favProgramLst.size)
//                                favAdptr.notifyItemRangeRemoved(
//                                    favChaptLst.size + favProgramLst.size,
//                                    removedPosition - favProgramLst.size
//                                )

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
            }

            viewmodel.removeProgramFromSave.observe(viewLifecycleOwner) {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataobj ->
                                if (dataobj.status == 200) {
                                    favProgramLst[removedPosition]?.isSave = false
//                                favProgramLst.removeAt(removedPosition)
//                                favAdptr.notifyItemRemoved(removedPosition)
//                                favAdptr.notifyItemRangeRemoved(
//                                    favChaptLst.size + favProgramLst.size,
//                                    removedPosition
//                                )
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
            }

            viewmodel.addChapterToSave.observe(viewLifecycleOwner) {
//                println("----addChapterToSave--------")
//                println("---nnn--->${favChaptLst[removedPosition]?.isSave}")
                Uitls.showProgree(false, requireActivity().applicationContext)
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    favChaptLst[removedPosition]?.isSave = false
                                    favAdptr.notifyItemChanged(removedPosition)
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
            }

            viewmodel.addProgramToSave.observe(viewLifecycleOwner) {
                Uitls.showProgree(false, requireActivity().applicationContext)
                it?.let {
                    when (it) {
                        is NetworkReponse.Success -> {
                            it.data?.let { dataObj ->
                                if (dataObj.status == 200) {
                                    favProgramLst[removedPosition]?.isSave = false
                                    favAdptr.notifyItemChanged(removedPosition)
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
            }
        }

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
        bin.noPlaylistLayout.root.visibility = View.GONE
        favAdptr = FavoriteAdapter(
            requireContext(),
            favChaptLst,
            favProgramLst,
            listnr, this
        ).apply {
            bin.rv.layoutManager = LinearLayoutManager(requireContext())
            bin.rv.adapter = this

        }
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                println("--typeReceived-->${intent?.getStringExtra(Constants.Type)}")
                println("--ID-->${this@FavoritiesFrg.ID}")
                println("--FavProgram-->${this@FavoritiesFrg.isProgramChapterType}")
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

//                        Enums.ADD_TO_FAV.toString() -> {
//                            if (ID != "null") {
//                                when (isProgramChapterType) {
//                                    true -> {
//                                        /***adding program to save*/
//                                        Uitls.showProgree(true, requireContext())
//                                        viewmodel.addProgramToFav(
//                                            userID,
//                                            programID = if (!isProgramCategoryType) ID else null,
//                                            categoryID = if (isProgramCategoryType) ID else null
//                                        )
//                                    }
//                                    false -> {
//                                        /***adding chapter to save*/
//                                        Uitls.showProgree(true, requireContext())
//                                        viewmodel.addChapterToFav(userID, chapterID = ID)
//                                    }
//                                }
//                            }
//                        }

                        Enums.ADD_TO_SAVE.toString() -> {
                            if (ID != "null") {
                                when (isProgramChapterType) {
                                    true -> {
                                        /***adding program to save*/
                                        Uitls.showProgree(true, requireContext())
                                        viewmodel.addProgramToSave(
                                            userID,
                                            programID = if (!isProgramCategoryType) ID else null,
                                            categoryID = if (isProgramCategoryType) ID else null
                                        )
                                    }
                                    false -> {
                                        /***adding chapter to save*/
                                        Uitls.showProgree(true, requireContext())
                                        viewmodel.addChapterToSave(userID, chapterID = ID)
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

    }

//    private fun getFavList() {
//        Uitls.showProgree(true, requireContext())
//        val call = mInterface.getFavChapters(
//            userId = PrefUtils.with(this.requireContext()).getString(Enums.UserID.toString(), "")
//                ?: ""
//        )
//        call.enqueue(object : Callback<GetFavoriteLstRes> {
//            override fun onResponse(
//                call: Call<GetFavoriteLstRes>,
//                response: Response<GetFavoriteLstRes>
//            ) {
//                Uitls.showProgree(false, this@FavoritiesFrg.requireContext())
//                if (response.body() != null && response.isSuccessful) {
//                    if (response.body()!!.data?.isNotEmpty() == true) {
//                        FavoriteAdapter(
//                            this@FavoritiesFrg.requireContext(),
//                            response.body()!!.data
//                        ).apply {
//                            bin.rv.layoutManager =
//                                LinearLayoutManager(this@FavoritiesFrg.requireContext())
//                            bin.rv.adapter = this
//                        }
//                    } else {
//                        bin.noPlaylistLayout.root.visibility = View.VISIBLE
//                    }
//                } else {
//                    Uitls.onUnSuccessResponse(response.code(), this@FavoritiesFrg.requireContext())
//                }
//            }
//
//            override fun onFailure(call: Call<GetFavoriteLstRes>, t: Throwable) {
//                Uitls.handlerError(this@FavoritiesFrg.requireContext(), t)
//                Uitls.showProgree(false, this@FavoritiesFrg.requireContext())
//            }
//        })
//    }

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
        viewmodel.getFavLst(
            userID
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(broadcastReceiver)
    }

    override fun onMoreClick(
        isProgramChapterType: Boolean,
        id: String,
        isSave: Boolean,
        position: Int,
        isCategoryType: Boolean
    ) {
        this.isProgramChapterType = isProgramChapterType
        this.isProgramCategoryType = isCategoryType
        this.ID = id
        removedPosition = position
        requireContext().showMoreOption(isSave, isFav = true)
    }


}