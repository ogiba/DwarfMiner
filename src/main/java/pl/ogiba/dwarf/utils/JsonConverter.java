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

    @Override
    public String toString(String object) {
        if (object == null) {
            return "";
        }

        return object;
    }

    @Override
    public String fromString(String string) {
        String formattedValue = formatJson(string);

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

    public String formatJson(String jsonString) {
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
                            for (int i = 0; i < nodeDepth; i++) {
                                formattedValue += "\t";
                            }
                        }

                        nodeDepth++;
                        formattedValue += currentChar + "\n";
                        openQuotation = true;
                        break;
                    case "\"":
                        if (openQuotation) {
                            openQuotation = false;
                            for (int i = 0; i < nodeDepth; i++) {
                                formattedValue += "\t";
                            }
                        }

                        formattedValue += currentChar;
                        break;
                    case "}":
                        nodeDepth--;
                        formattedValue += "\n";
                        for (int i = 0; i < nodeDepth; i++) {
                            formattedValue += "\t";
                        }
                        formattedValue += currentChar;
                        break;
                    case ",":
                        formattedValue += currentChar + "\n";
                        openQuotation = true;
                        break;
                    default:
                        for (int i = 0; i < nodeDepth; i++) {
                            formattedValue += "\t";
                        }
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
}
