package com.njves.memorandum

import java.util.*

data class Memo(val id: UUID = UUID.randomUUID(), var subject: String = "", var content: String = "")