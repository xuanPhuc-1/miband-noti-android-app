package com.example.xiaomibandnotification.parser

import android.app.Notification
import android.os.Bundle
import com.example.xiaomibandnotification.model.NavigationData

object MapsParser {
    private val rules = listOf(
        "Rẽ trái vào " to "← ",
        "Rẽ phải vào " to "→ ",
        "Tiếp tục trên " to "↑ ",
        "về hướng " to "↑ "
    )

    fun parse(extras: Bundle): NavigationData {

        val title =
            extras.getCharSequence(Notification.EXTRA_TITLE)?.toString() ?: ""

        val text =
            extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""

        val sub =
            extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString() ?: ""

        return NavigationData(
            title = title,
            message = simplify(text),
            eta = sub
        )
    }

    private fun simplify(text: String): String {

        for ((prefix, arrow) in rules) {
            if (text.startsWith(prefix)) {
                return arrow + cleanStreetName(text.removePrefix(prefix))
            }
        }

        return text
    }

    private fun cleanStreetName(name: String): String {

        return name
            .replace("Đ. ", "")
            .replace("Đường ", "")
            .replace("Đại lộ ", "")
            .replace("Quốc lộ ", "")
            .replace("Tỉnh lộ ", "")
            .trim()
    }
}