package ru.kszorin.seaworldkotlin.di.modules

import dagger.Module
import dagger.Provides
import ru.kszorin.seaworldkotlin.db.SeaWorldRoomDatabase
import javax.inject.Singleton

/**
 * Created on 03.04.2018.
 */
@Module
class DbModule(val roomDatabase: SeaWorldRoomDatabase) {

    @Provides
    @Singleton
    fun provideDatabase(): SeaWorldRoomDatabase {
        return roomDatabase
    }
}