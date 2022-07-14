package com.example.exam.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.exam.R
import com.example.exam.data.Filter
import com.example.exam.data.MediaItem
import com.example.exam.databinding.ActivityMainBinding
import com.example.exam.observe
import com.example.exam.startActivity
import com.example.exam.ui.detail.DetailActivity
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity() {

    private val viewMainModel: MainViewModel by viewModel()

    private val adapter by lazy {
        MediaAdapter {
            viewMainModel.onMediaItemClicked(it)
        }
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter

        with(viewMainModel) {
            updateItems()

            observe(progressVisible) {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            }

            observe(mediaItems) {
                adapter.items = it
            }

            observe(navigateToDetail) {
                startActivity<DetailActivity>(DetailActivity.EXTRA_ID to it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter = when (item.itemId) {
            R.id.filter_photos -> Filter.ByType(MediaItem.MediaItemType.PHOTO)
            R.id.filter_videos -> Filter.ByType(MediaItem.MediaItemType.VIDEO)
            else -> Filter.Non
        }
        viewMainModel.updateItems(filter)
        return super.onOptionsItemSelected(item)
    }
}