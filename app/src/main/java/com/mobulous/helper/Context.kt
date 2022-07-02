package com.mobulous.helper

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.DialogAddToFavLayBinding
import com.mobulous.fitscope.databinding.DialogDeleteRescheduleBinding
import com.mobulous.fitscope.databinding.DialogUploadProfileBinding

private var circulrDrawabe: CircularProgressDrawable? = null
fun Context.showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}

fun Context.GliderPlaceHolder(): Drawable {
    return circulrDrawabe
        ?: CircularProgressDrawable(this).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
//            return CircularProgressDrawable(con).apply {
//                strokeWidth = 5f
//                centerRadius = 30f
//                start()
//            }

}

fun Context.showMoreOption(isSave: Boolean, isFav: Boolean) {
    BottomSheetDialog(
        this,
        R.style.NewCustomBottomSheetDialogTheme
    ).apply {
        println("--showMoreOption--isSave->${isSave}-isFav->${isFav}")
        DialogAddToFavLayBinding.inflate(LayoutInflater.from(this@showMoreOption)).apply {
            setCanceledOnTouchOutside(true)
            setContentView(this.root)
            addToFavLbl.text =
                if (isFav) "Remove from Favorites" else "Add to Favorites"
            addToSaveLbl.text =
                if (isSave) "Remove from My List" else "Add to My List"

            addToFavLbl.setOnClickListener {
                sendBroadcast(Intent().apply {
                    action = Constants.explicitBroadCastAction
                    putExtra(
                        Constants.Type,
                        if (isFav) Enums.REMOVE_FROM_FAV.toString() else Enums.ADD_TO_FAV.toString()
                    )
                })
                dismiss()
            }

            addToSaveLbl.setOnClickListener {
                sendBroadcast(Intent().apply {
                    action = Constants.explicitBroadCastAction
                    putExtra(
                        Constants.Type,
                        if (isSave) Enums.REMOVE_FROM_SAVE.toString() else Enums.ADD_TO_SAVE.toString()
                    )
                })
                dismiss()

            }
            tvCancel.setOnClickListener {
                dismiss()

            }
            show()


        }

    }
}

fun Context.showMoreOptionForSchedule() {
    BottomSheetDialog(
        this,
        R.style.NewCustomBottomSheetDialogTheme
    ).apply {
        DialogDeleteRescheduleBinding.inflate(LayoutInflater.from(this@showMoreOptionForSchedule))
            .apply {
                setCanceledOnTouchOutside(true)
                setContentView(this.root)

                tvDeleteDialogDeleteRschdl.setOnClickListener {
                    LocalBroadcastManager.getInstance(this@showMoreOptionForSchedule)
                        .sendBroadcast(Intent().apply {
                            action = Constants.explicitBroadCastAction
                            putExtra(
                                Constants.Type,
                                Enums.REMOVE_FROM_SCHEDULE.toString()
                            )
                        })
                    dismiss()
                }

                tvRescheduleDialogDeleteRschdl.setOnClickListener {
                    LocalBroadcastManager.getInstance(this@showMoreOptionForSchedule)
                        .sendBroadcast(Intent().apply {
                            action = Constants.explicitBroadCastAction
                            putExtra(
                                Constants.Type,
                                Enums.ReSCHEDULE.toString()
                            )
                        })
                    dismiss()

                }
                tvCancleDialogDeleteRschdl.setOnClickListener {
                    dismiss()

                }
                show()


            }

    }
}


fun Context.showProfilePicOption() {
    BottomSheetDialog(
        this,
        R.style.NewCustomBottomSheetDialogTheme
    ).apply {
        DialogUploadProfileBinding.inflate(LayoutInflater.from(this@showProfilePicOption)).apply {
            setCanceledOnTouchOutside(true)
            setContentView(this.root)


            cameraLbl.setOnClickListener {
//                sendBroadcast(Intent().apply {
//                    action = Constants.explicitBroadCastAction
//                    putExtra(
//                        Constants.Type,
//                        if (isFav) Enums.REMOVE_FROM_FAV.toString() else Enums.ADD_TO_FAV.toString()
//                    )
//                })
                LocalBroadcastManager.getInstance(this@showProfilePicOption)
                    .sendBroadcast(Intent().apply {
                        action = Constants.explicitBroadCastAction
                        putExtra(Constants.Type, Enums.CameraIntent.toString())
                    })
                dismiss()
            }

            galleryLbl.setOnClickListener {
                LocalBroadcastManager.getInstance(this@showProfilePicOption)
                    .sendBroadcast(Intent().apply {
                        action = Constants.explicitBroadCastAction
                        putExtra(Constants.Type, Enums.GalleryIntent.toString())
                    })
                dismiss()
            }

            tvCancel.setOnClickListener {
                dismiss()

            }
            show()

        }

    }
}

