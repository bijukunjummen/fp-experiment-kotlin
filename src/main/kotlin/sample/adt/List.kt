package sample.adt

fun <A> list(vararg arr: A): List<A> {
    var l: List<A> = Nil
    for (i in arr.reversed()) {
        l = Cons(i, l)
    }
    return l
}

sealed class List<out A> {

    abstract val head: A

    abstract val tail: List<A>
    
    fun <B> map(f: (A) -> B): List<B> {
        return when (this) {
            is Cons -> Cons(f(head), tail.map(f))
            is Nil -> Nil
        }
    }

    fun <B> foldLeft(z: B, f: (B, A) -> B): B {
        tailrec fun foldLeft(l: List<A>, z: B, f: (B, A) -> B): B {
            return when(l) {
                is Nil -> z
                is Cons -> foldLeft(l.tail, f(z, l.head), f)
            }
        }
        
        return foldLeft(this, z, f)
    }

    fun reverse(): List<A> {
        return foldLeft(Nil as List<A>, { b, a -> Cons(a, b) })
    }

    fun <B> foldRight(z: B, f: (A, B) -> B): B {
        return reverse().foldLeft(z, { b, a -> f(a, b) })
    }

    fun size(): Int = foldRight(0, {_, r -> r + 1})

    fun append(l: List<@UnsafeVariance A>): List<A> {
        return when (this) {
            is Cons -> Cons(head, tail.append(l))
            is Nil -> l
        }
    }
    
 
    
    fun flatMap(f: (a: A) -> List<@UnsafeVariance A>): List<A> {
        return flatten(map { a -> f(a) })
    }
    
    companion object {
        fun <A> flatten(l: List<List<A>>): List<A> {
            return l.foldRight(Nil as List<A>, { a, b -> a.append(b)})
        }
    }
}



data class Cons<out T>(override val head: T, override val tail: List<T>) : List<T>()

object Nil : List<Nothing>() {
    override val head: Nothing
        get() {
            throw NoSuchElementException("head of empty list")
        }

    override val tail: List<Nothing>
        get() {
            throw NoSuchElementException("tail of empty list")
        }

    override fun toString(): String {
        return "Nil"
    }
}



