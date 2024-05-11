package com.acuon.moengage.data.repository

import com.acuon.moengage.common.Constants
import com.acuon.moengage.data.remote.RequestHandler
import com.acuon.moengage.domain.model.NewsResponseDTO
import com.acuon.moengage.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val requestHandler: RequestHandler) :
    MainRepository {
    override suspend fun getNewsArticles(): NewsResponseDTO? {
        return requestHandler.requestGet<NewsResponseDTO>(Constants.URL)
    }
}