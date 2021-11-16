package com.njves.memorandum

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Memo(@PrimaryKey val id: UUID = UUID.randomUUID(), var subject: String = "", var content: String = "", var date: Date = Date()) {
    fun isEmpty(): Boolean {
        return subject.isBlank() && content.isBlank()
    }
    // Метод возвращающий форматированную строку даты и времени
    fun getFormatDate(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
        return sdf.format(date)
    }

}