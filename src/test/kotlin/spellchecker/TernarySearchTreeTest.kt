package spellchecker

import org.junit.Assert.*
import org.junit.Test
import spellchecker.tree.TernarySearchTree
import java.lang.IllegalArgumentException

class TernarySearchTreeTest {
    @Test
    fun testPut() {
        val tree = TernarySearchTree<Int>()
        assertEquals(0, tree.size)
        tree.put("string", 1)
        assertEquals(1, tree.size)
    }

    @Test
    fun testContains() {
        val tree = TernarySearchTree<Int>()
        assertFalse(tree.contains("string"))
        tree.put("string", 1)
        assertTrue(tree.contains("string"))
        assertFalse(tree.contains("strings"))
    }

    @Test
    fun testGet() {
        val tree = TernarySearchTree<Int>()
        tree.put("string", 1)
        tree.put("text", 2)
        tree.put("strings", 3)
        assertEquals(1, tree["string"])
        assertEquals(2, tree["text"])
        assertEquals(3, tree["strings"])
    }

    @Test(expected = IllegalArgumentException::class)
    fun testPutEmptyString() {
        TernarySearchTree<String>().put("", "text")
    }
}