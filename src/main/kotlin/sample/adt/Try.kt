package sample.adt


sealed class Try<out T> {
    abstract fun isSuccess(): Boolean
    fun isFailure(): Boolean = !isSuccess()
    abstract fun <R> map(block: (T) -> R): Try<R>

    abstract fun get(): T
    abstract fun <R> flatMap(tryBlock: (T) -> Try<R>): Try<R>

    data class Success<T>(val result: T) : Try<T>() {
        override fun isSuccess(): Boolean = true
        override fun get(): T = result
        override fun <R> map(block: (T) -> R): Try<R> {
            return of {
                block(result)
            }
        }

        override fun <R> flatMap(tryBlock: (T) -> Try<R>): Try<R> {
            return try {
                tryBlock(result)
            } catch (e: Throwable) {
                failure(e)
            }
        }
    }

    data class Failure<T>(val throwable: Throwable) : Try<T>() {
        override fun isSuccess(): Boolean = false
        override fun get(): T = throw throwable
        override fun <R> map(block: (T) -> R): Try<R> {
            return this as Failure<R>
        }

        override fun <R> flatMap(tryBlock: (T) -> Try<R>): Try<R> {
            return this as Failure<R>
        }
    }

    companion object {
        fun <T> of(block: () -> T) = try {
            Success(block())
        } catch (e: Throwable) {
            Failure(e)
        }

        fun <T> success(t: T): Try<T> {
            return Success(t)
        }

        fun <T> failure(e: Throwable): Try<T> {
            return Failure(e)
        }
    }
}