package com.example.exam.data

data class MediaItem(val id : Int, val title: String, val url: String, val mediaItemType: MediaItemType) {

    enum class MediaItemType {
        PHOTO,
        VIDEO
    }
}