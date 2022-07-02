package com.mobulous.fragments.viewAllFrgs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobulous.fitscope.databinding.FragmentCommentsFrgBinding

class CommentsFrg : Fragment() {
    lateinit var bin: FragmentCommentsFrgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentCommentsFrgBinding.inflate(layoutInflater)
        return bin.root
    }

}