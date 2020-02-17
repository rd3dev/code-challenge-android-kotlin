package com.arctouch.codechallenge.data.impl

import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre

object CacheImpl : Cache {

    private var genres = listOf<Genre>()

    override fun getGenres(): List<Genre> = genres

    override fun cacheGenres(genres: List<Genre>) {
        CacheImpl.genres = genres
    }
}


