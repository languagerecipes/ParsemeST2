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

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class ComputeIAA {

    public static void main(String[] sugary) throws Exception {
        if (sugary.length != 2) {
            System.err.println("Please provide input arguments, i.e., path for two input annotation files");
            return;
        }
        String file1 = sugary[0];// "Pilot ST - Farsi - Behrang (VO).txt";
        String file2 = sugary[1];//"Pilot ST - Farsi - Mojgan (BQ corrected format - VO).txt";

        System.out.println("Checking text in annotation files ...");
        boolean checkTextConsistency = GeneralChecks.checkTextConsistency(file1, file2);
        System.out.println("Done!");
        if (!checkTextConsistency) {
            System.out.println("Please fix reported inconsistencies prior to IAA computation ");

        } else {

            AnnotationProfile apb = new AnnotationProfile(file1);
            AnnotationProfile apb2 = new AnnotationProfile(file2);

            System.out.println(apb.getCommonMWESpan(apb2).size());
            System.out.println("--- IAAs");
            System.out.println("Only For Text Spans of VMWE (upper bound IAA using F-Score) : " + apb.computeFMScoreTermBoundary(apb2));
            System.out.println("Computing IAA using Kappa for Category Assignment: ");
            System.out.println("Summary k = " + apb2.getKappaForAnnotationPerCategory(apb));
            simpleReport(apb);
            simpleReport(apb2);
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
//        for(Annotation a: apb.getAnnotaionList()){
//            System.out.println(a.toStringDebug());
//        }

    }

}
