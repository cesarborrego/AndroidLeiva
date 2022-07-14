package com.example.exam.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.MediaItem
import com.example.exam.data.MediaItemsProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val mediaItemProvider: MediaItemsProvider,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _detailImage = MutableLiveData<String>()
    val detailImage: LiveData<String> get() = _detailImage

    private val _detailVideoIndicator = MutableLiveData<Boolean>()
    val detailVideoIndicator: LiveData<Boolean> get() = _detailVideoIndicator


    fun onCreate(id: Int) {
        viewModelScope.launch {
            val mediaItems = withContext(ioDispatcher) { mediaItemProvider.getItems() }
            val item = mediaItems.firstOrNull { it.id == id }

            item?.let {
                _title.value = it.title
                _detailImage.value = it.url
                _detailVideoIndicator.value = it.mediaItemType == MediaItem.MediaItemType.VIDEO
            }
        }
    }
}