package com.nuhlp.nursehelper.utill.useapp

object Hangul {
    fun getInitialSound(text: String): String? {
        val chs = arrayOf(
            "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
            "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
            "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
            "ㅋ", "ㅌ", "ㅍ", "ㅎ"
        )
        if (text.length > 0) {
            val chName = text[0]
            if (chName.toInt() >= 0xAC00) {
                val uniVal = chName.toInt() - 0xAC00
                val cho = (uniVal - uniVal % 28) / 28 / 21
                return chs[cho]
            }
        }
        return null
    }
}