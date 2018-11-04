package sample.ds

class BST<K : Comparable<K>, V> {
    private var root: Node? = null

    fun put(key: K, value: V) {
        this.root = put(this.root, key, value)
    }

    private fun put(node: Node?, key: K, value: V): Node {
        if (node == null) return Node(key, value, null, null, 1)
        val cmp = key.compareTo(node.key)

        when {
            cmp < 0 -> node.left = put(node.left, key, value)
            cmp > 0 -> node.right = put(node.right, key, value)
            else -> node.value = value
        }
        node.size = 1 + size(node.left) + size(node.right)
        return node
    }

    fun size(): Int = size(root)

    private fun size(node: Node?): Int {
        return if (node == null) 0
        else node.size
    }


    fun get(key: K): V? {
        return get(this.root, key)
    }

    private fun get(node: Node?, key: K): V? {
        if (node == null) return null

        val cmp = key.compareTo(node.key)

        return when {
            cmp < 0 -> get(node.left, key)
            cmp > 0 -> get(node.right, key)
            else -> node.value
        }
    }

    fun delete(key: K) {
        this.root = delete(this.root, key)
    }

    private fun delete(node: Node?, key: K): Node? {
        if (node == null) return null

        val cmp = key.compareTo(node.key)

        if (cmp < 0) {
            node.left = delete(node.left, key)
        } else if (cmp > 0) {
            node.right = delete(node.right, key)
        } else {
            if (node.right == null) return node.left
            if (node.left == null) return node.right
            val m = min(node.right!!)
            m.right = deleteMin(node.right)
            m.left = node.left
            m.size = 1 + size(m.left) + size(m.right)
            return m
        }
        return node
    }


    fun contains(key: K) = get(key) != null

    fun deleteMin() {
        this.root = deleteMin(this.root)
    }

    private fun min(x: Node): Node {
        return if (x.left == null)
            x
        else
            min(x.left!!)
    }


    private fun deleteMin(node: Node?): Node? {
        if (node == null) return null
        if (node.left == null) return node.right

        node.left = deleteMin(node.left)
        node.size = 1 + size(node.left) + size(node.right)
        return node
    }


    inner class Node(val key: K, var value: V, var left: Node?, var right: Node?, var size: Int = 0) {

        override fun toString(): String {
            return "Node(key=$key, value=$value, left=$left, right=$right, size=$size)"
        }

    }
}