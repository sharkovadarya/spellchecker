package spellchecker.tree

data class Node<T>(
    val c: Char,
    var value: T? = null
) {
    var left: Node<T>? = null
    var mid: Node<T>? = null
    var right: Node<T>? = null
    var isWordEnd: Boolean = false
}

// based on this implementation: https://gist.github.com/TheCrafter/2c093c564b6eadb566f8cec52f6412f2

class TernarySearchTree<T> {
    var size: Int = 0
        private set
    private var root: Node<T>? = null

    fun getRootNode() = root

    operator fun contains(key: String): Boolean {
        return get(key) != null
    }

    operator fun get(key: String): T? {
        if (key.isEmpty()) {
            throw java.lang.IllegalArgumentException("Empty keys are not allowed.")
        }

        val node = get(root, key, 0)
        return node?.value
    }

    private operator fun get(node: Node<T>?, key: String, d: Int): Node<T>? {
        if (node == null)
            return null

        if (key.isEmpty()) {
            throw IllegalArgumentException("Empty keys are not allowed.")
        }

        val c = key[d]
        return when {
            c < node.c -> get(node.left, key, d)
            c > node.c -> get(node.right, key, d)
            d < key.length - 1 -> get(node.mid, key, d + 1)
            else -> node
        }
    }

    fun put(key: String, value: T) {
        if (!contains(key))
            size++

        root = put(root, key, value, 0)
    }

    private fun put(x: Node<T>?, key: String, value: T, d: Int): Node<T> {
        var node = x
        val c = key[d]
        if (node == null) {
            node = Node(c)
        }

        when {
            c < node.c -> node.left = put(node.left, key, value, d)
            c > node.c -> node.right = put(node.right, key, value, d)
            d < key.length - 1 -> node.mid = put(node.mid, key, value, d + 1)
            else -> {
                node.value = value
                node.isWordEnd = true
            }
        }
        return node
    }
}