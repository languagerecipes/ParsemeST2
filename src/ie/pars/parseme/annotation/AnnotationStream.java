/* 
 * Copyright (C) 2016 Behrang QasemiZadeh <me at atmykitchen.info>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ie.pars.parseme.annotation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class AnnotationStream {

    private BufferedReader in;
    private String line;
    private int currentSentenceNumber;
    private    int lineCounter;
    private int countToken;
    private String fileName  ;

    public AnnotationStream(String fileName) throws IOException {
        lineCounter = 0;
        countToken = 0;
        this.fileName = fileName;
        in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        in.readLine();
        in.readLine();

    }

    public int getCountToken() {
        return countToken;
    }
    
    

    private boolean nextLine() throws Exception {
        try {
            line = in.readLine();
            if (line != null) {
                lineCounter++;
                if (line.trim().length() == 0) {
                    currentSentenceNumber++;
                    line = in.readLine();

                    lineCounter++;
                    if (line.trim().length() == 0) {
                        System.err.println("Error in file: " + this.fileName);
                        System.err.println("@ line number: " + lineCounter );
                        System.err.println("-->two consecutive empty lines are not allowed " );
                        throw new Exception("Error in line" + lineCounter + "  ... two consecutive empty lines are not allowed ");
                    }
                }else{
                    countToken++;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(AnnotationStream.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line != null;

    }

    public AnnotationBits read() throws IOException, Exception {
        if (nextLine()) {
            return AnnotationBits.fromString(line, currentSentenceNumber, lineCounter);
        }
        return null;

    }

    public String getLine() {
        return line;
    }

    public int getLineCounter() {
        return lineCounter;
    }

    public int getCurrentSentenceNumber() {
        return currentSentenceNumber;
    }

}
