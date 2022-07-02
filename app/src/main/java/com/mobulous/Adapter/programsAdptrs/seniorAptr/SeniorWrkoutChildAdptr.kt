package com.mobulous.Adapter.programsAdptrs.seniorAptr

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.activity.video.AboutVideoActivity
import com.mobulous.fitscope.databinding.ChildHorizontalRvBinding
import com.mobulous.helper.*
import com.mobulous.listner.RecumbentCategoryCommentListnr
import com.mobulous.pojo.dashboard.ProgramCategoryDataItem

class SeniorWrkoutChildAdptr(
    val con: Context,
    val lst: ArrayList<ProgramCategoryDataItem?>,
    val listnr: RecumbentCategoryCommentListnr
) :
    RecyclerView.Adapter<SeniorWrkoutChildAdptr.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ChildHorizontalRvBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            if (lst[position]?.chapters?.isNotEmpty() == true && lst[position]?.chapters?.size == 1) {
                if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
                    con.startActivity(
                        Intent(con, AboutVideoActivity::class.java).putExtra(
                            Constants.Data, lst[position]?.chapters?.get(0)?.id.toString()
                        ).putExtra(Constants.InnerObj, Gson().toJson(lst.drop(position + 1)))
                            .putExtra(Constants.Type, Enums.CATEGORY_DATA_ITEM.toString())
                        /*since its category data so we will parse this type of data differently in UpNext FRagment*/
                    )
                } else {
                    con.startActivity(
                        Intent(con, BaseActivity::class.java).putExtra(
                            Constants.From, Enums.NORMAL.toString()
                        )
                    )
                }
            } else if (lst[position]?.chapters?.isNotEmpty() == true && lst[position]?.chapters?.size!! > 1) {
                listnr.onRecumbentClick(
                    data = Gson().toJson(lst[position]?.chapters),
                    parentLbl = lst[position]?.programTitle ?: ""
                )

            }

        }
        holder.mView.imgLbl.text = lst[position]?.programTitle ?: ""
        holder.mView.imageView9.visibility =
            if (Constants.isUserLogined) View.INVISIBLE else View.VISIBLE
        holder.mView.textView36.visibility =
            if (lst[position]?.chapters?.isNotEmpty() == true && lst[position]?.chapters?.size == 1) View.VISIBLE else View.INVISIBLE

        if (lst[position]?.chapters?.isNotEmpty() == true && lst[position]?.chapters?.size == 1) {
            holder.mView.textView36.text =
                (if (lst[position]?.chapters?.get(0)?.duration != "null") lst[position]?.chapters?.get(
                    0
                )?.duration?.toInt() else 0)?.let {
                    Uitls.getTimeStampHMS(
                        it
                    )
                }
        }
        holder.mView.imgView.loadNormalPhoto_Dimens300(lst[position]?.programImage ?: "")
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: ChildHorizontalRvBinding) :
        RecyclerView.ViewHolder(mView.root)
}