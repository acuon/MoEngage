package com.acuon.moengage.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.acuon.moengage.R
import com.acuon.moengage.BR
import com.acuon.moengage.common.Constants
import com.acuon.moengage.common.ResultOf
import com.acuon.moengage.databinding.FragmentHomeBinding
import com.acuon.moengage.ui.home.adapter.NewsAdapter
import com.acuon.moengage.ui.home.viewmodel.HomeViewModel
import com.acuon.moengage.utils.extensions.addDecoration
import com.acuon.moengage.utils.extensions.createDecorator
import com.acuon.moengage.utils.extensions.executeWithAction
import com.acuon.moengage.utils.shareArticle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        enum class SORT(val value: String) {
            ASCENDING("Newest"),
            DESCENDING("Oldest")
        }
    }

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.setVariable(BR.vm, viewModel)
        return binding.root
    }

    private val sortAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu_item,
            listOf(SORT.ASCENDING.value, SORT.DESCENDING.value)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        bindViewModel()
    }

    private fun setupView() {
        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@HomeFragment.newsAdapter
            addDecoration(createDecorator(top = 8, bottom = 8, left = 0, right = 0))
        }
        newsAdapter.setOnArticleClickListener { _, article ->
            findNavController().navigate(
                R.id.action_homeFragment_to_articleFragment,
                bundleOf(
                    Constants.Arguments.ARTICLE_URL to article?.url,
                    Constants.Arguments.ARTICLE_TITLE to article?.title
                )
            )
        }
        newsAdapter.setOnArticleShareListener { _, article ->
            requireContext().shareArticle(article?.url)
        }
        binding.spinnerSort.apply {
            this.setAdapter(sortAdapter)
            this.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, pos, _ ->
                    val selectedItem = sortAdapter.getItem(pos)
                    val sort = when (selectedItem) {
                        SORT.ASCENDING.value -> SORT.ASCENDING
                        SORT.DESCENDING.value -> SORT.DESCENDING
                        else -> SORT.ASCENDING
                    }
                    viewModel.sortArticles(sort, this@HomeFragment.newsAdapter.list)
                    this.setText(selectedItem, false)
                }
        }
    }

    private fun bindViewModel() {
        viewModel.articleUiState.observe(viewLifecycleOwner) {
            if (it is ResultOf.Success) {
                newsAdapter.list = it.data
            }
            binding.executeWithAction { uiState = ArticlesUiState(it) }
        }
    }

}