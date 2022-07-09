package com.example.exam.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.exam.data.MediaItem
import com.example.exam.data.MediaItemsProvider
import com.example.exam.databinding.ActivityDetailBinding
import com.example.exam.loadUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_ID = "DetailActivity:id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        lifecycleScope.launch {
            val mediaItems = withContext(Dispatchers.IO) { MediaItemsProvider.getItems() }
            val item = mediaItems.firstOrNull { it.id == id }

            item?.let {
                supportActionBar?.title = it.title
                with(binding) {
                    detailImage.loadUrl(it.url)
                    detailVideoIndicator.visibility = when (it.mediaItemType) {
                        MediaItem.MediaItemType.PHOTO -> View.GONE
                        MediaItem.MediaItemType.VIDEO -> View.VISIBLE
                    }
                }
            }
        }
    }
}