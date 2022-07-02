package com.mobulous.helper

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.URLSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.CommonToolbarBinding
import com.mobulous.fitscope.databinding.CustomVideoPlayerBinding
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class Uitls {
    companion object {
        private var progressDialog: Dialog? = null
        private var circulrDrawabe: CircularProgressDrawable? = null
        private var materialDialog: MaterialAlertDialogBuilder? = null

//        fun checkIfFragmentAttached(operation: Context.() -> Unit) {
//            if (isAdded && context != null) {
//                operation(requireContext())
//            }
//        }

        fun getCurrentFormatedDate(format: String): String =
            SimpleDateFormat(format, Locale.getDefault()).format(Date()).toString()

        fun onUnSuccessResponse(code: Int, con: Context) {
            Toast.makeText(con, "Not able to process your request", Toast.LENGTH_SHORT).show()
        }

        fun hideStatusBar(flag: Boolean, con: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (flag) {
                    con.window.insetsController?.show(WindowInsets.Type.statusBars())
                } else {
                    con.window.insetsController?.hide(WindowInsets.Type.statusBars())
                }
            } else {
                if (flag) {
                    // Show status bar
                    con.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                } else {
                    // Hide status bar
                    con.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
        }

        fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString
        }

        fun getFormatDateComment(date: String): String {
            return try {
                SimpleDateFormat(
                    "dd MMM",
                    Locale.getDefault()
                ).format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(date))
            } catch (e: Exception) {
                ""
            }
        }


//        fun getDatePicker(
//            con: Context,
//            supportFragmentManager: FragmentManager,
//            format: String, view: EditText
//        ) {
//            MaterialDatePicker.Builder.datePicker().setSelection(Date().time)
//                .build().apply {
//                    show(supportFragmentManager, con.toString())
//                    addOnPositiveButtonClickListener {
//                        //dd MMM,yyyy
//                        view.setText(
//                            SimpleDateFormat(format, Locale.getDefault()).format(
//                                Date(it)
//                            )
//                        )
//                    }
//                }
//        }


        fun dpToPx(dp: Int, con: Context): Int {
            val density: Float = con.getResources()
                .getDisplayMetrics().density
            return Math.round(dp.toFloat() * density)
        }

        fun hideUnhideVideoOptions(flag: Boolean, view: CustomVideoPlayerBinding) {
            view.ivCompelete.visibility = if (flag) View.VISIBLE else View.INVISIBLE
            view.ivSave.visibility = if (flag) View.VISIBLE else View.INVISIBLE
            view.ivSchedule.visibility = if (flag) View.VISIBLE else View.INVISIBLE
            view.ivFav.visibility = if (flag) View.VISIBLE else View.INVISIBLE
            view.ivDownloding.visibility = if (flag) View.VISIBLE else View.INVISIBLE

        }

        fun getTimeStampHMS(seconds: Int): String {
            return if (seconds / 3600 != 0) "${seconds / 3600}:${(seconds % 3600) / 60}:${seconds % 60}"
            else "${(seconds % 3600) / 60}:${seconds % 60}"
        }

        fun getVideoDuration(millis: Long): String {
            return if (String.format(
                    Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toHours(millis)
                ) == "00"
            ) {
                String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
                )
            } else {
                String.format(
                    Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
                )
            }


        }


        fun showDialog(con: Context, title: String) {
            materialDialog = MaterialAlertDialogBuilder(
                con,
                R.style.MyRounded_MaterialComponents_MaterialAlertDialog
            ).apply {
                setMessage(title)
                setPositiveButton(
                    "Ok"
                ) { dialog, _ ->
                    dialog.dismiss()
                    (con as Activity).onBackPressed()
                }
                show()
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getmonthTitleFormatter(): DateTimeFormatter {
            return DateTimeFormatter.ofPattern("MMMM")
        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun daysOfWeekFromLocale(): Array<DayOfWeek> {
            val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
            var daysOfWeek = DayOfWeek.values()
            // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
            // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
            if (firstDayOfWeek != DayOfWeek.MONDAY) {
                val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
                val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
                daysOfWeek = rhs + lhs
            }
            return daysOfWeek
        }

        fun updateAppBar(isHomeView: Boolean, view: CommonToolbarBinding, lbl: String? = "") {
            view.ivMainLogo.visibility = if (isHomeView) View.VISIBLE else View.GONE
            view.ivMainNotification.visibility = if (isHomeView) View.VISIBLE else View.GONE
            view.ivDrawer.visibility = if (isHomeView) View.VISIBLE else View.GONE

            view.tvTitle.visibility = if (isHomeView) View.GONE else View.VISIBLE
            view.tvTitle.text = lbl
            view.ivback.visibility = if (isHomeView) View.GONE else View.VISIBLE
            view.ivCorner.visibility = if (isHomeView) View.GONE else View.VISIBLE


        }

        fun isValidEmail(target: CharSequence?): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }

        fun isValidPassword(password: String?, userType: Int): Boolean {
            val pattern: Pattern
            val passwordPattern =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            pattern = Pattern.compile(passwordPattern)
            val matcher: Matcher = pattern.matcher(password)
            return if (userType == 1 || userType == 7) matcher.matches() && password?.length!! > 6 else true
        }


        fun hideUnhideView(rootLay: ConstraintLayout, arrowBtn: ImageView) {
            if (rootLay.visibility == View.VISIBLE) {
                rootLay.visibility = View.GONE
                arrowBtn.setImageResource(R.drawable.ic_arrow_up)
            } else {
                rootLay.visibility = View.VISIBLE
                arrowBtn.setImageResource(R.drawable.ic_arrow_down)
            }
        }

        fun getDatePicker(
            con: Context,
            supportFragmentManager: FragmentManager,
            format: String, view: EditText
        ) {
            MaterialDatePicker.Builder.datePicker().setSelection(Date().time)
                .build().apply {
                    show(supportFragmentManager, con.toString())
                    addOnPositiveButtonClickListener {
                        //dd MMM,yyyy
                        view.setText(
                            SimpleDateFormat(format, Locale.getDefault()).format(
                                Date(it)
                            )
                        )
                    }
                }
        }

        /***return future dates in picker only*/
        fun getFutureDatePicker(
            con: Context,
            supportFragmentManager: FragmentManager,
            format: String, view: EditText
        ) {
            MaterialDatePicker.Builder.datePicker().setSelection(Date().time)
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.from(Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24)).time))
                        .build()
                )
                .build().apply {
                    show(supportFragmentManager, con.toString())
                    addOnPositiveButtonClickListener {
                        //dd MMM,yyyy
                        view.setText(
                            SimpleDateFormat(format, Locale.getDefault()).format(
                                Date(it)
                            )
                        )
                    }
                }
        }


        fun showToast(con: Context, msg: String) {
            Toast.makeText(con, msg, Toast.LENGTH_SHORT).show()
        }

        fun handlerError(context: Context?, throwable: Throwable) {
            Log.w("errorHandler", throwable.message.toString())
            if (context == null)
                return
            else {
                when (throwable) {
                    is ConnectException -> Uitls.showToast(context, "Server Connection Error")
                    is SocketException -> Uitls.showToast(context, "Socket Time Out Exception")
                    is SocketTimeoutException -> Uitls.showToast(
                        context,
                        "Socket Time Out Exception"
                    )
                    is UnknownHostException -> Uitls.showToast(context, "No Internet Connection")
                    is InternalError -> Uitls.showToast(context, "Internal Server Error")
                    else -> Uitls.showToast(context, "Something went wrong!")
                }
            }
        }

        fun TextView.removeLinksUnderline() {
            val spannable = SpannableString(text)
            for (urlSpan in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
                spannable.setSpan(object : URLSpan(urlSpan.url) {
                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }, spannable.getSpanStart(urlSpan), spannable.getSpanEnd(urlSpan), 0)
            }
            text = spannable
        }

        fun showProgree(isShow: Boolean, con: Context) {
            try {
                println("---isShow--${isShow}")
                if (isShow) {
                    getCustomDialog(con)
                } else {
                    dismissDialog()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }


        }


        fun <T> merge(fitstLst: List<T>, secondLst: List<T>): List<T> {
            val returnLst: MutableList<T> = ArrayList()
            returnLst.addAll(fitstLst)
            returnLst.addAll(secondLst)
            return returnLst
        }

        fun getCustomDialog(context: Context): Dialog {
            if (progressDialog != null && progressDialog!!.isShowing) {
                return progressDialog!!
            } else {
                progressDialog = Dialog(context)
                progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                progressDialog!!.setContentView(R.layout.layout_progress_bar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()

            }
            return progressDialog!!
        }

        fun dismissDialog() {
            if (progressDialog != null && progressDialog!!.isShowing)
                progressDialog!!.dismiss()
        }


        fun LoadWebView(context: Context, app_url: String?, webView: WebView) {
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Please wait")
            progressDialog.show()
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.setOnCancelListener { dialog: DialogInterface? -> (context as Activity).finish() }
            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }

                override fun onPageFinished(view: WebView, url: String) {
                    if (progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                }

                override fun onReceivedError(
                    view: WebView,
                    request: WebResourceRequest,
                    error: WebResourceError
                ) {
                    (context as Activity).finish()
                    Toast.makeText(context, "Error$error", Toast.LENGTH_SHORT).show()
                }
            }
            webView.loadUrl(app_url!!)
        }

    }


}