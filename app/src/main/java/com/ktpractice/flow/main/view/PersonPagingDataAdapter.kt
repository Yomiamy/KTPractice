package com.ktpractice.flow.main.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ktpractice.R
import com.ktpractice.flow.profile.view.ProfileActivity
import com.ktpractice.model.Person
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.StringBuilder

class PersonPagingDataAdapter(private val mCtx: Context): PagingDataAdapter<Person, PersonPagingDataAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Person>() {
            // Person details may have changed , but ID is fixed.
            override fun areItemsTheSame(
                oldPerson: Person,
                newPerson: Person
            ) = (oldPerson.id == newPerson.id)

            override fun areContentsTheSame(
                oldPerson: Person,
                newPerson: Person
            ) = oldPerson == newPerson
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemLayout = LayoutInflater.from(mCtx).inflate(R.layout.list_item_person, parent, false)

        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        private val mClPersonContentLayout: ConstraintLayout
        private val mCivPersonAvatar: CircleImageView
        private val mTvPersonName: TextView
        private val mTvPosition: TextView
        private val mTvExpertise: TextView

        private val mIvBanner: ImageView

        init {
            mClPersonContentLayout = layout.findViewById(R.id.cl_person_content_layout)
            mCivPersonAvatar = layout.findViewById(R.id.civ_person_avatar)
            mTvPersonName = layout.findViewById(R.id.tv_name)
            mTvPosition = layout.findViewById(R.id.tv_position)
            mTvExpertise = layout.findViewById(R.id.tv_expertise)
            mIvBanner = layout.findViewById(R.id.iv_banner)
        }

        fun bind(item: Person?) {
            if(item == null) {
                return
            }

            val url = if (!item.avatar.isNullOrEmpty()) item.avatar else item.url
            val type = item.type

            mClPersonContentLayout.visibility = View.INVISIBLE
            mIvBanner.visibility = View.GONE
            if (type == "employee") {
                mClPersonContentLayout.visibility = View.VISIBLE

                Glide.with(mCtx).load(url).centerCrop().into(mCivPersonAvatar)
                mTvPersonName.text = item.name
                mTvPosition.text = item.position
                mTvExpertise.text = item.expertise.let {
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

                mClPersonContentLayout.setOnClickListener {
                    Intent(mCtx, ProfileActivity::class.java).apply {
                        putExtra(ProfileActivity.EXTRA_KEY_PERSON, item)
                    }.run {
                        mCtx.startActivity(this)
                    }
                }
            } else if (type == "banner") {
                mIvBanner.visibility = View.VISIBLE
                Glide.with(mCtx).load(url).into(mIvBanner)
            }
        }
    }
}