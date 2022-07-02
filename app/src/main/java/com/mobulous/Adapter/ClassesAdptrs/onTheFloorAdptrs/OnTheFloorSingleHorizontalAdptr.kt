package com.mobulous.Adapter.ClassesAdptrs.onTheFloorAdptrs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.activity.video.AboutVideoActivity
import com.mobulous.fitscope.databinding.VerticalRvDataItemBinding
import com.mobulous.helper.*
import com.mobulous.pojo.dashboard.CommonCategoryDataItem

class OnTheFloorSingleHorizontalAdptr(
    val con: Context, val lst: ArrayList<CommonCategoryDataItem?>
) :
    RecyclerView.Adapter<OnTheFloorSingleHorizontalAdptr.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            VerticalRvDataItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.mView.lbl1.text = lst[position].
//        val childLayoutManager =
//            LinearLayoutManager(
//                holder.mView.innerHorizonRv.context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//        // childLayoutManager.initialPrefetchItemCount = 4
//        holder.mView.innerHorizonRv.apply {
//            layoutManager = childLayoutManager
//            adapter =
//                HomeChild_ClassAdptr(
//                    holder.mView.innerHorizonRv.context,
//                    lst[position].parent_object
//                )
//            setRecycledViewPool(viewPool)
//        }
        holder.mView.lbl2.text = lst[position]?.id ?: ""
        holder.mView.textView36.text =
            (if (lst[position]?.chapters?.get(0)?.duration != "null") lst[position]?.chapters?.get(0)?.duration?.toInt() else 0)?.let {
                Uitls.getTimeStampHMS(
                    it
                )
            }
        holder.mView.textView36.visibility =
            if (holder.mView.textView36.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        holder.mView.lbl2.setOnClickListener {
            if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
                con.startActivity(
                    Intent(con, AboutVideoActivity::class.java).putExtra(
                        Constants.Data,
                        lst[position]?.chapters?.get(0)?.id.toString()
                    ).putExtra(Constants.InnerObj, Gson().toJson(lst.drop(position + 1)))
                        .putExtra(Constants.Type, Enums.CATEGORY_DATA_ITEM.toString())
                    /*since its category data so we will parse this type of data differently in UpNext FRagment*/
                )

                println("------>${lst.drop(position + 1)}")

            } else {
                con.startActivity(
                    Intent(con, BaseActivity::class.java).putExtra(
                        Constants.From, Enums.NORMAL.toString()
                    )
                )
            }


        }
        holder.mView.imge.loadNormalPhoto_Dimens300(
            lst[position]?.chapters?.get(0)?.enrollImage ?: ""
        )

    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: VerticalRvDataItemBinding) :
        RecyclerView.ViewHolder(mView.root)
}