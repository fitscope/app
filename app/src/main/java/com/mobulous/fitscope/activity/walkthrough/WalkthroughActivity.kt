package com.mobulous.fitscope.activity.walkthrough

import android.content.Intent
import androidx.viewpager.widget.ViewPager
import com.mobulous.Adapter.WalkthroughAdapter
import com.mobulous.BaseAc
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.databinding.ActivityWalkthroughBinding
import com.mobulous.model.WalkthroughtModel
import java.util.*

class WalkthroughActivity : BaseAc<ActivityWalkthroughBinding>(), ViewPager.OnPageChangeListener {
    private val walkthroughModel = ArrayList<WalkthroughtModel>()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        bin = ActivityWalkthroughBinding.inflate(layoutInflater)
//        setContentView(bin.root)
//        broadcasr = MyReciever()
//        listnr()
//    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (position == binding.tabWalkthrough.tabCount - 1) {
            binding.tvSkipWalkthrough.text = "LET'S BEGIN"

        } else {
            binding.tvSkipWalkthrough.text = "SKIP"

        }

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun getViewBinding(): ActivityWalkthroughBinding =
        ActivityWalkthroughBinding.inflate(layoutInflater)

    override fun initViews() {
        walkthroughModel.add(
            WalkthroughtModel(
                R.drawable.onboarding_test_full,
                "HUNDREDS OF STUDIO\n" + "CLASSES",
                "All Levels Beginner through\n" + "Advanced & Low Impact"
            )
        )
        walkthroughModel.add(
            WalkthroughtModel(
                R.drawable.onboarding2,
                "YOU HAVE A TRAINER\nWHEREVER YOU GO",
                "Download video to watch offline\nanywhere and anytime"
            )
        )
        walkthroughModel.add(
            WalkthroughtModel(
                R.drawable.onboarding3,
                "CLASSES FOR ALL YOUR\nEQUIPMENT",
                "Works with ANY equipment brand"
            )
        )
        walkthroughModel.add(
            WalkthroughtModel(
                R.drawable.onboarding5,
                "GET MORE OUT OF YOUR\n" + "WORKOUTS",
                "Fun & Experienced Trainers + Great\nMusic = Your Best Workout EVER"
            )
        )
        binding.vpWalkthrough.adapter = WalkthroughAdapter(this, walkthroughModel)
        binding.tabWalkthrough.setupWithViewPager(binding.vpWalkthrough, true)

    }

    override fun observers() {

    }

    override fun listnr() {
        binding.tvSkipWalkthrough.setOnClickListener {
            startActivity(Intent(this, BaseActivity::class.java))
            finish()
            //  sendBroadcast(Intent().setAction("com.mobulous.fitscope").putExtra("data", "hello"))

        }
        binding.vpWalkthrough.addOnPageChangeListener(this)
    }

}