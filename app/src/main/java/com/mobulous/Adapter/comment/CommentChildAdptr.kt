package com.mobulous.Adapter.comment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.databinding.ChildCommentLayBinding
import com.mobulous.helper.Uitls
import com.mobulous.helper.capitalizeWords
import com.mobulous.pojo.comment.RepliesItem

class CommentChildAdptr(val con: Context, val lst: ArrayList<RepliesItem?>) :
    RecyclerView.Adapter<CommentChildAdptr.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ChildCommentLayBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mView.timeReplyLbl.text = Uitls.getFormatDateComment(lst[position]?.createdAt ?: "")
        holder.mView.commentNameIcon.text =
            lst[position]?.user?.name?.substring(0, 2)?.capitalizeWords()
        holder.mView.commenterNameLbl.text = lst[position]?.user?.name?:""
        holder.mView.commentLbl.text = lst[position]?.comment
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: ChildCommentLayBinding) :
        RecyclerView.ViewHolder(mView.root)
}