package com.mobulous.Adapter.searchAptrs

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.activity.video.AboutVideoActivity
import com.mobulous.fitscope.databinding.SearchResultsRvDataItemBinding
import com.mobulous.helper.*
import com.mobulous.listner.SearchedProgramListnr
import com.mobulous.pojo.search.SearchedChaptersItem
import com.mobulous.pojo.search.SearchedProgramsItem

class SearchResultLstAdapter(
    var chaptLst: ArrayList<SearchedChaptersItem?>,
    var programLst: ArrayList<SearchedProgramsItem?>,
    var programLstnr: SearchedProgramListnr
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.ViewTypeChapter -> {
                ChapterViewHolder(
                    SearchResultsRvDataItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ProgramViewHolder(
                    SearchResultsRvDataItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }


        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position < programLst.size) {
            Constants.ViewTypeProgram
        } else if (position - programLst.size < chaptLst.size) {
            Constants.ViewTypeChapter
        } else {
            -1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChapterViewHolder -> {
                populateSearchedChapterData(
                    chaptLst[position - programLst.size],
                    holder
                )
            }
            is ProgramViewHolder -> {
                populateSearchedProgramData(
                    programLst[position],
                    holder
                )
            }
        }

        holder.itemView.setOnClickListener {
            if (holder is ChapterViewHolder) {
                if (PrefUtils.with(holder.itemView.context)
                        .getString(Enums.isLogin.toString(), "false") == "true"
                ) {
                    //  println("------>${position}---->${holder.absoluteAdapterPosition}")
                    holder.itemView.context.startActivity(
                        Intent(holder.itemView.context, AboutVideoActivity::class.java).putExtra(
                            Constants.Data,
                            chaptLst[holder.absoluteAdapterPosition - programLst.size]?.id.toString()
                        ).putExtra(Constants.InnerObj, "")
                    )
                } else {
                    holder.itemView.context.startActivity(
                        Intent(holder.itemView.context, BaseActivity::class.java).putExtra(
                            Constants.From, Enums.NORMAL.toString()
                        )
                    )
                }
            } else {
                programLstnr.onProgramClick(
                    id = programLst[holder.absoluteAdapterPosition]?.id.toString()
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return chaptLst.size + programLst.size
    }

    class ChapterViewHolder(val itemNoti: SearchResultsRvDataItemBinding) :
        RecyclerView.ViewHolder(itemNoti.root) {

    }

    class ProgramViewHolder(val itemNoti: SearchResultsRvDataItemBinding) :
        RecyclerView.ViewHolder(itemNoti.root) {

    }

    private fun populateSearchedChapterData(
        chaptersItem: SearchedChaptersItem?,
        holder: ChapterViewHolder
    ) {
        holder.itemNoti.img.loadNormalPhoto_Dimens600(
            chaptersItem?.enrollImage ?: ""
        )
        holder.itemNoti.textView36.text =
            Uitls.getTimeStampHMS(
                if (chaptersItem?.duration != null) chaptersItem.duration
                    ?: 0 else 0
            )
        holder.itemNoti.lbl.text =
            chaptersItem?.programTitle ?: ""

//        holder.itemNoti.MoreBtn.setOnClickListener {
//            showMoreOptionSheet(
//                isProgramChapterType = false,
//                ChaptersItem?.id.toString(),
//                isSave = ChaptersItem?.isSave ?: false, holder.absoluteAdapterPosition,
//                isCategoryType = false
//            )
//        }

    }

    private fun populateSearchedProgramData(
        programItem: SearchedProgramsItem?,
        holder: ProgramViewHolder
    ) {
        holder.itemNoti.textView36.visibility = View.GONE
        programItem?.let { programObj ->
            holder.itemNoti.img.loadNormalPhoto_Dimens600(
                programObj.horizontalPreview ?: ""
            )

        }
        holder.itemNoti.lbl.text = programItem?.title ?: ""

//        holder.itemNoti.MoreBtn.setOnClickListener {
//            showMoreOptionSheet(
//                isProgramChapterType = true,
//                id = programItem?.programDetails?.let { it.id.toString() }
//                    ?: programItem?.categoryDetails?.id.toString(),
//                isSave = programItem?.isSave ?: false, holder.absoluteAdapterPosition,
//                isCategoryType = programItem?.programDetails == null
//            )
//        }

    }


//    private fun showMoreOptionSheet(
//        isProgramChapterType: Boolean,
//        id: String,
//        isSave: Boolean, position: Int, isCategoryType: Boolean
//    ) {
//        moreOptionLisntr.onMoreClick(isProgramChapterType, id, isSave, position, isCategoryType)
//
//    }

}