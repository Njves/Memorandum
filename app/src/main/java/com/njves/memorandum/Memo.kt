package com.njves.memorandum

import android.graphics.Color
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Memo(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var subject: String = "",
                var content: String = "",
                var date: Date = Date(),
                var color: Int = Color.WHITE,
                var completed: Boolean = false) {
    fun isEmpty(): Boolean {
        return subject.isBlank() && content.isBlank()
    }
    @Ignore
    val sdf = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())

    // Метод возвращающий форматированную строку даты и времени
    val formatDate
    get() = sdf.format(date)

}