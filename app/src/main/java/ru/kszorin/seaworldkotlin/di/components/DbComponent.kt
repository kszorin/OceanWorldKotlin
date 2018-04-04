package ru.kszorin.seaworldkotlin.di.components

import dagger.Component
import ru.kszorin.seaworldkotlin.di.modules.DbModule
import ru.kszorin.seaworldkotlin.repositories.SeaWorldRepository
import javax.inject.Singleton

/**
 * Created on 03.04.2018.
 */

@Component(modules = arrayOf(DbModule::class))
@Singleton
interface DbComponent {

    fun inject(seaWorldRepository: SeaWorldRepository)
}