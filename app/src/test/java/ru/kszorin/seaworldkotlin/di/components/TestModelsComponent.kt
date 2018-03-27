package ru.kszorin.seaworldkotlin.di.components

import dagger.Component
import ru.kszorin.seaworldkotlin.di.models.TestWorldModule
import javax.inject.Singleton

/**
 * Created on 11.03.2018.
 */
@Component(modules = arrayOf(TestWorldModule::class))
@Singleton
interface TestModelsComponent : ModelsComponent {

}