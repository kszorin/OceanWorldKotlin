package ru.kszorin.seaworldkotlin.di.components

import dagger.Component
import ru.kszorin.seaworldkotlin.di.models.TestWorldModule
import ru.kszorin.seaworldkotlin.di.modules.WorldModule
import ru.kszorin.seaworldkotlin.models.Animal
import ru.kszorin.seaworldkotlin.models.World
import ru.kszorin.seaworldkotlin.models.behaviour.PeriodicReproduction
import javax.inject.Singleton

/**
 * Created on 11.03.2018.
 */
@Component(modules = arrayOf(TestWorldModule::class))
@Singleton
interface TestModelsComponent : ModelsComponent {

}