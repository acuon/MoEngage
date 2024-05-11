package com.acuon.moengage.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acuon.moengage.databinding.ItemLayoutNewsBinding
import com.acuon.moengage.domain.model.Article
import com.acuon.moengage.utils.extensions.executeWithAction
import com.acuon.moengage.utils.extensions.inflater
import javax.inject.Inject

class NewsAdapter @Inject constructor() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var list: List<Article?>? = null
        set(value) {
            field = value
            notifyItemRangeChanged(0, value?.size ?: 0)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutNewsBinding.inflate(
                parent.inflater(),
                parent,
                false
            )
        )
    }

    private var onArticleClickListener: ((Int, Article?) -> Unit)? = null
    private var onArticleShareListener: ((Int, Article?) -> Unit)? = null

    fun setOnArticleClickListener(listener: (Int, Article?) -> Unit) {
        onArticleClickListener = listener
    }

    fun setOnArticleShareListener(listener: (Int, Article?) -> Unit) {
        onArticleShareListener = listener
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, list?.get(position))
    }

    inner class ViewHolder(private val binding: ItemLayoutNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, article: Article?) {
            binding.executeWithAction { item = article }
            binding.root.setOnClickListener { onArticleClickListener?.invoke(position, article) }
            binding.ivShare.setOnClickListener { onArticleShareListener?.invoke(position, article) }
        }

    }
}