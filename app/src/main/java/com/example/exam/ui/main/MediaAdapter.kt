package com.example.exam.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.R
import com.example.exam.data.MediaItem
import com.example.exam.databinding.ViewMediaItemBinding
import com.example.exam.inflate
import com.example.exam.loadUrl
import kotlin.properties.Delegates

typealias clickLambda = (mediaItem: MediaItem) -> Unit

class MediaAdapter(
    items: List<MediaItem> = emptyList(),
    private val click: clickLambda
) :
    RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    var items: List<MediaItem> by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_media_item)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { click(item) }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewMediaItemBinding.bind(view)

        fun bind(mediaItem: MediaItem) {
            with(binding) {
                title.text = mediaItem.title
                image.loadUrl(mediaItem.url)
                videoIndicator.visibility = when (mediaItem.mediaItemType) {
                    MediaItem.MediaItemType.PHOTO -> View.GONE
                    MediaItem.MediaItemType.VIDEO -> View.VISIBLE
                }
            }
        }
    }
}