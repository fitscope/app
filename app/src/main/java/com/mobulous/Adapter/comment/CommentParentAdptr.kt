package com.mobulous.Adapter.comment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.databinding.CommentParentLayBinding
import com.mobulous.helper.Uitls
import com.mobulous.helper.capitalizeWords
import com.mobulous.listner.parentCommentListnr
import com.mobulous.pojo.comment.CommentsDataItems

class CommentParentAdptr(
    val con: Context,
    val lst: ArrayList<CommentsDataItems?>,
    val lisntr: parentCommentListnr
) :
    RecyclerView.Adapter<CommentParentAdptr.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CommentParentLayBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val childLayoutManager =
            LinearLayoutManager(
                holder.mView.childCommentRv.context,
                LinearLayoutManager.VERTICAL,
                false
            )
        // childLayoutManager.initialPrefetchItemCount = 4
        holder.mView.childCommentRv.apply {
            layoutManager = childLayoutManager
            adapter =
                CommentChildAdptr(
                    holder.mView.childCommentRv.context,
                    lst[position]?.replies ?: arrayListOf()
                )
            setRecycledViewPool(viewPool)
        }
        holder.mView.commentNameIcon.text =
            lst[position]?.user?.name?.substring(0, 2)?.capitalizeWords()
        holder.mView.commenterNameLbl.text = lst[position]?.user?.name?:""
        holder.mView.commentLbl.text = lst[position]?.comment
        holder.mView.timeReplyLbl.text = Uitls.getFormatDateComment(lst[position]?.createdAt ?: "")
        holder.mView.replyLbl.setOnClickListener {
            lisntr.onReplyClick(position, lst[position]!!)
        }

    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: CommentParentLayBinding) :
        RecyclerView.ViewHolder(mView.root)
}