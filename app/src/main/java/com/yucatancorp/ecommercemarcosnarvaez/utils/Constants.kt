package com.yucatancorp.ecommercemarcosnarvaez.utils

import retrofit2.http.Header
import retrofit2.http.Query

object ServiceConstants {
    const val API_KEY = "fce0e15738msh6a87c0c9db9505cp14b74fjsn54bc768f3bc7"
    const val BASE_URL = "https://axesso-walmart-data-service.p.rapidapi.com"
    const val ENDPOINT = "wlm/walmart-search-by-keyword"
    const val TAG_API_KEY = "x-rapidapi-key"
    const val KEYWORD_TAG = "keyword"
    const val PAGE_TAG = "page"
    const val SORT_TAG = "sortBy"
    const val BEST_MATCH_TAG = "best_match"
}

