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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Behrang QasemiZadeh <zadeh at phil.hhu.de>
 */
public class ComputeIAA {

    static boolean verbose = false;

    static void help(){
        System.err.println("Please provide this obligatory input arguments :\n"
                
                + "\tArg1) path for the first annotation file\n\tArg2) path for the second annotation file"
                + "\nif the paths contains a space-char, then please use quotation marks around them,\n\t e.g., \"Pilot ST - French - Agata Savary.tsv\"");
        System.err.println("Optionally, you can ask for some more using these"
                + " arguments after the provided Arg1 and Arg2:\n"
                + "\tArg3) verbose report by asserting the word verbose in front of the file names \n"
                + "\tArg4) ask for restricting your annotation vocabulary by this argument:  Vocab:Your-annotation-vocab-file,\n"
                + "\t\t e.g., Vocab:english-file.txt in which the english-file.txt is the file that contains your vocab,"
                + "\n\t\t e.g., LVC, ID, etc.");
        System.err.println("*About the vocab file: create a text file and and write each of the types separated by space-char or by new lines.");
        System.err.println("*If you like to check only one file, for the first Arg1 and Arg2 referring to the same file."
                + "\n\t e.g., \"Pilot ST - French - Agata Savary.tsv\" \"Pilot ST - French - Agata Savary.tsv\" ");

    }
    public static void main(String[] sugary) throws FileNotFoundException, Exception {
       // try {
       String annotationVaocabFile="";
            if (sugary.length < 2) {
                help();
                return;
            }
            for (int i = 2; i < sugary.length; i++) {
                if(sugary[i].equalsIgnoreCase("verbose")){
                    System.out.println("Set to verbose mode.");
                }else
                if(sugary[i].toLowerCase().startsWith("vocab:")){
                    System.out.println("Annotation vocan is now restricted.");
                    annotationVaocabFile= sugary[i].split(":")[1];
                }
            }
        
            
            
            String file1 = sugary[0];// "Pilot ST - Farsi - Behrang (VO).txt";
            String file2 = sugary[1]; //"Pilot ST - Farsi - Mojgan (BQ corrected format - VO).txt";
            SettingsAnnotationType sa  = new SettingsAnnotationType(annotationVaocabFile);
            if(file1.equals(file2)){
                System.out.println("You have requested a check on " + file1);
                TestOneAnnotationFile.process(file2, annotationVaocabFile);
                return;
            }
            
            
            System.out.println("Checking text in annotation files ...");
            boolean checkTextConsistency;

            checkTextConsistency = GeneralChecks.checkTextConsistency(file1, file2);

            if (!checkTextConsistency) {
                System.out.println("Please fix reported inconsistencies prior to IAA computation ");

            } else {
                System.out.println("Done checking text... starting IAA computation!");
                AnnotationProfile apb = new AnnotationProfile(file1, sa);
                AnnotationProfile apb2 = new AnnotationProfile(file2, sa);

                System.out.println(apb.getCommonMWESpan(apb2).size());
                System.out.println("--- IAAs");
                System.out.println("Only For Text Spans of VMWE (upper bound IAA using F-Score) : " + apb.computeFMScoreTermBoundary(apb2));
                System.out.println("Computing IAA using Kappa for Category Assignment: ");
                System.out.println("Summary k = " + apb2.getKappaForAnnotationPerCategory(apb));
                Util.simpleReport(apb, verbose);
                Util.simpleReport(apb2, verbose);
                if (verbose) {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("-VMWE-diff-list.txt"), StandardCharsets.UTF_8);

                    PrintWriter printer = new PrintWriter(outputStreamWriter);
                    printer.println("-----");
                    printer.println("#list of annotated  VMWEs that appear only in the annotation file " + apb.getName());
                    for (AnnotationSpan a : apb.getDiffMWESpan(apb2)) {
                        printer.println(apb.getAnnotationSpanMap().get(a).toStringDebug());
                    }
                    printer.println("\n-----");
                    printer.println("#list of annotated  VMWEs that appear only in the annotation file " + apb2.getName());
                    for (AnnotationSpan a : apb2.getDiffMWESpan(apb)) {
                        printer.println(apb2.getAnnotationSpanMap().get(a).toStringDebug());
                    }
                    printer.flush();
                    printer.close();
                }
            }

//        } catch (Exception ex) {
//
//            System.err.println("Exit with error ... please fix the reported problems and try to run the program again.");
//            // Logger.getLogger(ComputeIAA.class.getName()).log(Level.SEVERE, null, ex);
//            System.err.println("Here are the code lines that produces error: \n");
//            System.out.println(ex);
//        }
//        System.out.println("Have a nice day!");

    }

}
