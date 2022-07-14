package com.mobulous.helper

import com.mobulous.fragments.dashboardBottomNavFrgs.classesTabs

object Constants {
    /*credentials
    * fitscopestudio@icloud.com
      fitscope2021*/
    const val explicitBroadCastAction = "com.mobulous.fitscope"
    const val NotificationChannelName = "FitScopeChannel"
    var CLASSES_NUM_TABS = classesTabs.size
    const val PROGRAMS_NUM_TABS = 4
    const val width_400 = 400
    var isAlreadyAddedToSave = false
    var isAlreadyAddedToFav = false

    /**this will be used when we favorite or save whole program.. id will from send or update broadcast receiver*/
    var programID =
        ""
    var isUserLogined = false
    const val ViewTypeProgramFav = 1
    const val ViewTypeChapterFav = 2
    const val ViewTypeProgramSave = 3
    const val ViewTypeChapterSave = 4
    const val ViewTypeProgram = 5   /*for search adapters*/
    const val ViewTypeChapter = 6 /*for search adapters*/
    const val ViewTypeAuthor = 7
    const val ViewTypeFilter = 8
    const val yMd = "yyyy-MM-dd"
    const val upcoming_date_format = "EEEE,MMM dd"
    const val height_400 = 400
    const val width_300 = 300
    const val height_300 = 300
    const val width_600 = 600
    const val height_600 = 600
    var topBarTabIndex = 0
    var topBarTabIndexViewID = 0
    var classHomeData = ""
    const val Schedule = "Schedule"
    const val ReSchedule = "ReSchedule"

    const val Case = "Case"
    const val Type = "Type"
    const val userId = "userid"
    const val categoryId = "categoryId"
    const val programId = "programId"

    const val PREF_NAME="app_data"
    const val RESOLUTIONTYPE="resolution"

    var savedUserID = "" /*holds the user id from login response*/
    const val InnerObj = ""
    const val DifficultyLevel = "Difficulty Level"
    const val Author = "Author"
    const val Trainer = "Trainer"
    const val MusicGenre = "Music Genre"
    const val Graphics = "Graphics"
    const val ExerciseGoal = "Exercise Goal"
    const val Equipment = "Equipment"
    var AllNextValue = ""
    const val NewChapterDataLst = ""
    const val isPreviouslyComplete = "isPreviouslyComplete"
    const val isPreviouslySchedule = "isPreviouslySchedule"
    const val Complete = "Complete"
    const val LIBRARY_NUM_TABS = 3
    var ViewAllParentLabel = ""
    const val TERMS_ND_CONDITION = "https://www.fitscope.com/terms-conditions"
    const val PRIVACY_URL = "https://www.fitscope.com/privacy"
    const val SUPPORT_URL = "https://www.fitscope.com/contactus"
    const val FAQ_URL = "https://studio.fitscope.com/pages/faq"
    const val CONTACT_US_URL = "https://www.fitscope.com/contactus"
    const val WEBSITE_LOGIN_URL = "https://studio.fitscope.com/sign_in"
    const val RESET_PASSWORD_URL = "https://studio.fitscope.com/forgot_password"
    const val Data = "data"
    const val From = "From"
    const val WeightLoss = "Weight loss"
    const val Prenatal = "Prenatal"
    const val SeniorWorkouts = "Senior Workouts"
    const val Bootcamps = "Bootcamps"
    const val Recumbent = "Recumbent"
    const val PowerWalking = "Power Walking"
    const val TaiChi = "Tai Chi"
    var isProfileIsInUri = false
    var isDefaultProfileSet = false
    var ellipticalData = ""
    var recumbentCategoryJson = ""
    var weightLossJson = ""
    var prenatalJson = ""
    var seniorWorkOutJson = ""
    var seniorWorkOutJson2 = ""

    var bootcampsJson = ""
    var isProgramSectionViewed = false


    var isSignOutFromInside = false
    var homeParentLblIndexingCountMapping = hashMapOf<String, Int>().apply {
        put("Latest Cycling", 0)
        put("Latest Elliptical", 1)
        put("Latest Rowing", 2)
        put("Latest Walking", 3)
        put("Latest Running", 4)
        put("Latest Recumbent", 5)
        put("Latest Airbike", 6)
        put("Latest Elliptical Stepper", 7)


    }
    var homeTabletModeTabItemsMapping = hashMapOf<String, Int>().apply {
        put("HOME", 0)
        put("BIKES", 1)
        put("ELLIPTICALS", 2)
        put("ROWER", 3)
        put("TREADMILL", 4)
        put("ON THE FLOOR", 5)


    }

    var bikesIndexingCountMapping = hashMapOf<String, Int>().apply {
        put("CYCLING", 25)
        put("RECUMBENT", 9)
        put("AIRBIKE", 5)

    }
    var ellipticalIndexingCountMapping = hashMapOf<String, Int>().apply {
        put("ELLIPTICAL", 24)
        put("ELLIPTICAL STEPPER", 6)
    }
    var treadmilIndexingCountMapping = hashMapOf<String, Int>().apply {
        put("RUNNING", 23)
        put("POWER WALKING", 10)
    }
    const val STRENGTH_TRAINING = "STRENGTH TRAINING"
    const val YOGA = "YOGA"
    const val Featured = "Featured"
    var feauredViewAllJson = ""
    var seniorWorkOutPojo = ""

    var ontheFloorIndexingCountMapping = hashMapOf<String, Int>().apply {
        put("STRENGTH TRAINING", 11)
        put("YOGA", 6)
        put("CORE", 14)
        put("MAT PILATES", 7)
        put("TAI CHI", 12)
        put("STRETCH & RECOVERY", 5)
    }

    var nonHomeTabCategoryJson = ""
    var weightLossChaptersJson = ""
    const val VIEWALL_NUM_TABS = 2


}