package ru.kszorin.seaworldkotlin

import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.Parameterized
import ru.kszorin.seaworldkotlin.models.Animal
import ru.kszorin.seaworldkotlin.models.Orca
import ru.kszorin.seaworldkotlin.models.Pinguin
import ru.kszorin.seaworldkotlin.models.World
import java.util.*

/**
 * Created on 09.03.2018.
 */
@RunWith(Parameterized::class)
class AnimalUnitTest(val x: Int, val y: Int) {

    val orca = Orca(1, Pair(x, y))

    init {
        orca.waterSpace = array2dOfInt(3, 3)
        orca.waterSpace[0] = intArrayOf(World.FREE_WATER_CODE, World.FREE_WATER_CODE, World.FREE_WATER_CODE)
        orca.waterSpace[1] = intArrayOf(World.FREE_WATER_CODE,          1           , World.FREE_WATER_CODE)
        orca.waterSpace[2] = intArrayOf(        2,             World.FREE_WATER_CODE,           3)

        orca.creaturesMap = mutableMapOf()
        orca.creaturesMap.put(1, Pinguin(1, Pair(0, 1)))
        orca.creaturesMap.put(2, Pinguin(2, Pair(2, 1)))
        orca.creaturesMap.put(3, Orca(2, Pair(2, 2)))
    }

    @Test
    fun testFindPlacesfoMoving() {

        val size = orca.findPlacesForMoving().size
        assert(size > 0)
        println("x = $x, y = $y, founded positions = $size")
    }

    @Test
    fun testFindVictims() {

        val size = orca.findVictims().size
        assert(size > 0)
        println("x = $x, y = $y, founded victims = $size")
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