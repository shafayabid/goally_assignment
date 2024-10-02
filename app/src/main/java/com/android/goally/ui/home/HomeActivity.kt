package com.android.goally.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.android.goally.BaseActivity
import com.android.goally.R
import com.android.goally.adapters.RemindersAdapter
import com.android.goally.customviews.MultiViewPager
import com.android.goally.customviews.ScalePageTransformer
import com.android.goally.data.db.entities.reminder.Reminder
import com.android.goally.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var viewPager: MultiViewPager
    private val imageUrlList = mutableListOf<Reminder>()
    val remindersAdapter = RemindersAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding.run {
//            tvMessage.text =
//                getString(R.string.your_work_starts_here_add_or_update_your_own_views_according_to_your_assignment)
        }
        val transformer = ScalePageTransformer()
        viewPager = binding.vpReminders
        viewPager.adapter = remindersAdapter
        viewPager.setPageTransformer(true, transformer)
        setUpViewPagerListener()
    }

    private fun setupObservers() {
        //observer goes here
//        generalViewModel.getAuthenticationLive().observe(this) {
//            it?.let {
//                binding.tvEmail.text = getString(R.string.you_are_logged_in_as, it.name)
//            }
//        }

        generalViewModel.getReminderLive().observe(this) {
            it?.let {
                imageUrlList.clear()
                it.forEach {
                    Log.d("REMINDER_OBSERVER", "setupObservers: ${it}")
                    imageUrlList.add(it)
                }
                remindersAdapter.addItem(imageUrlList)
            }
        }
    }

    private fun setUpViewPagerListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                // Loop through all items and toggle visibility of TextView
                for (i in 0 until remindersAdapter.count) {
                    val view = remindersAdapter.getViewAt(i)
                    val textView = view?.findViewById<TextView>(R.id.tv_reminder_item)
                    val imageView = view?.findViewById<ImageView>(R.id.iv_play_btn)
                    textView?.visibility = if (i == position) View.VISIBLE else View.GONE
                    imageView?.visibility = if (i == position) View.VISIBLE else View.GONE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    companion object{
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}