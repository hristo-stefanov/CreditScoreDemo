package hristostefanov.creditscoredemo.core.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoachingSummary(
    val activeChat: Boolean = false,
    val activeTodo: Boolean = false,
    val numberOfCompletedTodoItems: Int = 0,
    val numberOfTodoItems: Int = 0,
    val selected: Boolean = false
)