package com.desuzed.newsapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.desuzed.newsapp.R
import com.desuzed.newsapp.databinding.FragmentDetailedContentBinding
import com.desuzed.newsapp.model.Article


class DetailedContentFragment : Fragment() {
    private lateinit var binding: FragmentDetailedContentBinding
    private lateinit var ivContent: ImageView
    private lateinit var tvContentTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvContentDescription: TextView
    private lateinit var tvArticleContent: TextView
    private lateinit var tvUrlToContent: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        setContent()
    }

    private fun setContent (){
        val article = arguments?.getSerializable(ARTICLE) as Article
        tvContentTitle.text = article.title
        tvContentDescription.text = article.description
        tvArticleContent.text = article.content
        tvUrlToContent.text = article.url
        tvAuthor.text = article.author
        Glide
            .with(requireContext())
            .load(article.urlToImage)
            .into(ivContent)

        binding.appBarLayout.setOnClickListener{
            val bundle = bundleOf(GalleryFragment.URL_IMAGE to article.urlToImage)
            navigate(R.id.action_detailedContentFragment_to_galleryFragment, bundle)
        }
    }

    private fun bind (){
        ivContent = binding.ivContent
        tvContentTitle = binding.tvContentTitle
        tvContentDescription = binding.tvContentDescription
        tvArticleContent = binding.tvArticleContent
        tvUrlToContent = binding.tvUrlToContent
        tvAuthor = binding.tvAuthor
    }
    companion object {
        const val ARTICLE = "article"
    }
}