//package com.mobulous.Adapter.viewAllAptrs
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.google.gson.Gson
//import com.mobulous.fitscope.activity.auth.BaseActivity
//import com.mobulous.fitscope.activity.video.AboutVideoActivity
//import com.mobulous.fitscope.databinding.VerticalRvDataItemBinding
//import com.mobulous.helper.*
//import com.mobulous.pojo.dashboard.program.SeniorWorkOutChaptersItem
//import com.mobulous.pojo.homePojo
//import com.mobulous.pojo.search.SearchNestedChaptersItem
//
//class SearchSingleViewAptr(val con: Context, val lst:ArrayList<SearchNestedChaptersItem>) :
//    RecyclerView.Adapter<SearchSingleViewAptr.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSingleViewAptr.ViewHolder {
//        return ViewHolder(
//            VerticalRvDataItemBinding.inflate(
//                LayoutInflater.from(con),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: SearchSingleViewAptr.ViewHolder, position: Int) {
//        holder.mView.lbl2.text = lst[position]?.title
//        holder.mView.imge.loadNormalPhoto_Dimens300(lst[position]?.enrollImage)
//        holder.mView.textView36.text =
//            Uitls.getTimeStampHMS(
//                if (lst[position]?.duration != null) lst[position]?.duration?.toInt() ?: 0 else 0
//            )
//
//        holder.mView.textView36.visibility =
//            if (holder.mView.textView36.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
//        holder.itemView.setOnClickListener {
//            if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
//                con.startActivity(
//                    Intent(con, AboutVideoActivity::class.java).putExtra(
//                        Constants.Data,
//                        Gson().toJson(lst[position])
//                    ).putExtra(Constants.From, Enums.FromSearchResults.toString())
//                        .putExtra(Constants.InnerObj, Gson().toJson(lst.drop(position + 1)))
//                )
//            } else {
//                con.startActivity(
//                    Intent(con, BaseActivity::class.java).putExtra(
//                        Constants.From, Enums.NORMAL.toString()
//                    )
//                )
//            }
//
//
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return lst.size
//    }
//
//    inner class ViewHolder(val mView: VerticalRvDataItemBinding) :
//        RecyclerView.ViewHolder(mView.root)
//}