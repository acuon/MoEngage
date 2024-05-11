package com.acuon.moengage.domain.repository

import com.acuon.moengage.domain.model.NewsResponseDTO


interface MainRepository {

    suspend fun getNewsArticles(): NewsResponseDTO?

}
