package pl.ogiba.dwarf.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import javafx.util.StringConverter
import java.util.regex.Pattern

/**
 *
 * @author robertogiba
 */
class JsonConverter : StringConverter<String>() {

    override fun toString(`object`: String?) = `object` ?: ""

    override fun fromString(string: String) = formatJson(string, DEFAULT_INDENT)

    fun toPrettyFormat(jsonString: String): String {
        val parser = JsonParser()
        val jsonElement = parser.parse(jsonString)
        val gson = GsonBuilder().setPrettyPrinting().create()

        return if (jsonElement.isJsonArray) {
            val json = jsonElement.asJsonArray
            gson.toJson(json)
        } else {
            val json = jsonElement.asJsonObject
            gson.toJson(json)
        }
    }

    fun formatJson(jsonString: String, indent: Int): String {
        val regex = Pattern.compile("[\\[{\"*\",}\\]]")

        var nodeDepth = 0
        var openQuotation = true

        return StringBuilder().let { builder ->
            for (singleChar in jsonString.toCharArray()) {
                val currentChar = singleChar.toString()

                val matcher = regex.matcher(currentChar)

                if (matcher.find()) {
                    when (currentChar) {
                        "]"      -> builder.append("\n").append(currentChar)
                        "{", "[" -> {
                            val isColon = findColon(builder.toString())

                            if (!isColon) {
                                builder.append(indentValue(indent * nodeDepth))
                            }

                            nodeDepth++
                            builder.append(currentChar).append("\n")
                            openQuotation = true
                        }
                        "\""     -> {
                            if (openQuotation) {
                                openQuotation = false
                                builder.append(indentValue(indent * nodeDepth))
                            }

                            builder.append(currentChar)
                        }
                        "}"      -> {
                            nodeDepth--
                            builder.append("\n").append(indentValue(indent * nodeDepth)).append(currentChar)
                        }
                        ","      -> {
                            builder.append(currentChar).append("\n")
                            openQuotation = true
                        }
                        else     -> builder.append(indentValue(indent * nodeDepth)).append(currentChar).append("\n")
                    }
                } else {
                    builder.append(currentChar)
                }
            }
            builder
        }.toString()
    }

    private fun findColon(valueToCheck: String): Boolean {
        if (valueToCheck.isEmpty()) {
            return false
        }
        val currentFormattedChars = valueToCheck.trim { it <= ' ' }.toCharArray()
        val lastIndex = currentFormattedChars.size - 1

        return currentFormattedChars[lastIndex] == ':'
    }

    private fun indentValue(depth: Int): String {
        var indentedValue = ""
        for (j in 0 until depth) {
            indentedValue += " "
        }
        return indentedValue
    }

    companion object {
        private const val DEFAULT_INDENT = 4
    }
}
