package com.rahim.data.repository.base

import saman.zamani.persiandate.PersianDate
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor() : BaseRepository {
    private val persianData = PersianDate()
    private val currentTimeDay = persianData.shDay
    private val currentTimeMonth = persianData.shMonth
    private val currentTimeYer = persianData.shYear

    override fun getCurrentTime(): List<Int> = listOf(currentTimeYer,currentTimeMonth,currentTimeDay)

}