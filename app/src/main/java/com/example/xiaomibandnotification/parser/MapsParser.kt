package com.example.xiaomibandnotification.parser

import android.app.Notification
import android.os.Bundle
import com.example.xiaomibandnotification.model.NavigationData

object MapsParser {

    fun parse(extras: Bundle): NavigationData {

        val title =
            extras.getCharSequence(Notification.EXTRA_TITLE)?.toString() ?: ""

        val text =
            extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""

        val sub =
            extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString() ?: ""

        return NavigationData(
            title = title,
            message = formatInstruction(text),
            eta = sub
        )
    }

    private fun formatInstruction(text: String): String {

        return when {

            text.contains("Rẽ trái", ignoreCase = true) ->
                "⬅️ $text"

            text.contains("Rẽ phải", ignoreCase = true) ->
                "➡️ $text"

            text.contains("Đi thẳng", ignoreCase = true) ->
                "⬆️ $text"

            text.contains("vòng xuyến", ignoreCase = true) ->
                "🔄 $text"

            text.contains("đến nơi", ignoreCase = true) ->
                "🏁 $text"

            else ->
                "🧭 $text"
        }
    }

}