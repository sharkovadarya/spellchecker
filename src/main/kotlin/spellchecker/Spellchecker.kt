package spellchecker

import org.apache.commons.lang3.StringUtils
import spellchecker.tree.Node
import spellchecker.tree.TernarySearchTree
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class Spellchecker {
    private val maxEditDistance = 3
    private val suggestionListSize = 7

    private val tree = TernarySearchTree<Long>()

    init {
        buildTree()
    }

    fun checkSpelling(word: String) : Boolean {
        return tree.contains(word)
    }

    fun getSuggestions(string: String): List<String> {
        if (string.isEmpty()) {
            throw IllegalArgumentException("Empty input.")
        }

        val suggestions = PriorityQueue<Word>()
        traverseTree(tree.getRootNode(), suggestions, string)

        val outputList = mutableListOf<String>()
        var i = 0
        while (suggestions.isNotEmpty() && i < suggestionListSize) {
            val element = suggestions.poll()
            outputList.add(element.word)
            i++
        }
        return outputList
    }

    private fun traverseTree(
        root: Node<Long>?,
        suggestedWords: PriorityQueue<Word>,
        word: String,
        string: String = ""
    ) {
        if (root == null)
            return

        val newString = string + root.c
        val distance = StringUtils.getLevenshteinDistance(word, newString)
        if (string.length < word.length &&
            StringUtils.getLevenshteinDistance(string, word.substring(0, string.length + 1)) > maxEditDistance) {
            return
        } else if (string.length > word.length + maxEditDistance) {
            return
        } else if (Math.abs(string.length - word.length) <= maxEditDistance && distance > maxEditDistance) {
            return
        }

        traverseTree(root.left, suggestedWords, word, string)
        if (root.isWordEnd && distance <= maxEditDistance) {
            suggestedWords.add(Word(newString, distance, root.value!!))
        }
        traverseTree(root.mid, suggestedWords, word, newString)
        traverseTree(root.right, suggestedWords, word, string)
    }

    private fun buildTree(): Node<Long>? {
        // it's not that big a file
        // dictionary taken from: http://norvig.com/google-books-common-words.txt
        val dictionaryFile = javaClass.classLoader.getResource("english_word_frequencies.txt").path
        val lines = Files.readAllLines(Paths.get(dictionaryFile))
        for (line in lines) {
            val token = line
                .split("\t".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
                .map { it.toLowerCase() }
            tree.put(token[0], token[1].toLong())
        }

        return tree.getRootNode()
    }

    private inner class Word(
        val word: String,
        val editDistance: Int,
        val frequency: Long
    ) : Comparable<Word> {
        override operator fun compareTo(other: Word): Int {
            return when {
                this.editDistance > other.editDistance -> 1
                this.editDistance < other.editDistance -> -1
                this.frequency < other.frequency -> 1
                this.frequency > other.frequency -> -1
                else -> 0
            }
        }
    }
}