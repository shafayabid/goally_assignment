package com.android.goally.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.goally.BaseActivity
import com.android.goally.R
import com.android.goally.customviews.ScalePageTransformer
import com.android.goally.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reminderName = intent.getStringExtra("reminderName") ?: "Reminder Task"

        setupObservers(reminderName)
        setupViews()

    }

    private fun setupViews() {
        binding.run {
            tvNow.text = getString(R.string.now)
            tvStart.text = getString(R.string.starts)
            tvNowTime.text = getCurrentTime()
            tvStartTime.text = getString(R.string.static_time2)
            tvTimeBar.text = getString(R.string.static_time)
            tvOkay.text = getString(R.string.okay)
        }

    }

    private fun setupObservers(name: String) {
        generalViewModel.getReminderByName(name)?.observe(this) {

            binding.tvTitle.text = it.name
            Glide.with(this).load(it.visualAidUrl).into(binding.ivReminderDetail)

        }
    }

    private fun getCurrentTime(): String {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(currentTime)
    }

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, DetailActivity::class.java)
        }
    }

}