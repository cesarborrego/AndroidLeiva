package com.example.exam.ui.main

import com.example.exam.data.Filter
import com.example.exam.data.MediaItem
import com.example.exam.data.MediaItemsProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(private val view: View, private val scope: CoroutineScope) {

    interface View {
        fun setProgressVisible(visible: Boolean)
        fun updateItems(mediaItems: List<MediaItem>)
        fun navigateToDetail(id : Int)
    }

    fun updateItems(filter: Filter = Filter.Non) {
        scope.launch(Dispatchers.Main) {
            view.setProgressVisible(true)
            val items = withContext(Dispatchers.IO) { getFilteredItems(filter) }
            view.updateItems(items)
            view.setProgressVisible(false)
        }
    }

    private fun getFilteredItems(filter: Filter): List<MediaItem> {
        return MediaItemsProvider.getItems().let { mediaItems ->
            when (filter) {
                Filter.Non -> mediaItems
                is Filter.ByType -> mediaItems.filter { it.mediaItemType == filter.value }
            }
        }
    }

    fun onMediaItemClicked(mediaItem: MediaItem) {
        view.navigateToDetail(mediaItem.id)
    }


}