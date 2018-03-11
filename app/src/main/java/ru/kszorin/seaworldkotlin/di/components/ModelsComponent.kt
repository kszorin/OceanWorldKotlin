package ru.kszorin.seaworldkotlin.di.components

import dagger.Component
import ru.kszorin.seaworldkotlin.di.modules.WorldModule
import ru.kszorin.seaworldkotlin.models.Animal
import ru.kszorin.seaworldkotlin.models.World
import ru.kszorin.seaworldkotlin.models.behaviour.PeriodicReproduction
import javax.inject.Singleton

/**
 * Created on 04.03.2018.
 */
@Component(modules = arrayOf(WorldModule::class))
@Singleton
interface ModelsComponent {

    fun inject(world: World)

    fun inject(animal: Animal)

    fun inject(periodicReproduction: PeriodicReproduction)
}