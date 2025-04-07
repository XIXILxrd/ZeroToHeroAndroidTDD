package ru.easycode.zerotoheroandroidtdd

interface Count {

    fun initial(number: String): UiState

    fun increment(number: String): UiState

    fun decrement(number: String): UiState

    class Base(val step: Int, val max: Int, val min: Int) : Count {

        init {
            if (step < 1) {
                throw IllegalStateException("step should be positive, but was $step")
            }

            if (max < 0) {
                throw IllegalStateException("max should be positive, but was $max")
            }

            if (max < step) {
                throw IllegalStateException("max should be more than step")
            }

            if (max < min) {
                throw IllegalArgumentException("max should be more than min")
            }
        }

        override fun initial(number: String): UiState {
            return when (number.toInt()) {
                max -> UiState.Max(number)
                min -> UiState.Min(number)
                else -> UiState.Base(number)
            }
        }

        override fun increment(number: String): UiState {
            val result = number.toInt() + step

            return if (result + step > max) {
                UiState.Max(result.toString())
            } else {
                UiState.Base(result.toString())
            }
        }

        override fun decrement(number: String): UiState {
            val result = number.toInt() - step

            return if (result - step < min) {
                UiState.Min(result.toString())
            } else {
                UiState.Base(result.toString())
            }
        }
    }

}

