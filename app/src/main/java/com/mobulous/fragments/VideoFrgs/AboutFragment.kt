package com.mobulous.fragments.VideoFrgs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.mobulous.fitscope.databinding.FragmentAboutBinding
import com.mobulous.pojo.video.VideoDataObj


class AboutFragment(private val mVideoOb: String) : Fragment() {
    lateinit var bin: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentAboutBinding.inflate(layoutInflater)
        initviews()
        return bin.root
    }

    private fun initviews() {
        try {
            Gson().fromJson<VideoDataObj>(mVideoOb, VideoDataObj::class.java).apply {
                bin.textView31.text = longDescription ?: shortDescription
                bin.textView25.text = trainer ?: "nil"
                bin.textView26.text = difficulty ?: "nil"
                bin.textView29.text = goal ?: "nil"
                bin.textView30.text = music ?: "nil"
                if (bin.textView25.text.toString().isEmpty()) {
                    bin.textView22.visibility = View.GONE
                    bin.textView25.visibility = View.GONE

                }
                if (bin.textView26.text.toString().isEmpty()) {
                    bin.textView23.visibility = View.GONE
                    bin.textView26.visibility = View.GONE

                }
                if (bin.textView29.text.toString().isEmpty()) {
                    bin.textView27.visibility = View.GONE
                    bin.textView29.visibility = View.GONE

                }
                if (bin.textView30.text.toString().isEmpty()) {
                    bin.textView28.visibility = View.GONE
                    bin.textView30.visibility = View.GONE

                }


            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}