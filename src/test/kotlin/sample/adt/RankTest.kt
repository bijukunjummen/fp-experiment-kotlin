package sample.adt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class RankTest {
    @Test
    fun rankTest() {
        val array = arrayOf(2, 4, 6, 9, 10, 11, 16, 17, 19, 20, 25)
        assertEquals(-1, rank(100, array))
        assertEquals(0, rank(2, array))
        assertEquals(2, rank(6, array))
        assertEquals(5, rank(11, array))
        assertEquals(10, rank(25, array))
    }

    @Test
    fun rankIterTest() {
        val array = arrayOf(2, 4, 6, 9, 10, 11, 16, 17, 19, 20, 25)
        assertEquals(-1, rankIter(100, array))
        assertEquals(0, rankIter(2, array))
        assertEquals(2, rankIter(6, array))
        assertEquals(5, rankIter(11, array))
        assertEquals(10, rankIter(25, array))
    }
}