package ru.kszorin.seaworldkotlin.di.modules

import dagger.Module
import dagger.Provides
import ru.kszorin.seaworldkotlin.db.SeaWorldDb
import javax.inject.Singleton

/**
 * Created on 03.04.2018.
 */
@Module
class DbModule(val database: SeaWorldDb) {

    @Provides
    @Singleton
    fun provideDatabase(): SeaWorldDb {
        return database
    }
}