/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.utils;

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
        Pattern regex = Pattern.compile("[\\[\\{*,\\}\\]]");

        String formattedValue = "";
        for (char singleChar : string.toCharArray()) {
            String currentChar = String.valueOf(singleChar);

            final Matcher matcher = regex.matcher(currentChar);

            if (matcher.find()) {
                if (currentChar.equals("}") || currentChar.equals("]")) {
                    formattedValue += "\n" + currentChar;
                } else {
                    formattedValue += currentChar + "\n";
                }
            } else {
                formattedValue += currentChar;
            }
        }
        return formattedValue;
    }

}
