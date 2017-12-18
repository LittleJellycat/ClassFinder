import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    args.size == 2 || throw IllegalArgumentException("Please, provide path and pattern.")
    File(args[0]).exists() || throw IllegalArgumentException("File not found.")
    val pattern = args[1]
    val classes = Files.readAllLines(Paths.get(args[0]))
            .filter { it.containsPattern(pattern) }
            .sortedBy { it.substringAfterLast('.') }
    println(classes)
}

fun String.containsPattern(pattern: String) = when {
    pattern.isEmpty() -> false
    pattern.any { it.isUpperCase() } -> substringAfterLast('.').containsCaseSensitivePattern(pattern)
    else -> substringAfterLast('.').containsCaseInsensitivePattern(pattern)
}

private fun String.containsCaseSensitivePattern(pattern: String): Boolean {
    var stringPointer = 0
    var patternPointer = 0
    var lastUpperIndex = -1
    val inBounds = { patternPointer < pattern.length && stringPointer < length }
    val toNextUpperCaseLetter = {
        while (stringPointer < length && this[stringPointer].isLowerCase()) {
            stringPointer++
        }
    }
    val charEq = { this[stringPointer] == pattern[patternPointer] }
    while (inBounds()) {
        if (charEq()) {
            stringPointer++
            patternPointer++
        } else if (pattern[patternPointer] == '*') {
            patternPointer++
            while (inBounds()) {
                if (charEq()) {
                    stringPointer++
                    patternPointer++
                    break
                }
                stringPointer++
            }
        } else if (pattern[patternPointer].isUpperCase() && this[stringPointer].isLowerCase()) {
            toNextUpperCaseLetter()
            lastUpperIndex = patternPointer
        } else if (pattern[patternPointer].isLowerCase() && !charEq()) {
            if (lastUpperIndex != -1) {
                patternPointer = lastUpperIndex
                toNextUpperCaseLetter()
            } else {
                stringPointer++
            }
        } else do {
            stringPointer++
        } while (stringPointer < length && this[stringPointer].isUpperCase())
        if (patternPointer == pattern.length - 1 && pattern[patternPointer] == ' ') {
            return stringPointer == length
        }
    }

    return patternPointer == pattern.length
}

private fun String.containsCaseInsensitivePattern(pattern: String): Boolean {
    var stringPointer = 0
    var patternPointer = 0
    var lastMatched = false
    val inBounds = { patternPointer < pattern.length && stringPointer < length }
    val charEq = { this[stringPointer].toLowerCase() == pattern[patternPointer] }
    val toNextMatch = {
        while (inBounds()) {
            if (charEq()) {
                lastMatched = true
                stringPointer++
                patternPointer++
                break
            }
            stringPointer++
        }
    }
    while (pattern[patternPointer] == '*' && patternPointer < pattern.length) {
        patternPointer++
    }
    toNextMatch()
    while (inBounds()) {
        if (charEq() && (this[stringPointer].isUpperCase() || lastMatched)) {
            stringPointer++
            patternPointer++
            lastMatched = true
        } else if (pattern[patternPointer] == '*') {
            patternPointer++
            toNextMatch()
        } else {
            stringPointer++
            lastMatched = false
        }
        if (patternPointer == pattern.length - 1 && pattern[patternPointer] == ' ') {
            return stringPointer == length
        }
    }

    return patternPointer == pattern.length
}