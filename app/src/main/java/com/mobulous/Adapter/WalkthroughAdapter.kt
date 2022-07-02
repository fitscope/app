package com.mobulous.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mobulous.fitscope.R
import com.mobulous.model.WalkthroughtModel
import java.util.*

class WalkthroughAdapter(var con: Context, var lst: ArrayList<WalkthroughtModel>) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater!!.inflate(R.layout.adpater_walkthrough, null)



        when (position) {
            0 -> {
                view.findViewById<ConstraintLayout>(R.id.onboardingLayOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivhide_walkItem)
                    .setImageResource(lst[position].img)
                view.findViewById<TextView>(R.id.tvhideTitle_walkItem).text = lst[position].title
                view.findViewById<TextView>(R.id.tvhideDes_walkItem).text = lst[position].des
                view.findViewById<ConstraintLayout>(R.id.onboardingLayTwoThree).visibility =
                    View.GONE
            }
            1 -> {
                view.findViewById<ConstraintLayout>(R.id.onboardingLayOne).visibility = View.GONE
                view.findViewById<ImageView>(R.id.img2)
                    .setImageResource(lst[position].img)
                view.findViewById<TextView>(R.id.txt1).text = lst[position].title
                view.findViewById<TextView>(R.id.txt2).text = lst[position].des
                view.findViewById<ConstraintLayout>(R.id.onboardingLayTwoThree).visibility =
                    View.VISIBLE
            }
            2 -> {
                view.findViewById<ConstraintLayout>(R.id.onboardingLayOne).visibility = View.GONE
                view.findViewById<ImageView>(R.id.img2)
                    .setImageResource(lst[position].img)
                view.findViewById<TextView>(R.id.txt1).text = lst[position].title
                view.findViewById<TextView>(R.id.txt2).text = lst[position].des
                view.findViewById<ConstraintLayout>(R.id.onboardingLayTwoThree).visibility =
                    View.VISIBLE
            }
            3 -> {
                view.findViewById<ConstraintLayout>(R.id.onboardingLayOne).visibility = View.GONE
                view.findViewById<ImageView>(R.id.img2)
                    .setImageResource(lst[position].img)
                view.findViewById<TextView>(R.id.txt1).text = lst[position].title
                view.findViewById<TextView>(R.id.txt2).text = lst[position].des
                view.findViewById<ConstraintLayout>(R.id.onboardingLayTwoThree).visibility =
                    View.VISIBLE
            }
        }

        val vp = container as ViewPager
        vp.addView(view, 0)

        return view
    }

    override fun getCount(): Int {
        return lst.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}