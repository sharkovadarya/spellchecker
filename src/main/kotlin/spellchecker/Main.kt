package spellchecker

fun main() {
    val spellchecker = Spellchecker()

    // "принимающий на вход слово" -> считаем, что первое из полученных
    val input = readLine()?.split(Regex("\\s+"))?.first()?.toLowerCase()
    if (input == null) {
        println("No input given.")
        return
    } else {
        // English words might have hyphens
        val words = input.split("-")
        for (word in words) {
            val isCorrectlySpelled = spellchecker.checkSpelling(word)
            if (isCorrectlySpelled) {
                println("$word: correct spelling.")
            } else {
                println("$word: possibly incorrect spelling.\nSuggestions: ")
                val suggestions = spellchecker.getSuggestions(word)
                suggestions.forEach { println(it) }
            }
        }
    }
}