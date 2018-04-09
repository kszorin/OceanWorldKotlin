package ru.kszorin.seaworldkotlin

import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.stetho.Stetho
import ru.kszorin.seaworldkotlin.db.SeaWorldRoomDatabase
import ru.kszorin.seaworldkotlin.di.components.DaggerDbComponent
import ru.kszorin.seaworldkotlin.di.components.DaggerModelsComponent
import ru.kszorin.seaworldkotlin.di.components.DbComponent
import ru.kszorin.seaworldkotlin.di.components.ModelsComponent
import ru.kszorin.seaworldkotlin.di.modules.DbModule
import ru.kszorin.seaworldkotlin.di.modules.WorldModule
import ru.kszorin.seaworldkotlin.entities.World

/**
 * Created on 04.03.2018.
 */
class SeaWorldApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //DB diagnostic
        Stetho.initializeWithDefaults(this)

        //dagger2
        modelsComponent = buildModelsComponent()
        dbComponent = buildDbComponent()
    }

    private fun buildModelsComponent(): ModelsComponent {
        return DaggerModelsComponent
                .builder()
                .worldModule(WorldModule(World.Companion.FIELD_SIZE_X, World.Companion.FIELD_SIZE_Y))
                .build()
    }

    private fun buildDbComponent(): DbComponent {
        return DaggerDbComponent
                .builder()
                .dbModule(DbModule(Room.databaseBuilder(
                        applicationContext,
                        SeaWorldRoomDatabase::class.java,
                        "seaworld-roomDatabase")
                        .build()))
                .build()
    }

    companion object {
        var modelsComponent: ModelsComponent? = null

        var dbComponent: DbComponent? = null
    }
}