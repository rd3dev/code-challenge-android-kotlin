package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.model.Genre

interface Cache {
    fun cacheGenres(genres: List<Genre>)
    fun getGenres(): List<Genre>

}