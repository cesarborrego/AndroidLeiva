package com.example.exam.ui.detail

import android.os.Bundle
import android.view.View
import com.example.exam.databinding.ActivityDetailBinding
import com.example.exam.loadUrl
import com.example.exam.observe
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : ScopeActivity() {
    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_ID = "DetailActivity:id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        with(detailViewModel) {
            onCreate(id)

            observe(title) {
                supportActionBar?.title = it
            }

            observe(detailImage) {
                binding.detailImage.loadUrl(it)
            }

            observe(detailVideoIndicator) {
                binding.detailVideoIndicator.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }
}