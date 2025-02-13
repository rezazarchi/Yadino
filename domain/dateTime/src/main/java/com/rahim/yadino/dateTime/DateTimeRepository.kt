package com.rahim.yadino.dateTime

import com.rahim.yadino.model.TimeDate
import kotlinx.coroutines.flow.Flow

interface DateTimeRepository {
  val currentTimeDay: Int
  val currentTimeMonth: Int
  val currentTimeYear: Int
  suspend fun addTime()
  suspend fun calculateToday()
  fun getTimes(): Flow<List<TimeDate>>
  suspend fun getTimesMonth(yearNumber: Int, monthNumber: Int): List<TimeDate>
  fun getCurrentNameDay(date: String, format: String): String
  suspend fun updateDayToToday(day: Int, year: Int, month: Int)
}
