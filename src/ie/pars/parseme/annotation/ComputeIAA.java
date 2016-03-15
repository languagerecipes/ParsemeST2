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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class ComputeIAA {

    static boolean verbose = false;

    public static void main(String[] sugary) throws FileNotFoundException {
        try {
            if (sugary.length < 2) {
                System.err.println("Please provide input arguments, i.e., path for two input annotation files");
                return;
            }
            if (sugary.length == 3) {
                if (sugary[2].contains("verbose")) {
                    verbose = true;
                }
            }
            String file1 = sugary[0];// "Pilot ST - Farsi - Behrang (VO).txt";
            String file2 = sugary[1];//"Pilot ST - Farsi - Mojgan (BQ corrected format - VO).txt";

            System.out.println("Checking text in annotation files ...");
            boolean checkTextConsistency;

            checkTextConsistency = GeneralChecks.checkTextConsistency(file1, file2);

            if (!checkTextConsistency) {
                System.out.println("Please fix reported inconsistencies prior to IAA computation ");

            } else {
                System.out.println("Done checking text... starting IAA computation!");
                AnnotationProfile apb = new AnnotationProfile(file1);
                AnnotationProfile apb2 = new AnnotationProfile(file2);

                System.out.println(apb.getCommonMWESpan(apb2).size());
                System.out.println("--- IAAs");
                System.out.println("Only For Text Spans of VMWE (upper bound IAA using F-Score) : " + apb.computeFMScoreTermBoundary(apb2));
                System.out.println("Computing IAA using Kappa for Category Assignment: ");
                System.out.println("Summary k = " + apb2.getKappaForAnnotationPerCategory(apb));
                simpleReport(apb);
                simpleReport(apb2);
                if (verbose) {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("MWE-difflist.txt"), StandardCharsets.UTF_8);
                    
                    PrintWriter printer = new PrintWriter(outputStreamWriter);
                    printer.println("#list of annotated  VMWEs that appear only in the annotation file " + apb.getName());
                    for (AnnotationSpan a : apb.getDiffMWESpan(apb2)) {
                        printer.println(apb.getAnnotationSpanMap().get(a).toStringDebug());
                    }
                     printer.println("#list of annotated  VMWEs that appear only in the annotation file " + apb2.getName());
                    for (AnnotationSpan a : apb2.getDiffMWESpan(apb)) {
                        printer.println(apb.getAnnotationSpanMap().get(a).toStringDebug());
                    }
                    printer.close();
                }
            }
            
        } catch (Exception ex) {
            System.err.println("Exit with error ... please fix the reported problems and try to run the program again.");
            // Logger.getLogger(ComputeIAA.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Have a nice day!");
       
    }

    /**
     * Meain method to load annotations from a text file
     *
     * @param annotationFileTabSep
     * @return
     * @throws Exception
     */
    private static void simpleReport(AnnotationProfile apb) throws Exception {
        System.out.println("--- annotation profile for ->  " + apb.getName());
        System.out.println("Number of annotations: " + apb.getAnnotaionList().size());
        System.out.println("Sentenc: " + apb.getTotalSentNumber());
        System.out.println("Token: " + apb.getTotalTokenCount());
        System.out.println("Average Number of annotated VMWE per Sentence: " + (apb.getAnnotaionList().size() * 1.0 / apb.getTotalSentNumber()));
        System.out.println("MWEs with Identical Surface Structure (i.e., only strings and not types): " + apb.getAnnotationFreqDist().size());

        if (verbose) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(apb.getName() + "-MWEList.txt"), StandardCharsets.UTF_8);
            PrintWriter printer = new PrintWriter(outputStreamWriter);
            printer.println("#list of annotated  VMWEs are");
            for (Annotation a : apb.getAnnotaionList()) {
                printer.println(a.toStringDebug());
            }
            printer.close();
        }

    }

}
