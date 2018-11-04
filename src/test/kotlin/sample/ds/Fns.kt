package sample.ds

import org.junit.jupiter.api.Test


class Fns {
    @Test
    fun testASequence() {
        val l = listOf(1, 2, 3, 4, 5)

        val nl = l.asSequence()
                .map { it * 10 }
                .toList()

        for (v in nl) {
            println(v)
        }
    }
}