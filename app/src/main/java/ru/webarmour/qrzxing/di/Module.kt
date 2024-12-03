package ru.webarmour.qrzxing.di

import android.app.Application
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.webarmour.qrzxing.data.ItemsDao
import ru.webarmour.qrzxing.data.MainDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {


    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
    ): MainDatabase {
        return MainDatabase.getInstance(app)
    }


    @Provides
    @Singleton
    fun provideFavouriteCitiesDao(database: MainDatabase): ItemsDao {
        return database.getDao()
    }
}