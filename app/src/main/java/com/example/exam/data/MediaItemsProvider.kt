package com.example.exam.data

import androidx.annotation.WorkerThread

object MediaItemsProvider {

    @WorkerThread
    fun getItems(): List<MediaItem> {
        Thread.sleep(2000)
        return (1..10).map {
            MediaItem(
                it,
                "Title $it",
                "https://placekitten.com/200/200?image=$it",
                if (it % 3 == 0) MediaItem.MediaItemType.VIDEO else MediaItem.MediaItemType.PHOTO
            )
        }
    }
}