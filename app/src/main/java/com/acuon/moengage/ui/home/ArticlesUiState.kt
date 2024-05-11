package com.acuon.moengage.ui.home

import android.content.Context
import com.acuon.moengage.R
import com.acuon.moengage.common.ResultOf
import com.acuon.moengage.domain.model.Article

data class ArticlesUiState(
    private val state: ResultOf<List<Article?>?>
) {

    fun isLoading() = state is ResultOf.Loading

    fun isSuccess() = state is ResultOf.Success

    fun isError() = state is ResultOf.Error

    fun getErrorMessage(context: Context) = if (state is ResultOf.Error) {
        state.message ?: context.getString(R.string.something_went_wrong)
    } else ""
}