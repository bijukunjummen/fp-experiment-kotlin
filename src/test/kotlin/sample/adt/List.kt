package sample.adt

fun <A> list(vararg arr: A): List<A> {
    var l: List<A> = Nil
    for (i in arr.reversed()) {
        l = Cons(i, l)
    }
    return l
}

sealed class List<out A> {
    abstract fun size(): Int
    fun <B> map(f: (A) -> B): List<B> {
        return when (this) {
            is Cons -> Cons(f(head), tail.map(f))
            is Nil -> Nil
        }
    }

    abstract val head: A

    abstract val tail: List<A>


    fun <B> foldLeft(z: B, f: (B, A) -> B): B {
        return when (this) {
            is Nil -> z
            is Cons -> tail.foldLeft(f(z, head), f)
        }
    }

    fun reverse(): List<A> {
        return foldLeft(Nil as List<A>, { b, a -> Cons(a, b) })
    }

    fun <B> foldRight(z: B, f: (A, B) -> B): B {
        return reverse().foldLeft(z, { b, a -> f(a, b) })
    }

    fun append(l: List<@UnsafeVariance A>): List<A> {
        return when (this) {
            is Cons -> Cons(head, tail.append(l))
            is Nil -> l
        }
    }
    
 
    
    fun flatMap(f: (a: A) -> List<@UnsafeVariance A> ): List<A> {
        return flatten(map { a -> f(a) })
    }
    
    companion object {
        fun <A> flatten(l: List<List<A>>): List<A> {
            return l.foldRight(Nil as List<A>, {a, b -> a.append(b)})
        }
    }
}



object Nil : List<Nothing>() {
    override fun size(): Int = 0

    override val head: Nothing
        get() {
            throw NoSuchElementException("head of empty list")
        }

    override val tail: List<Nothing>
        get() {
            throw NoSuchElementException("tail of empty list")
        }
}

data class Cons<out T>(override val head: T, override val tail: List<T>) : List<T>() {

    override fun size(): Int =
            1 + tail.size()

}




