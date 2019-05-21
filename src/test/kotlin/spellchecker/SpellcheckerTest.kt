package spellchecker

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class SpellcheckerTest {
    private val spellchecker = Spellchecker()

    @Test
    fun testWord() {
        assertTrue(spellchecker.checkSpelling("word"))
    }

    @Test
    fun testIncorrectlySpelledWord() {
        assertFalse(spellchecker.checkSpelling("wrd"))
    }

    @Test
    fun testSuggestions() {
        val suggestions = spellchecker.getSuggestions("wrd")
        assertTrue(suggestions.contains("word"))
        assertTrue(suggestions.contains("ward"))
        assertFalse(suggestions.contains("wrrd"))
    }

    @Test
    fun testKeysmash() {
        val suggestions = spellchecker.getSuggestions("fgadsjhfkshj")
        assertTrue(suggestions.isEmpty())
    }

    // this particular dictionary has all these words
    @Test
    fun testGeographicalPlaces() {
        assertTrue(spellchecker.checkSpelling("harlem"))
        assertTrue(spellchecker.checkSpelling("london"))
        assertTrue(spellchecker.checkSpelling("asia"))
    }

    @Test
    fun testWordNotInDictionary() {
        // this word exists and is spelled correctly, but it's not in the dictionary
        assertFalse(spellchecker.checkSpelling("defenestration"))
    }

    @Test
    fun testMissedLetterAtEnd() {
        assertFalse(spellchecker.checkSpelling("goos"))
        assertTrue(spellchecker.getSuggestions("goos").contains("goose"))
    }

    @Test
    fun testMisspelledMiddleLetters() {
        assertFalse(spellchecker.checkSpelling("ountry"))
        assertTrue(spellchecker.getSuggestions("ountry").contains("outcry"))
    }

}