package com.uti.posttest5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StoryAdapter(private val storyList: List<Story>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    class StoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val storyProfile: ImageView = view.findViewById(R.id.iv_story_profile)
        val storyName: TextView = view.findViewById(R.id.tv_story_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = storyList[position]
        holder.storyName.text = story.name
        Glide.with(holder.itemView.context)
            .load(story.imageResId)
            .circleCrop()
            .into(holder.storyProfile)
    }

    override fun getItemCount() = storyList.size
}
