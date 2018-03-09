package ru.kszorin.seaworldkotlin

import android.app.Application
import ru.kszorin.seaworldkotlin.di.components.DaggerModelsComponent
import ru.kszorin.seaworldkotlin.di.components.ModelsComponent
import ru.kszorin.seaworldkotlin.di.modules.WorldModule
import ru.kszorin.seaworldkotlin.models.World

/**
 * Created on 04.03.2018.
 */
class SeaWorldApp : Application() {

    override fun onCreate() {
        super.onCreate()
        modelsComponent = buildModelsComponent()
    }

    fun buildModelsComponent(): ModelsComponent {
        return DaggerModelsComponent
                .builder()
                .worldModule(WorldModule(World.Companion.FIELD_SIZE_X, World.Companion.FIELD_SIZE_Y))
                .build()
    }

    companion object {
        var modelsComponent: ModelsComponent? = null
    }
}