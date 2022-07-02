package com.mobulous.listner

import com.mobulous.pojo.comment.CommentsDataItems
import com.mobulous.pojo.dashboard.CommonChaptersItem
import com.mobulous.pojo.homePojo
import com.mobulous.pojo.schedule.ScheduleByDateDataItem

interface homeClassViewAllListnr {
    fun onViewAllClick(lbl: String, data: String)
}

interface DownloadVideoProgressListnr {
    fun onProgress(progress: Int) {

    }
}

interface parentCommentListnr {
    fun onReplyClick(postion: Int, obj: CommentsDataItems)
}

//interface SearchChildrenLisntr {
//    fun onSearchedChildrenClick(
//        postion: Int,
//        obj: ArrayList<SearchedResultDataItem?>?,
//        parentName: String
//    )
//}

interface ScheduleSaveListnr {
    fun onChapterScheduleSaveClick(selectedDate: String)
}

/** used for upcoming fragment and history fragment*/
interface UpComingNdHistoryLisntr {
    fun onMoreOptionSelected(
        position: Int,
        id: String,
        chapterID: String,
        chapterName: String,
        chapterDuration: String,
        scheduleID: String
    )
}

interface commentItemCountLisntr {
    fun onCommentCount(count: Int)
}

interface SubcriptionFromSideMenuListnr {
    fun onSubcriptionfromSideMenu()
}

interface OnClassDataLoad {
    fun onLoadDone()
}


interface WeightLossViewAllListnr {
    fun onWeightLossViewAllClick(lbl: String, data: String)
}

interface PrenatalViewAllListnr {
    fun onPrenatalViewAllClick(lbl: String, data: String)
}

interface SeniorWrkoutViewAllListnr {
    fun onSeniorWrkoutViewAllClick(lbl: String, data: String)
}

interface SeniorWrkoutHorizontalViewAllListnr {
    fun onSeniorWrkoutHorizontalViewAllClick(lbl: String, data: String)
}


interface BuildStrengthViewAllListnr {
    fun onBuildStrengthViewAllClick(lbl: String, data: String)
}

interface DateListnr {
    fun onDateClick(date: String)
}

interface RemoveScheduleLisntr {
    fun onScheduleClicked(
        position: Int,
        scheduleID: String? = null,
        obj: ScheduleByDateDataItem? = null
    )
}

interface ReScheduleLisntr {
    fun onReScheduleClicked(position: Int, scheduleID: String)
}


interface ScheduleDatesLisntr {
    fun onScheduleDates(date: String)
}

interface BikeViewAllListnr {
    fun onBikeViewAllClick(lbl: String, data: String)
}

interface BikeHorizontalViewAllListnr {
    fun onBikeHorizontalViewAllClick(lbl: String, obj: ArrayList<CommonChaptersItem?>)
}

interface EllipticalsHorizontalViewAllListnr {
    fun onEllipticalsHorizontalViewAllClick(lbl: String, obj: homePojo)
}


interface EllipticalViewAllListnr {
    fun onEllipticalViewAllClick(lbl: String, data: String)
}

interface TreadmillViewAllListnr {
    fun onTreadMillViewAllClick(lbl: String, data: String)
}

interface TreadmillHorizontalViewAllListnr {
    fun onTreadmillHorizontalViewAllClick(lbl: String, obj: homePojo)
}


interface OnTheFloorViewAllListnr {
    fun onTheFloorViewAllClick(lbl: String, data: String)
}

interface OnTheFloorHorizontalViewAllListnr {
    fun onTheFloorHorizontalViewAllClick(lbl: String, obj: ArrayList<CommonChaptersItem?>)
}

interface RecumbentCategoryCommentListnr {
    fun onRecumbentClick(data: String, parentLbl: String)
}


interface FeaturedViewAllListnr {
    fun onFeaturedViewAllClick(lbl: String, obj: homePojo)
}


interface RowerViewAllListnr {
    fun onRowerViewAllClick(lbl: String, obj: ArrayList<CommonChaptersItem?>)
}

interface RecumbentViewAllListnr {
    fun onRecumbentViewAllClick(
        lbl: String,
        data: String
    )
}


interface LibraryProgramListnr {
    fun onProgramClick(
        id: String, isProgramObj: Boolean
    )
}

interface SearchedProgramListnr {
    fun onProgramClick(
        id: String
    )
}

interface FilterListnr {
    fun onFilterSelect(filter: String)
}


interface FavoriteMoreOptionLisntr {
    fun onMoreClick(
        isProgramChapterType: Boolean,
        id: String,
        isSave: Boolean,
        position: Int,
        isCategoryType: Boolean
    )
}

interface SaveMoreOptionLisntr {
    fun onMoreClick(
        isProgramChapterType: Boolean,
        id: String,
        isSave: Boolean,
        isFav: Boolean,
        position: Int,
        isCategoryType: Boolean
    )
}


interface MyLViewAllListnr {
    fun onMyLViewAllClick(postion: Int)
}

interface SubScriptionLstnr {
    fun onSubscription()
}

interface SignUpLstnr {
    fun onSignUp()
}






