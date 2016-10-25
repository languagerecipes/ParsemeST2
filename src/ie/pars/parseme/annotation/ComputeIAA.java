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

    static void help() {
        System.err.println("** To use the system for IAA computation use the following arguments:");
        System.err.println("Please provide this obligatory input arguments :\n"
                + "\tArg1) the language you use using lang:LANGUAGE e.g., lang:Brazilian, lang:English, lang:Polish, etc."
                + "\tArg2) path for the first annotation file as source:PATH_TO_FILE\n"
                + "\tArg3) path for the second annotation file as target:PATH_TO_FILE\n"
                + "\nif the paths contains a space-char, then please use quotation marks around them,\n\t e.g., \"Pilot ST - French - Agata Savary.tsv\"");
        System.err.println("Optionally, you can ask for "
                + "\t a detailed report by asserting the word verbose as an argument \n"
        );

        System.err.println("** To use the system for checking your annotation file use the following arguments:\n"
                + "\tArg1) the language you use using lang:LANGUAGE e.g., lang:Brazilian, lang:English, lang:Polish, etc."
                + "\tArg2) path for the first annotation file as source:PATH_TO_FILE\n");

    }

    public static void main(String[] sugary) throws FileNotFoundException {
        // try {
       // String annotationVaocabFile = "";
       String lang = null;
       String source = null;
       String target = null;
        if (sugary.length < 2) {
            help();
            return;
        }
        for (int i = 0; i < sugary.length; i++) {
            if (sugary[i].equalsIgnoreCase("verbose")) {
                System.out.println("Set to verbose mode.");
                verbose = true;
            } else {
                if (sugary[i].toLowerCase().startsWith("lang:")) {
                    lang = sugary[i].split(":")[1];
                    System.out.println("Language set to " + lang);
                } else if (sugary[i].toLowerCase().startsWith("source:")) {
                    source = sugary[i].split(":")[1];
                    System.out.println("Reading annotation file (1) from " + source);
                } else if (sugary[i].toLowerCase().startsWith("target:")) {
                    target = sugary[i].split(":")[1];
                    System.out.println("Reading annotation file (2) from  " + target);
                }
            }
        }
        if(lang==null){
            System.out.println("Please set the input language ");
            System.out.println("Here is a short guide for you:");
            help();
        }
        if(source==null && target==null){
            System.out.println("Please provide path to annotation files. Follow the instruction below: ");
            help();
        }
        if(source!=null && target==null){
            System.out.println("You have requested annotation file checking for " + source);
            TestOneAnnotationFile.process(source, lang);
            return;
        }else if(source!=null && target==null){
            System.out.println("You have requested annotation files checking and IAA computation.... ");
        }
        
        String file1 = source;// "Pilot ST - Farsi - Behrang (VO).txt";
        String file2 = target; //"Pilot ST - Farsi - Mojgan (BQ corrected format - VO).txt";
        SettingsAnnotationType sa = new SettingsAnnotationType(lang);
        if (file1.equals(file2)) {
            System.out.println("You have requested a check on " + file1);
            
        }

        try {
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

        } catch (Exception ex) {

            System.err.println("Exit with error ... please fix the reported problems and try to run the program again.");
            // Logger.getLogger(ComputeIAA.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Here are the code lines that produces error: \n");
            System.out.println(ex);
        }
        System.out.println("Have a nice day!");

    }

}
