package com.example.exam.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Filter
import com.example.exam.data.MediaItem
import com.example.exam.data.MediaItemsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _progressVisible = MutableLiveData<Boolean>()
    val progressVisible: LiveData<Boolean> get() = _progressVisible

    private val _mediaItems = MutableLiveData<List<MediaItem>>()
    val mediaItems: LiveData<List<MediaItem>> get() = _mediaItems

    private val _navigateToDetail = MutableLiveData<Int>()
    val navigateToDetail: LiveData<Int> get() = _navigateToDetail

    fun updateItems(filter: Filter = Filter.Non) {
        viewModelScope.launch(Dispatchers.Main) {
            _progressVisible.value = true
            _mediaItems.value = withContext(Dispatchers.IO) { getFilteredItems(filter) }
            _progressVisible.value = false
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
        _navigateToDetail.value = mediaItem.id
    }


}