package com.uti.posttest5

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostinganAdapter(
    private val onEdit: (Postingan) -> Unit,
    private val onDelete: (Postingan) -> Unit
) : ListAdapter<Postingan, PostinganAdapter.PostinganViewHolder>(POSTINGAN_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostinganViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_postingan, parent, false)
        return PostinganViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostinganViewHolder, position: Int) {
        val postingan = getItem(position)
        holder.bind(postingan, onEdit, onDelete)
    }

    class PostinganViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvUsername: TextView = view.findViewById(R.id.tv_username)
        private val tvCaption: TextView = view.findViewById(R.id.tv_caption)
        private val ivPostImage: ImageView = view.findViewById(R.id.iv_post_image)
        private val ivMore: ImageView = view.findViewById(R.id.iv_more)

        fun bind(postingan: Postingan, onEdit: (Postingan) -> Unit, onDelete: (Postingan) -> Unit) {
            tvUsername.text = postingan.username
            tvCaption.text = postingan.caption

            val imageUri = Uri.parse(postingan.imageUri)
            Glide.with(itemView.context)
                .load(imageUri)
                .into(ivPostImage)

            ivMore.setOnClickListener {
                showPopupMenu(it, postingan, onEdit, onDelete)
            }
        }

        private fun showPopupMenu(
            view: View,
            postingan: Postingan,
            onEdit: (Postingan) -> Unit,
            onDelete: (Postingan) -> Unit
        ) {
            val popup = PopupMenu(view.context, view)
            popup.menuInflater.inflate(R.menu.post_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_edit -> {
                        onEdit(postingan)
                        true
                    }
                    R.id.menu_delete -> {
                        onDelete(postingan)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    fun setData(data: List<Postingan>) {
        submitList(data)
    }

    companion object {
        private val POSTINGAN_COMPARATOR = object : DiffUtil.ItemCallback<Postingan>() {
            override fun areItemsTheSame(oldItem: Postingan, newItem: Postingan): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Postingan, newItem: Postingan): Boolean {
                return oldItem == newItem
            }
        }
    }
}
