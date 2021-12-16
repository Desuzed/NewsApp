package com.desuzed.newsapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.desuzed.newsapp.App
import com.desuzed.newsapp.R
import com.desuzed.newsapp.databinding.FragmentListNewsBinding
import com.desuzed.newsapp.model.Article
import com.desuzed.newsapp.model.vm.NewsViewModel
import com.desuzed.newsapp.model.vm.ViewModelFactory
import com.desuzed.newsapp.view.adapters.ArticleListAdapter
import com.desuzed.newsapp.view.adapters.OnItemClickListener

class ListNewsFragment : Fragment(), OnItemClickListener {
    private lateinit var fragmentListNewsBinding: FragmentListNewsBinding
    private lateinit var rvListArticles: RecyclerView
    private val articlesAdapter: ArticleListAdapter by lazy { ArticleListAdapter(this) }
    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelFactory(App.instance.getRepo())
        )
            .get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentListNewsBinding = FragmentListNewsBinding.inflate(inflater, container, false)
        return fragmentListNewsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        observe()
    }
//todo remove observers
    private fun observe() {
        newsViewModel.observe(viewLifecycleOwner, {
            articlesAdapter.submitList(it.articles)
        })
    }

    private fun bind() {
        rvListArticles = fragmentListNewsBinding.rvListArticles
        rvListArticles.adapter = articlesAdapter

    }

    override fun onClick(article: Article) {
        val bundle = bundleOf(DetailedContentFragment.ARTICLE to article)
        navigate(R.id.action_listNewsFragment_to_detailedContentFragment, bundle)
        Log.i("TAG", "onClick: $article ")
    }



}