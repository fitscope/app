package com.mobulous.Adapter.ClassesAdptrs.onTheFloorAdptrs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.databinding.VerticalRvDataItemBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.loadNormalPhoto_Dimens300
import com.mobulous.listner.OnTheFloorViewAllListnr
import com.mobulous.pojo.dashboard.classes.onTheFloor.ClassOnTheFlorrDataItem

class OnTheFloorAdptr(
    val con: Context,
    val lst: ArrayList<ClassOnTheFlorrDataItem?>,
    val lisntr: OnTheFloorViewAllListnr
) :
    RecyclerView.Adapter<OnTheFloorAdptr.ViewHolder>() {
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
        holder.mView.imge.loadNormalPhoto_Dimens300(lst[position]?.cateImage ?: "")
        holder.mView.lbl2.text = lst[position]?.category ?: ""
        holder.mView.textView36.visibility =
            if (holder.mView.textView36.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        holder.itemView.setOnClickListener {
            lst[position]?.categoryData?.let { categoryObj ->
                Constants.isAlreadyAddedToSave = categoryObj[0]?.isSave ?: false
                Constants.isAlreadyAddedToFav = categoryObj[0]?.isFav ?: false
                Constants.programID = categoryObj[0]?.categoryId ?: ""
            }
            lisntr.onTheFloorViewAllClick(
                lbl = lst[position]?.category ?: "",
                data = Gson().toJson(lst[position]?.categoryData)
            )
        }

//        holder.itemView.setOnClickListener {
//            con.startActivity(
//                Intent(con, AboutVideoActivity::class.java).putExtra(
//                    Constants.Data,
//                    Gson().toJson(lst[position])
//                ).putExtra(Constants.From.toString(), Enums.FromOnTheFloorChapters.toString())
//            )
//        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: VerticalRvDataItemBinding) :
        RecyclerView.ViewHolder(mView.root)
}