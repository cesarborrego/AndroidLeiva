package com.example.exam.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.exam.databinding.ActivityDetailBinding
import com.example.exam.loadUrl

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_ID = "DetailActivity:id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        detailViewModel = ViewModelProvider(this).get()
        detailViewModel.onCreate(id)

        detailViewModel.title.observe(this) {
            supportActionBar?.title = it
        }

        detailViewModel.detailImage.observe(this) {
            binding.detailImage.loadUrl(it)
        }

        detailViewModel.detailVideoIndicator.observe(this) {
            binding.detailVideoIndicator.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}