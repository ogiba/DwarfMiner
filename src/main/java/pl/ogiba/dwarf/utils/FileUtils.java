/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ogiba
 */
public class FileUtils {

    public static void saveFile(String directory, String fileName, String content) {
        File file = new File(directory);

        if (!file.exists()) {
            file.mkdir();
        }

        File fileToSave = new File(file, fileName);

        OutputStream out;
        try {
            out = new FileOutputStream(fileToSave);
            out.write(content.getBytes());
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String readFile(String directory, String fileName) {
        String content = null;
        File filew = new File(String.format("%s/%s", directory, fileName));
        try (BufferedReader br = new BufferedReader(new FileReader(filew))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            content = sb.toString();
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return content;
    }
}
