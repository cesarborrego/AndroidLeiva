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
import com.example.exam.databinding.ActivityMainBinding
import com.example.exam.startActivity
import com.example.exam.ui.detail.DetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val mainPresenter = MainPresenter(this, lifecycleScope)

    private val adapter = MediaAdapter {
        mainPresenter.onMediaItemClicked(it)
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter
        mainPresenter.updateItems()

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
        mainPresenter.updateItems(filter)
        return super.onOptionsItemSelected(item)
    }

    override fun setProgressVisible(visible: Boolean) {
        binding.progress.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun updateItems(mediaItems: List<MediaItem>) {
        adapter.items = mediaItems
    }

    override fun navigateToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.EXTRA_ID to id)
    }
}