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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.desuzed.newsapp.App
import com.desuzed.newsapp.R
import com.desuzed.newsapp.data.ErrorProvider
import com.desuzed.newsapp.data.ErrorProviderImpl
import com.desuzed.newsapp.databinding.FragmentListNewsBinding
import com.desuzed.newsapp.model.Article
import com.desuzed.newsapp.model.News
import com.desuzed.newsapp.model.vm.NewsViewModel
import com.desuzed.newsapp.model.vm.UiViewModel
import com.desuzed.newsapp.model.vm.ViewModelFactory
import com.desuzed.newsapp.model.vm.liveData.UiState
import com.desuzed.newsapp.view.adapters.ArticleListAdapter
import com.desuzed.newsapp.view.adapters.OnItemClickListener

class ListNewsFragment : Fragment(), OnItemClickListener {
    private lateinit var fragmentListNewsBinding: FragmentListNewsBinding
    private lateinit var rvListArticles: RecyclerView
    private lateinit var etQuery: EditText
    private lateinit var tvNoData: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val articlesAdapter: ArticleListAdapter by lazy { ArticleListAdapter(this) }
    private val uiViewModel by viewModels<UiViewModel>()
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
        uiViewModel.observe(viewLifecycleOwner, uiStateObserver)
    }

    private val errorMessageObserver = Observer<String> {
       toast(it)
    }

    private val newsObserver = Observer<News> {
        articlesAdapter.submitList(it.articles)
        uiViewModel.postValue(UiState.Success())
    }

    private fun toast (message : String){
        if (message.isEmpty()) return       //Сделано для того чтобы при возвращении на фрагмент обратно ен показывался тост заново
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        uiViewModel.postValue(UiState.Error())
    }

    private val uiStateObserver = Observer<UiState> {
        when (it){
            is UiState.Success -> {
                toggleRefresher(false)
                toggleUi(true)
            }
            is UiState.Error ->{
                toggleRefresher(false)
                toggleUi(newsViewModel.showNews()!=null)
            }
            is UiState.Loading -> toggleRefresher(true)
            is UiState.NoData -> {
                toggleRefresher(false)
                toggleUi(false)
            }
        }
    }

    private fun toggleRefresher (state : Boolean){
        swipeRefresh.isRefreshing = state
    }

    private fun toggleUi (state: Boolean){
        if (state){
            rvListArticles.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }else {
            rvListArticles.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
        }
    }

    private fun initEditText() {
        etQuery.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            val text = etQuery.text.toString()
            if (text.isEmpty()) {
                toast(ErrorProviderImpl(resources).parseCode(ErrorProvider.EMPTY_QUERY))
                hideKeyboard()
                return@OnEditorActionListener false
            }
            if (actionId != EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                return@OnEditorActionListener false
            } else {
                newsViewModel.fetchDataFromApi(text)
                newsViewModel.postQuery(text)
                uiViewModel.postValue(UiState.Loading())
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
        swipeRefresh = fragmentListNewsBinding.swipeRefresh
        etQuery = fragmentListNewsBinding.etQuery
        tvNoData = fragmentListNewsBinding.tvNoData
        rvListArticles.adapter = articlesAdapter
        swipeRefresh.setOnRefreshListener {
            val query = newsViewModel.showQuery()
            newsViewModel.fetchDataFromApi(query.toString())
            uiViewModel.postValue(UiState.Loading())
        }
    }

    override fun onClick(article: Article) {
        val bundle = bundleOf(DetailedContentFragment.ARTICLE to article)
        navigate(R.id.action_listNewsFragment_to_detailedContentFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        newsViewModel.removeErrorObserver(errorMessageObserver)
        newsViewModel.removeNewsObserver(newsObserver)
        uiViewModel.removeObserver(uiStateObserver)
    }

}