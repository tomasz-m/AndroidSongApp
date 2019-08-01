package com.github.tomasz_m.songapp.repository

interface Cache<T> {
    fun hasFreshCache(cashId: String): Boolean
    fun getLatestCache(cashId: String): T?
    fun setCache(cashId: String, data: T)
}