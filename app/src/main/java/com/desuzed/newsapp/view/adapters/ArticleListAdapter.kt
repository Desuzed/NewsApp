package com.desuzed.newsapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desuzed.newsapp.databinding.RvItemArticleListBinding
import com.desuzed.newsapp.model.Article

class ArticleListAdapter(
    private val onItemClickListener: OnItemClickListener,
) :
    ListAdapter<Article, ArticleListAdapter.ArticleViewHolder>(ArticleComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        return ArticleViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int
    ) {
        val current = getItem(position)
        holder.bind(current, onItemClickListener)
    }

    class ArticleViewHolder(binding: RvItemArticleListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvTitle: TextView = binding.tvTitle
        private val tvDescription: TextView = binding.tvDescription
        private val rvNewsImage: ImageView = binding.rvNewsImage


        fun bind(current: Article, onItemClickListener: OnItemClickListener) {
            tvTitle.text = current.title
            tvDescription.text = current.description
            Glide
                .with(itemView.context)
                .load(current.urlToImage)
                .into(rvNewsImage)
            itemView.setOnClickListener {
                onItemClickListener.onClick(current)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ArticleViewHolder {
                return ArticleViewHolder(
                    RvItemArticleListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    }

    class ArticleComparator : DiffUtil.ItemCallback<Article>() {
        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem == newItem
        }
    }

}

interface OnItemClickListener {
    fun onClick(article: Article)
}