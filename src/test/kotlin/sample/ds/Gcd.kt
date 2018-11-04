package sample.ds

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

tailrec fun gcd(a: Int, b: Int): Int {
    val r = a % b
    return when (r) {
        0 -> b
        else -> gcd(b, r)
    }
}

class GcdTest {

    @ParameterizedTest(name = "{index} => a={0}, b={1}, gcd={2}")
    @MethodSource("gcdData")
    fun testGcdFunc(a: Int, b: Int, ans: Int) {
        assertThat(gcd(a, b)).isEqualTo(ans)
    }

    companion object {
        @JvmStatic
        fun gcdData(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(96, 112, 16),
                    Arguments.of(15, 20, 5),
                    Arguments.of(12, 36, 12),
                    Arguments.of(72, 60, 12),
                    Arguments.of(65, 39, 13),
                    Arguments.of(95, 76, 19)
            )
        }

    }
}