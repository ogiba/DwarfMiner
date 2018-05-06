/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
        String formattedValue = toPrettyFormat(string);//formattedValue.replaceAll(closeRegex, closeReplacement);
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

}
