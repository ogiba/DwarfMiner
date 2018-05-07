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
//        Pattern regex = Pattern.compile("[\\[\\{*,\\}\\]]");
//        String regex = "[\\[\\{*,\\}\\]]";

//        String openRegex = "[\\[\\{,]";
//        String openReplacement = "$0\n";
//
//        String formattedValue = string.replaceAll(openRegex, openReplacement);
//
//        String closeRegex = "[\\]\\}]";
//        String closeReplacement = "\n$0";
        String formattedValue = toPrettyFormat(string);
//        String formattedValue = formatJson(string);
        //        for (char singleChar : string.toCharArray()) {
        //            String currentChar = String.valueOf(singleChar);
        //
        //            final Matcher matcher = regex.matcher(currentChar);
        //
        //            if (matcher.find()) {
        //                if (currentChar.equals("}") || currentChar.equals("]")) {
        //                    formattedValue += "\n" + currentChar;
        //                } else {
        //                    formattedValue += currentChar + "\n";
        //                }
        //            } else {
        //                formattedValue += currentChar;
        //            }
        //        }
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

//        String openRegex = "[\\[\\{,]";
//        String openReplacement = "$0\n";
//
//        String closeRegex = "[\\]\\}]";
//        String closeReplacement = "\n$0";
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
                        for (int i = 0; i < nodeDepth; i++) {
                            formattedValue += "\t";
                        }
                        nodeDepth++;
                        formattedValue += currentChar + "\n";
                        break;
                    case "\"":
                        if (openQuotation) {
                            openQuotation = false;
                            for (int i = 0; i < nodeDepth; i++) {
                                formattedValue += "\t";
                            }
                            formattedValue += currentChar;
                        }
                        break;
                    default:
                        for (int i = 0; i < nodeDepth; i++) {
                            formattedValue += "\t";
                        }
                        nodeDepth--;
                        formattedValue += currentChar + "\n";
                        openQuotation = true;
                        break;
                }
            } else {
                formattedValue += currentChar;
            }
        }
        return formattedValue;
    }
}
