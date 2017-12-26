package sample.adt


fun rankIter(k: Int, arr: Array<Int>): Int {
    fun rankIter(low: Int, high: Int): Int {
        var lo = low
        var hi = high
        while (lo <= hi) {
            val mid = (lo + hi)/2

            if (k < arr[mid]) {
                hi = mid
            } else if (k > arr[mid]){
                lo = mid + 1
            } else {
                return mid
            }

        }
        return -1
    }

    return rankIter(0, arr.size - 1)
}