package com.yucatancorp.ecommercemarcosnarvaez.data

import com.yucatancorp.ecommercemarcosnarvaez.utils.ServiceConstants.BEST_MATCH_TAG
import com.yucatancorp.ecommercemarcosnarvaez.utils.ServiceConstants.ENDPOINT
import com.yucatancorp.ecommercemarcosnarvaez.utils.ServiceConstants.KEYWORD_TAG
import com.yucatancorp.ecommercemarcosnarvaez.utils.ServiceConstants.PAGE_TAG
import com.yucatancorp.ecommercemarcosnarvaez.utils.ServiceConstants.SORT_TAG
import com.yucatancorp.ecommercemarcosnarvaez.utils.ServiceConstants.TAG_API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductsService {

    @GET(ENDPOINT)
    suspend fun searchByKeyword(
        @Header(TAG_API_KEY) apiKey: String,
        @Query(KEYWORD_TAG) keyword: String,
        @Query(PAGE_TAG) page: Int,
        @Query(SORT_TAG) sortBy: String = BEST_MATCH_TAG
    ): ResponseDTO
}