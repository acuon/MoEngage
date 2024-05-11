package com.acuon.moengage.ui.article

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.acuon.moengage.BR
import com.acuon.moengage.R
import com.acuon.moengage.common.Constants
import com.acuon.moengage.databinding.FragmentArticleBinding
import com.acuon.moengage.ui.article.viewmodel.ArticleViewModel
import com.acuon.moengage.utils.shareArticle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val viewModel: ArticleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false)
        binding.setVariable(BR.vm, viewModel)
        return binding.root
    }

    private val articleUrl by lazy { arguments?.getString(Constants.Arguments.ARTICLE_URL) }
    private val articleTitle by lazy { arguments?.getString(Constants.Arguments.ARTICLE_TITLE) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadWebViewWithUrl(articleUrl)
        binding.header.apply {
            tvHeading.text = articleTitle
            ivBack.setOnClickListener { findNavController().popBackStack() }
            ivShare.setOnClickListener {
                context?.shareArticle(articleUrl)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebViewWithUrl(url: String?) {
        binding.apply {
            webViewArticle.settings.apply {
                javaScriptEnabled = true
                builtInZoomControls = true
                displayZoomControls = false
                setSupportZoom(true)
            }

            webViewArticle.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url!!)
                    return false
                }
            }
            url?.let { webViewArticle.loadUrl(it) }
        }
    }
}