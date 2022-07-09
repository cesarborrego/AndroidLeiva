package com.example.exam.data

sealed class Filter {
    object Non : Filter()
    class ByType(val value: MediaItem.MediaItemType) : Filter()
}
