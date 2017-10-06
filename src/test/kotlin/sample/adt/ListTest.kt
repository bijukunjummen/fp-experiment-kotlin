package sample.adt

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ListTest {

    @Test
    fun creatingAListRaw() {
        val l1: List<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))

        assertThat(l1.head).isEqualTo(1)
        assertThat(l1.tail).isEqualTo(Cons(2, Cons(3, Cons(4, Nil))))

        val l2: List<String> = Nil
        assertThat(l2).isEqualTo(Nil)
    }

    @Test
    fun createAListVarargs() {
        val l = list(1, 2, 3, 4)
        assertThat(l.head).isEqualTo(1)
        assertThat(l.tail).isEqualTo(Cons(2, Cons(3, Cons(4, Nil))))
    }

    @Test
    fun headOfAnEmptyList() {
        assertThatThrownBy({
            Nil.head
        })
    }

    @Test
    fun dropElements() {
        val l = list(4, 3, 2, 1)
        assertThat(l.drop(2)).isEqualTo(list(2, 1))
    }

    @Test
    fun dropWhile() {
        val l = list(1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
        assertThat(l.dropWhile({ e -> e < 20 })).isEqualTo(list(21, 34, 55, 89))
    }

    @Test
    fun foldLeft() {
        val l = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
        assertThat(l.foldLeft(0, { r, e -> r + e })).isEqualTo(10)
        assertThat(l.foldLeft(1, { r, e -> r * e })).isEqualTo(24)
    }

    @Test
    fun foldRightSum() {
        val l = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
        assertThat(l.foldRight(0, { e, r -> e + r })).isEqualTo(10)
        assertThat(l.foldRight(1, { e, r -> e * r })).isEqualTo(24)
        assertThat(l.foldRightViaFoldLeft(0, { e, r -> e + r })).isEqualTo(10)
        assertThat(l.foldRightViaFoldLeft(1, { e, r -> e * r })).isEqualTo(24)
    }

    @Test
    fun reverseAList() {
        val l = list("a", "b", "c")
        assertThat(l.reverse()).isEqualTo(list("c", "b", "a"))
    }
    
    @Test
    fun appendTwoLists() {
        val l1: List<Number> = list(1, 2, 3)
        val l2 = list(4.1, 5.1, 6.1)

        assertThat(l1.append(l2)).isEqualTo(list(1, 2, 3, 4.1, 5.1, 6.1))
    }

    @Test
    fun sizeOfANilList() {
        val l = Nil
        assertThat(l.size()).isEqualTo(0)
    }

    @Test
    fun sizeOfANonNilList() {
        val l = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
        assertThat(l.size()).isEqualTo(4)
    }

    @Test
    fun mapElements() {
        val l = Cons(1, Cons(2, Cons(3, Nil)))
        val l2 = l.map { e -> e.toString() }
        assertThat(l2).isEqualTo(Cons("1", Cons("2", Cons("3", Nil))))
    }

    @Test
    fun testFlatten() {
        val l = list(list(1, 2), list(3, 4))
        assertThat(List.flatten(l)).isEqualTo(list(1, 2, 3, 4))
    }

    @Test
    fun flatMapElements() {
        val l = Cons(1, Cons(2, Cons(3, Nil)))
        val l2 = l.flatMap { e -> list(e.toString(), e.toString()) }

        assertThat(l2)
                .isEqualTo(
                        Cons("1", Cons("1", Cons("2", Cons("2", Cons("3", Cons("3", Nil)))))))
    }
}