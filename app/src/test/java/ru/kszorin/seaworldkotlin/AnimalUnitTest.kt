package ru.kszorin.seaworldkotlin

import dagger.android.DaggerActivity
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

import ru.kszorin.seaworldkotlin.models.Orca
import ru.kszorin.seaworldkotlin.models.Pinguin
import ru.kszorin.seaworldkotlin.models.World
import java.util.*
import ru.kszorin.seaworldkotlin.di.components.DaggerTestModelsComponent
import ru.kszorin.seaworldkotlin.di.models.TestWorldModule
import ru.kszorin.seaworldkotlin.models.Creature
import javax.inject.Inject


/**
 * Created on 09.03.2018.
 */
@RunWith(Parameterized::class)
class AnimalUnitTest(val x: Int, val y: Int) {

    lateinit private var orca: Orca

    @Before
    fun setTestComponent() {
        val testModelsComponent = DaggerTestModelsComponent.builder().testWorldModule(TestWorldModule()).build()
        SeaWorldApp.modelsComponent = testModelsComponent
        orca =  Orca(0, Pair(x, y))
        orca.creaturesMap.put(0, orca)
        orca.waterSpace[y][x] = 0
    }

    @Test
    fun testFindPlacesforMoving() {
        val size = orca.findPlacesForMoving().size
        assertTrue(size > 0)
        println("x = $x, y = $y, founded positions = $size")
    }

    @Test
    fun testFindVictims() {

        val size = orca.findVictims().size
        assertTrue(size > 0)
        println("x = $x, y = $y, founded victims = $size")
    }

    @Test
    fun testEnvironsMoving() {
        val result = orca.movingBehaviour.move(orca, orca.findPlacesForMoving())
        assertTrue(result)
    }

    @Test
    fun testHunting() {
        val result = orca.eatingBehaviour.eat(orca, orca.findVictims())
        assertTrue(result)
    }


    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: test 'Find free positions:")
        fun data(): Collection<Array<Int>> {
            return listOf(arrayOf(0, 0),
                    arrayOf(1, 0),
                    arrayOf(2, 0),
                    arrayOf(0, 1),
                    arrayOf(2, 1),
                    arrayOf(1, 2))
        }
    }
}