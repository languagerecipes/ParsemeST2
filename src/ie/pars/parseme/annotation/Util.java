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

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Behrang QasemiZadeh <zadeh at phil.hhu.de>
 */
public class Util {
     /**
     * Util method to generate a report from an annotation file with the format
     * described for PARSEME Platinum Files
     *
     * Using parameter verbose a report will be dumped in a file: The file name 
     * is AnnotationFileName+ -MWEList.txt
     * @param apb
     * @param verbose
     * @return
     * @throws Exception
     */
    public static void simpleReport(AnnotationProfile apb, boolean verbose) throws Exception {
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
