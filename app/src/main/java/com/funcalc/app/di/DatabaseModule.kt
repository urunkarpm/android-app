package com.funcalc.app.di

import android.content.Context
import com.funcalc.app.data.local.dao.AchievementDao
import com.funcalc.app.data.local.dao.FunFactDao
import com.funcalc.app.data.local.database.FunCalcDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FunCalcDatabase {
        return FunCalcDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFunFactDao(database: FunCalcDatabase): FunFactDao {
        return database.funFactDao()
    }

    @Provides
    @Singleton
    fun provideAchievementDao(database: FunCalcDatabase): AchievementDao {
        return database.achievementDao()
    }
}
