package com.example.exam.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.exam.R
import com.example.exam.data.Filter
import com.example.exam.data.MediaItem
import com.example.exam.data.MediaItemsProvider
import com.example.exam.databinding.ActivityMainBinding
import com.example.exam.startActivity
import com.example.exam.ui.detail.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val adapter = MediaAdapter {
        startActivity<DetailActivity>(DetailActivity.EXTRA_ID to it.id)
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter
        updateItems()

    }

    fun updateItems(filter: Filter = Filter.Non) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.progress.visibility = View.VISIBLE
            adapter.items = withContext(Dispatchers.IO) {
                getFilteredItems(filter)
            }
            binding.progress.visibility = View.GONE
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter = when (item.itemId) {
            R.id.filter_photos -> Filter.ByType(MediaItem.MediaItemType.PHOTO)
            R.id.filter_videos -> Filter.ByType(MediaItem.MediaItemType.VIDEO)
            else -> Filter.Non
        }
        updateItems(filter)
        return super.onOptionsItemSelected(item)
    }
}