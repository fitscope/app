package com.mobulous.Adapter.libraryAptrs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.activity.video.AboutVideoActivity
import com.mobulous.fitscope.databinding.LibraryVpAdapterBinding
import com.mobulous.helper.*
import com.mobulous.listner.LibraryProgramListnr
import com.mobulous.listner.SaveMoreOptionLisntr
import com.mobulous.pojo.library.SaveChaptersItem
import com.mobulous.pojo.library.SaveProgramsItem

class MySaveLstAdapter(
    var con: Context,
    var saveChaptLst: ArrayList<SaveChaptersItem?>,
    var saveProgramLst: ArrayList<SaveProgramsItem?>,
    var programLstnr: LibraryProgramListnr,
    var moreOptionLisntr: SaveMoreOptionLisntr
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.ViewTypeChapterSave -> {
                SaveChapterViewHolder(
                    LibraryVpAdapterBinding.inflate(
                        LayoutInflater.from(con),
                        parent,
                        false
                    )
                )
            }
            else -> {
                SaveProgramViewHolder(
                    LibraryVpAdapterBinding.inflate(
                        LayoutInflater.from(con),
                        parent,
                        false
                    )
                )
            }


        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position < saveProgramLst.size) {
            Constants.ViewTypeProgramSave
        } else if (position - saveProgramLst.size < saveChaptLst.size) {
            Constants.ViewTypeChapterSave
        } else {
            -1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SaveChapterViewHolder -> {
                populateFavChapterData(
                    saveChaptLst[position - saveProgramLst.size],
                    holder
                )
            }
            is SaveProgramViewHolder -> {
                populateFavProgramData(
                    saveProgramLst[position],
                    holder
                )
            }
        }

        holder.itemView.setOnClickListener {
            if (holder is SaveChapterViewHolder) {
                if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
                    //  println("------>${position}---->${holder.absoluteAdapterPosition}")
                    con.startActivity(
                        Intent(con, AboutVideoActivity::class.java).putExtra(
                            Constants.Data,
                            saveChaptLst[holder.absoluteAdapterPosition - saveProgramLst.size]?.chapterDetails?.id.toString()
                        )
                    )
                } else {
                    con.startActivity(
                        Intent(con, BaseActivity::class.java).putExtra(
                            Constants.From, Enums.NORMAL.toString()
                        )
                    )
                }
            } else {
                programLstnr.onProgramClick(
                    id = saveProgramLst[holder.absoluteAdapterPosition]?.programDetails?.let { proObj ->
                        proObj.id.toString()
                    }
                        ?: saveProgramLst[holder.absoluteAdapterPosition]?.categoryDetails?.id.toString(),
                    isProgramObj = saveProgramLst[holder.absoluteAdapterPosition]?.programDetails != null
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return saveChaptLst.size + saveProgramLst.size
    }

    class SaveChapterViewHolder(val itemNoti: LibraryVpAdapterBinding) :
        RecyclerView.ViewHolder(itemNoti.root) {

    }

    class SaveProgramViewHolder(val itemNoti: LibraryVpAdapterBinding) :
        RecyclerView.ViewHolder(itemNoti.root) {

    }

    private fun populateFavChapterData(
        saveChaptersItem: SaveChaptersItem?,
        holder: SaveChapterViewHolder
    ) {
        holder.itemNoti.img.loadNormalPhoto_Dimens600(
            saveChaptersItem?.chapterDetails?.enrollImage ?: ""
        )
        holder.itemNoti.textView36.text =
            Uitls.getTimeStampHMS(
                if (saveChaptersItem?.chapterDetails?.duration != null) saveChaptersItem.chapterDetails.duration
                    ?: 0 else 0
            )
        holder.itemNoti.lbl.text =
            saveChaptersItem?.chapterDetails?.title ?: ""

        holder.itemNoti.MoreBtn.setOnClickListener {
            showMoreOptionSheet(
                isProgramChapterType = false,
                saveChaptersItem?.chapterDetails?.id.toString(),
                isSave = saveChaptersItem?.isSave ?: false,
                isFav = saveChaptersItem?.isFav ?: false,
                holder.absoluteAdapterPosition,
                isCategoryType = false
            )
        }
    }

    private fun populateFavProgramData(
        saveProgramItem: SaveProgramsItem?,
        holder: SaveProgramViewHolder
    ) {
        saveProgramItem?.programDetails?.let { programObj ->
            holder.itemNoti.img.loadNormalPhoto_Dimens600(
                programObj.horizontalPreview ?: ""
            )

        } ?: holder.itemNoti.img.loadNormalPhoto_Dimens600(
            saveProgramItem?.categoryDetails?.image ?: ""
        )
        holder.itemNoti.lbl.text = saveProgramItem?.programDetails?.let { programObj ->
            programObj.title ?: ""
        } ?: saveProgramItem?.categoryDetails?.title ?: ""

        holder.itemNoti.MoreBtn.setOnClickListener {
            showMoreOptionSheet(
                isProgramChapterType = true,
                id = saveProgramItem?.programDetails?.let { it.id.toString() }
                    ?: saveProgramItem?.categoryDetails?.id.toString(),
                isSave = saveProgramItem?.isSave ?: false,
                isFav = saveProgramItem?.isFav ?: false,
                holder.absoluteAdapterPosition,
                isCategoryType = saveProgramItem?.programDetails == null
            )
        }

    }


    private fun showMoreOptionSheet(
        isProgramChapterType: Boolean,
        id: String,
        isSave: Boolean, isFav: Boolean, position: Int, isCategoryType: Boolean
    ) {
        moreOptionLisntr.onMoreClick(
            isProgramChapterType,
            id,
            isSave,
            isFav,
            position,
            isCategoryType
        )

    }

}