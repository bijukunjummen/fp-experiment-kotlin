package sample.adt

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ListTest {
    
    @Test
    fun creatingAListRaw() {
        val l = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))

        assertThat(l.head).isEqualTo(1)
        assertThat(l.tail).isEqualTo(Cons(2, Cons(3, Cons(4, Nil))))
        assertThat(l.tail.head).isEqualTo(2)
    }
    
    @Test
    fun createAListVarargs() {
        var l = list(1, 2, 3, 4)
        assertThat(l.head).isEqualTo(1)
        assertThat(l.tail).isEqualTo(Cons(2, Cons(3, Cons(4, Nil))))
        assertThat(l.tail.head).isEqualTo(2)        
    }
    
    @Test
    fun headOfAnEmptyList() {
        assertThatThrownBy({
            Nil.head
        })
    }
    
    @Test
    fun breakDownToHeadAndTail() {
        val l = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
        val (h, t) = l
        assertThat(h).isEqualTo(1)
        assertThat(t).isEqualTo(Cons(2, Cons(3, Cons(4, Nil))))
    }
    
    @Test
    fun foldLeftSum() {
        val l = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
        assertThat(l.foldLeft(0, {r, e -> r + e})).isEqualTo(10)
    }

    @Test
    fun foldRightSum() {
        val l = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
        assertThat(l.foldRight(0, {e, r -> e + r})).isEqualTo(10)
    }
    
    @Test
    fun append() {
        val l1 = list(1, 2, 3)
        val l2 = list(4, 5, 6)
        
        assertThat(l1.append(l2)).isEqualTo(list(1, 2, 3, 4, 5, 6))
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

        val l2 = l.flatMap { e -> list(e, e) }

        assertThat(l2)
                .isEqualTo(
                        Cons(1, Cons(1, Cons(2, Cons(2, Cons(3, Cons(3, Nil)))))))
    }
}