package sample.adt

fun rank(k: Int, arr: Array<Int>): Int {
    tailrec fun rank(low: Int, high: Int): Int {
        if (low > high) {
            return -1
        }
        val mid = (low + high) / 2

        return when {
            (k < arr[mid]) -> rank(low, mid)
            (k > arr[mid]) -> rank(mid + 1, high)
            else -> mid
        }
    }

    return rank(0, arr.size - 1)
}
