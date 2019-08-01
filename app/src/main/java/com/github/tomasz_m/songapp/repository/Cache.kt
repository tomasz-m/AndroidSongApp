package com.github.tomasz_m.songapp.repository

interface Cache<T> {
    fun hasFreshCash(cashId: String): Boolean
    fun getLatestCash(cashId: String): T?
    fun setCash(cashId: String, data: T)
}