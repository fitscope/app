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
import com.mobulous.listner.FavoriteMoreOptionLisntr
import com.mobulous.listner.LibraryProgramListnr
import com.mobulous.pojo.fav.FavChaptersItem
import com.mobulous.pojo.fav.FavProgramsItem

class FavoriteAdapter(
    var con: Context,
    var favChaptLst: ArrayList<FavChaptersItem?>,
    var favProgramLst: ArrayList<FavProgramsItem?>,
    var programLstnr: LibraryProgramListnr,
    var moreOptionLisntr: FavoriteMoreOptionLisntr
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.ViewTypeChapterFav -> {
                FavChapterViewHolder(
                    LibraryVpAdapterBinding.inflate(
                        LayoutInflater.from(con),
                        parent,
                        false
                    )
                )
            }
            else -> {
                FavProgramViewHolder(
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
        return if (position < favProgramLst.size) {
            Constants.ViewTypeProgramFav
        } else if (position - favProgramLst.size < favChaptLst.size) {
            Constants.ViewTypeChapterFav
        } else {
            -1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavChapterViewHolder -> {
                populateFavChapterData(
                    favChaptLst[position - favProgramLst.size],
                    holder
                )
            }
            is FavProgramViewHolder -> {
                populateFavProgramData(
                    favProgramLst[position],
                    holder
                )
            }
        }

        holder.itemView.setOnClickListener {
            if (holder is FavChapterViewHolder) {
                if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
                    //  println("------>${position}---->${holder.absoluteAdapterPosition}")
                    con.startActivity(
                        Intent(con, AboutVideoActivity::class.java).putExtra(
                            Constants.Data,
                            favChaptLst[holder.absoluteAdapterPosition - favProgramLst.size]?.chapterDetails?.id.toString()
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
                    id = favProgramLst[holder.absoluteAdapterPosition]?.programDetails?.let { proObj ->
                        proObj.id.toString()
                    }
                        ?: favProgramLst[holder.absoluteAdapterPosition]?.categoryDetails?.id.toString(),
                    isProgramObj = favProgramLst[holder.absoluteAdapterPosition]?.programDetails != null
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return favChaptLst.size + favProgramLst.size
    }

    class FavChapterViewHolder(val itemNoti: LibraryVpAdapterBinding) :
        RecyclerView.ViewHolder(itemNoti.root) {

    }

    class FavProgramViewHolder(val itemNoti: LibraryVpAdapterBinding) :
        RecyclerView.ViewHolder(itemNoti.root) {

    }

    private fun populateFavChapterData(
        favChaptersItem: FavChaptersItem?,
        holder: FavChapterViewHolder
    ) {
        holder.itemNoti.img.loadNormalPhoto_Dimens600(
            favChaptersItem?.chapterDetails?.enrollImage ?: ""
        )
        holder.itemNoti.textView36.text =
            Uitls.getTimeStampHMS(
                if (favChaptersItem?.chapterDetails?.duration != null) favChaptersItem.chapterDetails.duration
                    ?: 0 else 0
            )
        holder.itemNoti.lbl.text =
            favChaptersItem?.chapterDetails?.title ?: ""

        holder.itemNoti.MoreBtn.setOnClickListener {
            showMoreOptionSheet(
                isProgramChapterType = false,
                favChaptersItem?.chapterDetails?.id.toString(),
                isSave = favChaptersItem?.isSave ?: false, holder.absoluteAdapterPosition,
                isCategoryType = false
            )
        }
    }

    private fun populateFavProgramData(
        favProgramItem: FavProgramsItem?,
        holder: FavProgramViewHolder
    ) {
        favProgramItem?.programDetails?.let { programObj ->
            holder.itemNoti.img.loadNormalPhoto_Dimens600(
                programObj.horizontalPreview ?: ""
            )

        } ?: holder.itemNoti.img.loadNormalPhoto_Dimens600(
            favProgramItem?.categoryDetails?.image ?: ""
        )
        holder.itemNoti.lbl.text = favProgramItem?.programDetails?.let { programObj ->
            programObj.title ?: ""
        } ?: favProgramItem?.categoryDetails?.title ?: ""

        holder.itemNoti.MoreBtn.setOnClickListener {
            showMoreOptionSheet(
                isProgramChapterType = true,
                id = favProgramItem?.programDetails?.let { it.id.toString() }
                    ?: favProgramItem?.categoryDetails?.id.toString(),
                isSave = favProgramItem?.isSave ?: false, holder.absoluteAdapterPosition,
                isCategoryType = favProgramItem?.programDetails == null
            )
        }

    }


    private fun showMoreOptionSheet(
        isProgramChapterType: Boolean,
        id: String,
        isSave: Boolean, position: Int, isCategoryType: Boolean
    ) {
        moreOptionLisntr.onMoreClick(isProgramChapterType, id, isSave, position, isCategoryType)

    }

}