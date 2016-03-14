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

import java.util.TreeSet;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class GeneralChecks {

     public static boolean checkTextConsistency(String annoFile1, String annoFile2) throws Exception {
        AnnotationStream annotationFile1 = new AnnotationStream(annoFile1);
        AnnotationStream annotationFile2 = new AnnotationStream(annoFile2);
        AnnotationBits read1;
        AnnotationBits read2;
       // boolean areTokensTheSame = true;
        while ((read1 = annotationFile1.read()) != null) {
            read2 = annotationFile2.read();
            
            if (!read1.getToken().equals(read2.getToken())) {
                System.err.println("Error line " + annotationFile2.getLineCounter());
                System.err.println("Some tokens are not the same, i.e., in line " + annotationFile2.getLineCounter());
                System.err.println( "* In "+ annoFile1 + " the input is --> " + read1.getToken() );
                System.err.println( "* But in "+ annoFile2 + " the input is --> " + read2.getToken() );
                System.err.println("Please fix this problem!");
                
                return false;
            }
        }
      return true;
    }
}

