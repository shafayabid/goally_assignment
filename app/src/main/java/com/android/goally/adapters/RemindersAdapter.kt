package com.android.goally.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.android.goally.data.db.entities.reminder.Reminder
import com.android.goally.databinding.ReminderItemBinding
import com.android.goally.listener.ReminderClickListener
import com.android.goally.util.setSafeOnClickListener
import com.bumptech.glide.Glide

class RemindersAdapter(
    private val context: Context,
    private val clickListener: ReminderClickListener
) : PagerAdapter() {

    private val mImageUrlList = mutableListOf<Reminder>()
    private val viewMap = mutableMapOf<Int, View>()

    override fun getCount(): Int {
        return mImageUrlList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val binding = ReminderItemBinding.inflate(inflater, container, false)

        val imageUrl = mImageUrlList[position].visualAidUrl

        // Load image using Glide or any image loading library
        Glide.with(context)
            .load(imageUrl)
            .into(binding.ivReminderItem)

        binding.tvReminderItem.text = mImageUrlList[position].name

        binding.ivReminderItem.setSafeOnClickListener {
            clickListener.onReminderClick(mImageUrlList[position], position)
        }

        container.addView(binding.root)
        viewMap[position] = binding.root
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        viewMap.remove(position)
    }

    fun addItem(imageUrl: MutableList<Reminder>) {
        mImageUrlList.clear()
        mImageUrlList.addAll(imageUrl)
        notifyDataSetChanged()
    }

    fun getViewAt(position: Int): View? {
        return if (position in 0 until mImageUrlList.size) {
            viewMap[position] // Provide the corresponding view for the position
        } else null
    }

}