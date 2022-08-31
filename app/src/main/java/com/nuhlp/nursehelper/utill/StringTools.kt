package com.nuhlp.nursehelper.utill


fun arrayToLines(array: Array<String>): String {
    var Sentence = ""
    array.forEach {
        Sentence += "$it\n"
    }
    if (Sentence != "") {
        Sentence.substring(0, Sentence.lastIndex)
    }
    return Sentence
}
