package com.ktpractice.flow.main.view

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ktpractice.R
import com.ktpractice.flow.main.view.PersonListAdapter.ViewHolder
import com.ktpractice.model.Person
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.StringBuilder

class PersonListAdapter(val mCtx: Context) :
    PagedListAdapter<Person, ViewHolder>(Person.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemLayout = LayoutInflater.from(mCtx).inflate(R.layout.list_item_person, parent, false)

        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            val url = if (!TextUtils.isEmpty(item?.avatar)) item?.avatar else item?.url
            val type = item?.type

            holder.mClPersonContentLayout.visibility = INVISIBLE
            holder.mIvBanner.visibility = GONE
            if (TextUtils.equals(type, "employee")) {
                holder.mClPersonContentLayout.visibility = VISIBLE

                Glide.with(mCtx).load(url).centerCrop().into(holder.mCivPersonAvatar)
                holder.mTvPersonName.text = item?.name
                holder.mTvPosition.text = item?.position
                holder.mTvExpertise.text = item?.expertise.let {
                    val strBuilder = StringBuilder("")

                    for (item in it!!) {
                        strBuilder
                            .append(item)
                            .append(",")
                    }
                    strBuilder
                        .deleteCharAt(strBuilder.length - 1)
                        .toString()
                }
            } else if (TextUtils.equals(type, "banner")) {
                holder.mIvBanner.visibility = VISIBLE
                Glide.with(mCtx).load(url).into(holder.mIvBanner)
            }
        }
    }

    class ViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        val mClPersonContentLayout: ConstraintLayout
        val mCivPersonAvatar: CircleImageView
        val mTvPersonName: TextView
        val mTvPosition: TextView
        val mTvExpertise: TextView

        val mIvBanner: ImageView

        init {
            mClPersonContentLayout = layout.findViewById(R.id.cl_person_content_layout)
            mCivPersonAvatar = layout.findViewById(R.id.civ_person_avatar)
            mTvPersonName = layout.findViewById(R.id.tv_name)
            mTvPosition = layout.findViewById(R.id.tv_position)
            mTvExpertise = layout.findViewById(R.id.tv_expertise)
            mIvBanner = layout.findViewById(R.id.iv_banner)
        }
    }
}