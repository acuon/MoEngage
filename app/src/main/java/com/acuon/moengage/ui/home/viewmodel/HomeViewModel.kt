package com.acuon.moengage.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.acuon.moengage.common.BaseViewModel
import com.acuon.moengage.common.Constants
import com.acuon.moengage.common.ResultOf
import com.acuon.moengage.domain.model.Article
import com.acuon.moengage.domain.use_case.GetArticlesUseCase
import com.acuon.moengage.ui.home.HomeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articlesUseCase: GetArticlesUseCase
) : BaseViewModel() {

    init {
        getArticles()
    }

    private val _articleUiState = MutableLiveData<ResultOf<List<Article?>?>>(ResultOf.Loading())
    val articleUiState: LiveData<ResultOf<List<Article?>?>>
        get() = _articleUiState

    private fun getArticles() {
        execute {
            articlesUseCase().map {
                _articleUiState.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun sortArticles(sortBy: HomeFragment.Companion.SORT, articles: List<Article?>?) {
        execute {
            _articleUiState.value = ResultOf.Loading()
            kotlin.runCatching {
                val comparator = Comparator<Article?> { article1, article2 ->
                    val dateFormat = SimpleDateFormat(Constants.DateFormats.SERVER_FORMAT)
                    val date1 = article1?.publishedAt?.let { dateFormat.parse(it) }
                    val date2 = article2?.publishedAt?.let { dateFormat.parse(it) }

                    when (sortBy) {
                        HomeFragment.Companion.SORT.ASCENDING -> date1?.compareTo(date2)!!
                        HomeFragment.Companion.SORT.DESCENDING -> date2?.compareTo(date1)!!
                    }
                }
                articles?.sortedWith(comparator)
            }.onSuccess {
                _articleUiState.value = ResultOf.Success(it)
            }.onFailure {
                _articleUiState.value =
                    ResultOf.Error(it.localizedMessage ?: "Something went wrong")
            }
        }
    }

}