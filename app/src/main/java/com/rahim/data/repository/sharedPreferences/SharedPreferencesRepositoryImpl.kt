package com.rahim.data.repository.sharedPreferences

import com.rahim.data.sharedPreferences.SharedPreferencesCustom
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(private val sharedPreferencesCustom: SharedPreferencesCustom) :
    SharedPreferencesRepository {
    override fun saveShowWelcome(isShow: Boolean) {
        sharedPreferencesCustom.saveWelcomePage(isShow)
    }

    override fun isShowWelcomeScreen(): Boolean = sharedPreferencesCustom.isShowWelcome()

}