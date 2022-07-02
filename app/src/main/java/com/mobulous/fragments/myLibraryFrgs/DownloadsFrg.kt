package com.mobulous.fragments.myLibraryFrgs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobulous.Adapter.libraryAptrs.DownloadAdapter
import com.mobulous.fitscope.databinding.FragmentDownloadsFrgBinding
import com.mobulous.pojo.video.VideoDataObj
import com.mobulous.room.AppDatabase

class DownloadsFrg : Fragment() {
    lateinit var bin: FragmentDownloadsFrgBinding
    private var chapterID = ""
    private var downloadLst = ArrayList<VideoDataObj>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentDownloadsFrgBinding.inflate(layoutInflater)
        initView()
        return bin.root
    }

    fun initView() {
        AppDatabase.getInstance(requireContext())?.userDao()?.getAllDownloads()?.forEach {
            chapterID = it.chapterID
            downloadLst.add(Gson().fromJson(it.data, VideoDataObj::class.java))
        }
        DownloadAdapter(
            this.requireContext(),
            downloadLst,
            chapterID
        ).apply {
            if (list.isNotEmpty()) {
                bin.imageView11.visibility = View.GONE
                bin.textView15.visibility = View.GONE
                bin.textView43.visibility = View.GONE
            }
            bin.rv.layoutManager = LinearLayoutManager(this@DownloadsFrg.requireContext())
            bin.rv.adapter = this
        }


    }


}