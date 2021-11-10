package com.njves.memorandum

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Memo(@PrimaryKey val id: UUID = UUID.randomUUID(), var subject: String = "", var content: String = "") {
    fun isEmpty(): Boolean {
        return subject == "" && content == ""
    }
}