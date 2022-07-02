//package com.mobulous.Adapter.searchAptrs
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.mobulous.fitscope.databinding.SearchResultsRvDataItemBinding
//import com.mobulous.listner.SearchChildrenLisntr
//import com.mobulous.pojo.search.SearchedResultDataItem
//import java.util.*
//
//class SearchResultsAdapter(
//    var con: Context,
//    var searchResultsLst: ArrayList<SearchedResultDataItem>,
//    var lisntr: SearchChildrenLisntr
//) :
//    RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder>() {
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): MyViewHolder {
//        return MyViewHolder(
//            SearchResultsRvDataItemBinding.inflate(
//                LayoutInflater.from(con),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
////        holder.mView.lbl.text = searchResultsLst[position].title
////        holder.mView.img.loadNormalPhoto_Dimens300(
////            searchResultsLst[position].horizontalPreview ?: ""
////        )
////        holder.mView.textView36.text =
////            Uitls.getTimeStampHMS(
////                if (searchResultsLst[position].chapters?.get(0)?.duration != null) searchResultsLst[position].chapters?.get(
////                    0
////                )?.duration?.toInt() ?: 0 else 0
////            )
////
////        holder.itemView.setOnClickListener {
////            if (searchResultsLst[position].chapters?.isNotEmpty() == true) {
////                if ((searchResultsLst[position].title == searchResultsLst[position].chapters?.get(0)?.title)) {
////
////                    if (PrefUtils.with(con)
////                            .getString(Enums.isLogin.toString(), "false") == "true"
////                    ) {
////
////                        con.startActivity(
////                            Intent(con, AboutVideoActivity::class.java).putExtra(
////                                Constants.Data,
////                                Gson().toJson(searchResultsLst[position])
////                            ).putExtra(Constants.From, Enums.FromSearchResults.toString())
////                                .putExtra(Constants.InnerObj, Gson().toJson(searchResultsLst.drop(position + 1)))
////                        )
////
////                    } else {
////                        con.startActivity(
////                            Intent(con, BaseActivity::class.java).putExtra(
////                                Constants.From, Enums.NORMAL.toString()
////                            )
////                        )
////                    }
////
////                } else {
////                    lisntr.onSearchedChildrenClick(
////                        position,
////                        searchResultsLst[position].chapters,
////                        parentName = searchResultsLst[position].title ?: ""
////                    )
////                }
////            } else {
////                if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
////
////                    con.startActivity(
////                        Intent(con, AboutVideoActivity::class.java).putExtra(
////                            Constants.Data,
////                            Gson().toJson(searchResultsLst[position])
////                        ).putExtra(Constants.From, Enums.FromSearchResults.toString())
////                    )
////
////                } else {
////                    con.startActivity(
////                        Intent(con, BaseActivity::class.java).putExtra(
////                            Constants.From, Enums.NORMAL.toString()
////                        )
////                    )
////                }
////            }
////
////
////        }
//
//
//    }
//
//    override fun getItemCount(): Int {
//        return searchResultsLst.size
//    }
//
//    class MyViewHolder(val mView: SearchResultsRvDataItemBinding) :
//        RecyclerView.ViewHolder(mView.root) {
//
//    }
//
//}