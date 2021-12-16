package com.desuzed.newsapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.desuzed.newsapp.App
import com.desuzed.newsapp.R
import com.desuzed.newsapp.databinding.FragmentListNewsBinding
import com.desuzed.newsapp.model.Article
import com.desuzed.newsapp.model.News
import com.desuzed.newsapp.model.vm.NewsViewModel
import com.desuzed.newsapp.model.vm.ViewModelFactory
import com.desuzed.newsapp.view.adapters.ArticleListAdapter
import com.desuzed.newsapp.view.adapters.OnItemClickListener

class ListNewsFragment : Fragment(), OnItemClickListener {
    private lateinit var fragmentListNewsBinding: FragmentListNewsBinding
    private lateinit var rvListArticles: RecyclerView
    private lateinit var etQuery: EditText
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
        initEditText()
    }

    private fun observe() {
        newsViewModel.observeNews(viewLifecycleOwner, newsObserver)
        newsViewModel.observeError(viewLifecycleOwner, errorMessageObserver)
    }

    private val errorMessageObserver = Observer<String> {
       toast(it)
    }

    private val newsObserver = Observer<News> {
        articlesAdapter.submitList(it.articles)
    }

    private fun toast (message : String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun initEditText() {
        etQuery.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            val text = etQuery.text.toString()
            if (text.isEmpty()) {
                //   sharedViewModel.onError(ActionResultProvider.EMPTY_FIELD)
                hideKeyboard()
                return@OnEditorActionListener false
            }
            if (actionId != EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                //  sharedViewModel.onError(ActionResultProvider.FAIL)
                return@OnEditorActionListener false
            } else {
                newsViewModel.fetchDataFromApi(text)
                hideKeyboard()
                return@OnEditorActionListener true
            }

        })
    }

    private fun hideKeyboard() {
        val activity = requireActivity()
        if (activity.currentFocus == null) {
            return
        }
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    private fun bind() {
        rvListArticles = fragmentListNewsBinding.rvListArticles
        etQuery = fragmentListNewsBinding.etQuery
        rvListArticles.adapter = articlesAdapter
    }

    override fun onClick(article: Article) {
        val bundle = bundleOf(DetailedContentFragment.ARTICLE to article)
        navigate(R.id.action_listNewsFragment_to_detailedContentFragment, bundle)
    }



}