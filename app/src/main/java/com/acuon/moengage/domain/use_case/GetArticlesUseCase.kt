package com.acuon.moengage.domain.use_case

import com.acuon.moengage.common.ResultOf
import com.acuon.moengage.domain.model.Article
import com.acuon.moengage.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<ResultOf<List<Article?>?>> = flow {
        try {
            emit(ResultOf.Loading())
            val response = repository.getNewsArticles()
            emit(ResultOf.Success(response?.articles))
        } catch (e: Exception) {
            emit(ResultOf.Error(e.localizedMessage ?: "Something went wrong"))
            Timber.tag("Response").d(e.toString())
        } catch (e: IOException) {
            emit(ResultOf.Error(e.localizedMessage ?: "Something went wrong"))
        }
    }.flowOn(Dispatchers.IO)
}