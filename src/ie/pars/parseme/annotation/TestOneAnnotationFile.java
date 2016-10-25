/*
 * Copyright (C) 2016 Behrang QasemiZadeh <zadeh at phil.hhu.de>
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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Behrang QasemiZadeh <zadeh at phil.hhu.de>
 */
public class TestOneAnnotationFile {
    public static void process(String file, String annotationTypeSetting) {

        try {
            SettingsAnnotationType annotateTypeSetting = new SettingsAnnotationType(annotationTypeSetting);
            System.out.println("Parsing your annotation file..."
                    + "\nIf there are errors the parser will throw you an Exception! ");
            String fileName = file;
            AnnotationProfile ap = new AnnotationProfile(fileName, annotateTypeSetting);
            System.out.println("Your file seems to be ok! Here are some stat for you: ");
            
            Util.simpleReport(ap, true);
            
            System.out.println("If you have passed the -verbose parameter in your command, then further information and the list of MWEs can be found in " + fileName + "-MWEList.txt");
        } catch (Exception ex) {
            System.out.println("Your file has not passed the test, please fix the errors and redo the test. ");
        }

    }

    public static void main(String[] args) throws Exception {

        TestOneAnnotationFile.process("Pilot2 ST - Template.tsv", "English");

    }
}
