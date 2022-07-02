package com.mobulous.viewPagers.dashboardViewPager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.activity.video.AboutVideoActivity
import com.mobulous.helper.*
import com.mobulous.pojo.dashboard.CommonChaptersItem

class BannerVPAdptr(
    private val context: Context,
    private val images: ArrayList<CommonChaptersItem?>
) :
    PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null


    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = layoutInflater!!.inflate(R.layout.home_banner_lay, null)
        val imageView = view.findViewById<View>(R.id.bannerImg) as ImageView
        view.findViewById<ImageView>(R.id.imageView10).visibility =
            if (Constants.isUserLogined) View.GONE else View.VISIBLE
        view.findViewById<TextView>(R.id.durationlbl).text =
            Uitls.getTimeStampHMS(images[position]?.duration?.toInt() ?: 0)
        view.findViewById<TextView>(R.id.textView38).text = images[position]?.title
        imageView.loadNormalPhoto_Dimens400(images[position]?.previewImage ?: "")

        imageView.setOnClickListener {
            if (PrefUtils.with(context).getString(Enums.isLogin.toString(), "false") == "true") {
                context.startActivity(
                    Intent(context, AboutVideoActivity::class.java).putExtra(
                        Constants.Data, images[position]?.id.toString()
                    )
                )
            } else {
                context.startActivity(
                    Intent(context, BaseActivity::class.java).putExtra(
                        Constants.From, Enums.NORMAL.toString()
                    )
                )
            }

        }
        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }

}