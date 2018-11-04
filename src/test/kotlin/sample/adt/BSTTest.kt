package sample.adt

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import sample.ds.BST

class BSTTest {

    @Test
    fun basicOperationsOnBst() {
        val bst = BST<Int, String>()

        bst.put(1, "one")
        bst.put(2, "two")

        assertThat(bst.size()).isEqualTo(2)

        assertThat(bst.get(1)).isEqualTo("one")
        assertThat(bst.get(2)).isEqualTo("two")
    }

    @Test
    fun deleteANode() {
        val bst = BST<String, String>()

        bst.put("a", "a")
        bst.put("b", "b")

        bst.delete("a")

        assertThat(bst.size()).isEqualTo(1)
        assertThat(bst.contains("a")).isFalse()
        assertThat(bst.contains("b")).isTrue()
    }

    @Test
    fun deleteMin() {
        val bst = BST<Int, Int>()

        bst.put(4, 4)
        bst.put(6, 6)
        bst.put(1, 1)

        bst.deleteMin()

        assertThat(bst.size()).isEqualTo(2)
        assertThat(bst.contains(1)).isFalse()
    }
}