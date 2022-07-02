package com.mobulous.fitscope.activity.fragment.AboutVideo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.comment.CommentParentAdptr
import com.mobulous.Repo.videoDetail.VideoDetailRepo
import com.mobulous.ViewModelFactory.VideoDetailVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentCommentBinding
import com.mobulous.helper.*
import com.mobulous.listner.commentItemCountLisntr
import com.mobulous.listner.parentCommentListnr
import com.mobulous.pojo.comment.CommentsDataItems
import com.mobulous.viewModels.videodetail.VideoDetailViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder


class CommentFragment(val chapterID: String) : Fragment(), parentCommentListnr {
    lateinit var bin: FragmentCommentBinding
    lateinit var viewmodel: VideoDetailViewModel
    lateinit var mInterface: ApiInterface
    lateinit var commentCountLisntr: commentItemCountLisntr
    private var commentCount = 0
    private var userName = ""
    private var commentID = ""
    private var userId = ""
    lateinit var commentAdtr: CommentParentAdptr
    private var commentLst: ArrayList<CommentsDataItems?>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentCommentBinding.inflate(layoutInflater)
        initview()
        listnr()
        observer()
//        activity?.runOnUiThread {
//            getCommentLst(chapterID)
//        }

        return bin.root
    }

    private fun observer() {
        viewmodel.getCommentLstData.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        if (it.data?.status == 200) {
                            it.data.data?.let { commentResLst ->
                                commentLst?.clear()
                                commentLst?.addAll(commentResLst)
                                commentResLst.forEach {
                                    commentCount += 1
                                    it?.replies?.forEach {
                                        commentCount += 1
                                    }
                                }
                                commentCountLisntr.onCommentCount(commentCount)
                                commentAdtr.notifyDataSetChanged()
                            }
                        }
                    }

                    is NetworkReponse.Error -> {
                        requireActivity().showToast(it.errorMessage)
                    }
                    else -> {}
                }

            } ?: requireActivity().showToast(getString(R.string.no_able_to_process_api))
        })

        viewmodel.addCommentReplyData.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        if (it.data?.status == 200) {
                            bin.edtxtAddCommentComment.text = null
                            bin.crossIC.visibility = View.GONE
                            bin.replyLay.visibility = View.GONE
                            viewmodel.getComments(
                                hashMapOf(
                                    Pair("userId", userId),
                                    Pair("chapterId", chapterID)
                                )
                            )
                        } else {
                            requireActivity().showToast(it.data?.message.toString())
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

        viewmodel.addCommentData.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        if (it.data?.status == 200) {
                            bin.edtxtAddCommentComment.text = null
                            bin.crossIC.visibility = View.GONE
                            bin.replyLay.visibility = View.GONE
                            viewmodel.getComments(
                                hashMapOf(
                                    Pair("userId", userId),
                                    Pair("chapterId", chapterID)
                                )
                            )
                        } else {
                            requireActivity().showToast(it.data?.message.toString())
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

    private fun listnr() {

        bin.crossIC.setOnClickListener {
            bin.replyLay.visibility = View.GONE
            bin.crossIC.visibility = View.GONE
        }
        bin.tvPostComment.setOnClickListener {
            if (bin.edtxtAddCommentComment.text.isNotEmpty()) {
                Uitls.showProgree(true, requireContext())
                if (bin.replyLay.visibility != View.VISIBLE) {
                    viewmodel.addComment(
                        userId,
                        hashMapOf(
                            Pair("chapterId", chapterID),
                            Pair("comment", bin.edtxtAddCommentComment.text.toString())
                        )
                    )
                } else {
                    viewmodel.addCommentReply(
                        userId,
                        hashMapOf(
                            Pair("chapterId", chapterID),
                            Pair("comment", bin.edtxtAddCommentComment.text.toString()),
                            Pair("commentId", commentID)
                        )
                    )
                }
            }
        }
    }

    private fun initview() {
        bin.crossIC.visibility = View.INVISIBLE
        PrefUtils.with(this.requireContext()).apply {
            userName = getString(Enums.UserName.toString(), "") ?: ""
            userId = getString(Enums.UserID.toString(), "") ?: ""
        }
        viewmodel = ViewModelProvider(
            this, VideoDetailVMFactory(
                VideoDetailRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        this.requireContext()
                    )
                )
            )
        ).get(VideoDetailViewModel::class.java).apply {
            Uitls.showProgree(true, requireContext())
            getComments(hashMapOf(Pair("userId", userId), Pair("chapterId", chapterID)))
        }
        bin.replyLay.visibility = View.GONE
        commentLst = ArrayList()
        commentAdtr = CommentParentAdptr(
            this@CommentFragment.requireContext(),
            commentLst!!, this@CommentFragment
        ).apply {
            bin.rcyComment.layoutManager =
                LinearLayoutManager(this@CommentFragment.requireContext())
            bin.rcyComment.adapter = this
        }
    }

//    private fun commentReplyCall() {
//        Uitls.showProgree(true, this.requireContext())
//        val call = mInterface.addCommentReply(
//            AddCommentReplyPost(
//                userName = userName,
//                userId = userId,
//                chapterId = chapterID,
//                comment = bin.edtxtAddCommentComment.text.toString(),
//                commentId = commentID
//            )
//        )
//        call.enqueue(object : Callback<CommentReplyRes> {
//            override fun onResponse(
//                call: Call<CommentReplyRes>,
//                response: Response<CommentReplyRes>
//            ) {
//                if (response.body() != null && response.isSuccessful) {
//                    bin.edtxtAddCommentComment.text = null
//                    bin.crossIC.visibility = View.GONE
//                    bin.replyLay.visibility = View.GONE
//                    // getCommentLst(chapterID)
//                } else {
//                    Uitls.showProgree(false, this@CommentFragment.requireContext())
//                    Uitls.onUnSuccessResponse(
//                        response.code(),
//                        this@CommentFragment.requireContext()
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<CommentReplyRes>, t: Throwable) {
//                Uitls.showProgree(false, this@CommentFragment.requireContext())
//                Uitls.handlerError(this@CommentFragment.requireContext(), t)
//            }
//        })
//    }

//    private fun addComment() {
//        Uitls.showProgree(true, this.requireContext())
//        val call = mInterface.addComment(
//            AddCommentPost(
//                chapterId = chapterID,
//                comment = bin.edtxtAddCommentComment.text.toString(),
//                userName = userName,
//                userId = userId
//            )
//        )
//        call.enqueue(object : Callback<AddCommentRes> {
//            override fun onResponse(call: Call<AddCommentRes>, response: Response<AddCommentRes>) {
//                if (response.body() != null && response.isSuccessful) {
//                    bin.edtxtAddCommentComment.text = null
//                    bin.crossIC.visibility = View.GONE
//                    bin.replyLay.visibility = View.GONE
//                    // getCommentLst(chapterID)
//                } else {
//                    Uitls.showProgree(false, this@CommentFragment.requireContext())
//                    Uitls.onUnSuccessResponse(
//                        response.code(),
//                        this@CommentFragment.requireContext()
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<AddCommentRes>, t: Throwable) {
//                Uitls.handlerError(this@CommentFragment.requireContext(), t)
//                Uitls.showProgree(false, this@CommentFragment.requireContext())
//            }
//        })
//    }

//    private fun getCommentLst(id: String) {
//        commentLst?.clear()
//        commentCount = 0
//        Uitls.showProgree(true, this@CommentFragment.requireContext())
//        val call = mInterface.getComments(GetCommentLstPost(chapterId = id))
//        call.enqueue(object : Callback<GetCommentLstRes> {
//            override fun onResponse(
//                call: Call<GetCommentLstRes>,
//                response: Response<GetCommentLstRes>
//            ) {
//                Uitls.showProgree(isShow = false, this@CommentFragment.requireContext())
//                if (response.body() != null && response.isSuccessful) {
//                    if (response.body()!!.status == 200 && response.body()!!.data?.isNotEmpty() == true) {
//                        commentLst?.addAll(response.body()!!.data!!)
//                        response.body()!!.data?.forEach {
//                            commentCount += 1
//                            it?.replies?.forEach {
//                                commentCount += 1
//                            }
//                        }
//                        commentCountLisntr.onCommentCount(commentCount)
//                        commentAdtr.notifyDataSetChanged()
//                    }
//                } else {
//                    Uitls.onUnSuccessResponse(
//                        response.code(),
//                        this@CommentFragment.requireContext()
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<GetCommentLstRes>, t: Throwable) {
//                Uitls.showProgree(isShow = false, this@CommentFragment.requireContext())
//                Uitls.handlerError(this@CommentFragment.requireContext(), t)
//            }
//        })
//    }

    override fun onReplyClick(postion: Int, obj: CommentsDataItems) {
        bin.crossIC.visibility = View.VISIBLE
        bin.replyLay.visibility = View.VISIBLE
        bin.replyLay.text = "Replying to ${obj.user?.name ?: ""}'s comment"
        commentID = obj.id ?: ""
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            commentCountLisntr = activity as commentItemCountLisntr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

}