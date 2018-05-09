package pl.ogiba.dwarf.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.util.StringConverter;

/**
 *
 * @author robertogiba
 */
public class JsonConverter extends StringConverter<String> {

    private static final int DEFUALT_INDENT = 4;

    @Override
    public String toString(String object) {
        if (object == null) {
            return "";
        }

        return object;
    }

    @Override
    public String fromString(String string) {
        String formattedValue = formatJson(string, DEFUALT_INDENT);

        return formattedValue;
    }

    public String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonString);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String prettyJson;
        if (jsonElement.isJsonArray()) {
            JsonArray json = jsonElement.getAsJsonArray();

            prettyJson = gson.toJson(json);
        } else {
            JsonObject json = jsonElement.getAsJsonObject();

            prettyJson = gson.toJson(json);
        }

        return prettyJson;
    }

    public String formatJson(String jsonString, int indent) {
        Pattern regex = Pattern.compile("[\\[\\{\"*\",\\}\\]]");

        int nodeDepth = 0;
        boolean openQuotation = true;

        String formattedValue = "";
        for (char singleChar : jsonString.toCharArray()) {
            String currentChar = String.valueOf(singleChar);

            final Matcher matcher = regex.matcher(currentChar);

            if (matcher.find()) {
                switch (currentChar) {
                    case "]":
                        formattedValue += "\n" + currentChar;
                        break;
                    case "{":
                    case "[":
                        final boolean isColon = findColon(formattedValue);

                        if (!isColon) {
                            formattedValue += indentValue(indent * nodeDepth);
                        }

                        nodeDepth++;
                        formattedValue += currentChar + "\n";
                        openQuotation = true;
                        break;
                    case "\"":
                        if (openQuotation) {
                            openQuotation = false;
                            formattedValue += indentValue(indent * nodeDepth);
                        }

                        formattedValue += currentChar;
                        break;
                    case "}":
                        nodeDepth--;
                        formattedValue += "\n";
                        formattedValue += indentValue(indent * nodeDepth);
                        formattedValue += currentChar;
                        break;
                    case ",":
                        formattedValue += currentChar + "\n";
                        openQuotation = true;
                        break;
                    default:
                        formattedValue += indentValue(indent * nodeDepth);
                        formattedValue += currentChar + "\n";
                        break;
                }
            } else {
                formattedValue += currentChar;
            }
        }
        return formattedValue;
    }

    private boolean findColon(String valueToCheck) {
        if (valueToCheck.length() < 1) {
            return false;
        }
        char[] currentFormattedChars = valueToCheck.trim()
                .toCharArray();
        final int lastIndex = currentFormattedChars.length - 1;

        return currentFormattedChars[lastIndex] == ':';
    }

    private String indentValue(int depth) {
        String indentedValue = "";
        for (int j = 0; j < depth; j++) {
            indentedValue += " ";
        }
        return indentedValue;
    }
}
