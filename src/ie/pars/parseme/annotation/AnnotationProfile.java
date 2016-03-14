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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Main class to build an annotation profile and do what ever you have to do
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class AnnotationProfile {

    private String name;
    private Collection<Annotation> annotaionList;
    private int totalSentNumber;
    private int totalTokenCount;

    /**
     *
     * @param annotationFile
     * @throws Exception
     */
    public AnnotationProfile(String annotationFile) throws Exception {
        name = new File(annotationFile).getName();
        loadAnnotations(annotationFile);
    }

    public String getName() {
        return name;
    }

    /**
     *
     * @param annotationFileTabSep
     * @throws Exception
     */
    private void loadAnnotations(String annotationFileTabSep) throws IOException, Exception {
        AnnotationStream annotationFile1 = new AnnotationStream(annotationFileTabSep);
        AnnotationBits annotationBit;
        // this is a temp map for building annotations from bits in the file
        TreeMap<String, Annotation> annotationMap = new TreeMap<>();
        while ((annotationBit = annotationFile1.read()) != null) {

            if (annotationBit.hasMweType1()) {
                Annotation a = new Annotation(annotationBit.getMweType1(),
                        annotationBit.getLineNumeber(),
                        annotationBit.getSentenceNumber()
                );
                a.setLineCountOffset(annotationBit.getLineNumeber());
                a.setIdentifierMWE(annotationBit.getMweIdentifier1());
                a.setSentenceNumber(annotationBit.getSentenceNumber());
                a.addTokenIdentifier(annotationBit.getTokenIdentifier());
                a.addTokenString(annotationBit.getToken());
                a.setKeyUsedDuringConstruction(annotationBit.getAnnotationBit1Key());

                if (annotationMap.containsKey(a.getKeyUsedDuringConstruction())) {
                    System.err.println("Annotation with double annotate root type or not root type?! or even using the same number for two different annotations... ");
                    throw new Exception("Errornous annotation in file " + annotationFileTabSep
                            + "\nat line number " + annotationBit.getLineNumeber());
                } else {
                    annotationMap.put(a.getKeyUsedDuringConstruction(), a);
                }
            } else if (annotationBit.hasMweIdentifier1()) {
                String annotationBit1Key = annotationBit.getAnnotationBit1Key();

                // do no need to check this, if the key is not already in the map then it is due to error in annotation
                if (!annotationMap.containsKey(annotationBit1Key)) {
                    throw new Exception("Errornous annotation in file " + annotationFileTabSep
                            + "\nat line number " + annotationBit.getLineNumeber()
                            + "\n**** Please solve the problem in line " + annotationBit.getLineNumeber()
                    );
                } else {
                    annotationMap.get(annotationBit1Key).addTokenIdentifier(annotationBit.getTokenIdentifier());
                    annotationMap.get(annotationBit1Key).addTokenString(annotationBit.getToken());
                }
            }
            // no else as this is possible
            if (annotationBit.hasMweType2()) {
                Annotation a = new Annotation(annotationBit.getMweType2(), annotationBit.getLineNumeber(), annotationBit.getSentenceNumber());
                a.setLineCountOffset(annotationBit.getLineNumeber());
                a.setIdentifierMWE(annotationBit.getMweIdentifier2());
                a.setSentenceNumber(annotationBit.getSentenceNumber());
                a.addTokenIdentifier(annotationBit.getTokenIdentifier());
                a.addTokenString(annotationBit.getToken());
                a.setKeyUsedDuringConstruction(annotationBit.getAnnotationBit2Key());
                if (annotationMap.containsKey(a.getKeyUsedDuringConstruction())) {
                    throw new Exception("Errornous annotation in file ");
                } else {
                    annotationMap.put(a.getKeyUsedDuringConstruction(), a);
                }
            } else if (annotationBit.hasMweIdentifier2()) {
                String annotationBit2Key = annotationBit.getAnnotationBit2Key();
                annotationMap.get(annotationBit2Key).addTokenIdentifier(annotationBit.getTokenIdentifier());
                annotationMap.get(annotationBit2Key).addTokenString(annotationBit.getToken());
            }
        }
        this.totalTokenCount = annotationFile1.getCountToken();
        this.totalSentNumber = annotationFile1.getCurrentSentenceNumber();
        this.annotaionList = annotationMap.values();
    }

    public int getTotalTokenCount() {
        return totalTokenCount;
    }

    public Collection<Annotation> getAnnotaionList() {
        return annotaionList;
    }

    public TreeMap<Annotation, Integer> getAnnotationFreqDist() {
        TreeMap<Annotation, Integer> annotationDist;
        annotationDist = new TreeMap(new AnnotationSpanComparator());
        for (Annotation a : annotaionList) {
            if (annotationDist.containsKey(a)) {
                Integer get = annotationDist.get(a);
                annotationDist.put(a, ++get);
            } else {
                annotationDist.put(a, 1);
            }

        }
        return annotationDist;
    }

    public int getTotalSentNumber() {
        return totalSentNumber + 1;
    }

    public Map<AnnotationSpan, Annotation> getAnnotationSpanMap() {
        Map<AnnotationSpan, Annotation> annotationSpanMap = new TreeMap<>();
        for (Annotation a : annotaionList) {
            annotationSpanMap.put(a.getAnnotationSpan(), a);
        }
        return annotationSpanMap;
    }

    /*
     LIST OF COMPARTOR USED
     */
    /**
     * used to find MWEs with identical text
     */
    class AnnotationSpanComparator implements Comparator<Annotation> {

        @Override
        public int compare(Annotation t, Annotation t1) {

            return AnnotationSpan.compareTokenList(t.getTokenNumberIdentifier(), t1.getTokenNumberIdentifier());
        }
    }

    /**
     * Method to compute IAA for MWE spans: this is the upperbound agreement
     *
     * @param referencAnnotationFile
     * @return
     * @throws Exception
     */
    public double computeFMScoreTermBoundary(AnnotationProfile referencAnnotationFile) throws Exception {

        //assume that *a* is the gold referencet, 
        int truePositives = 0; /// this means that a term is in both *a* and *b*
        // int trueNegative = 0; --> does not exist in our evaluation since only terms are annotated

        int falseNegatives = 0; /// count of terms apper in *a* but not in *b* is a false negative
        int falsePositives = 0; /// count of terms appear in *b* but not in *a* is a false positive
        Map<AnnotationSpan, Annotation> annotationSpanMapReference = referencAnnotationFile.getAnnotationSpanMap();
        // first true stuff
        for (AnnotationSpan span : annotationSpanMapReference.keySet()) {
            if (this.getAnnotationSpanMap().containsKey(span)) {
                truePositives++;
            } else {
                falseNegatives++;
            }
        }
//
        for (AnnotationSpan span : this.getAnnotationSpanMap().keySet()) {
            if (!annotationSpanMapReference.containsKey(span)) {
                falsePositives++;
            }
        }
//
        double precision = (truePositives * 1.0) / (truePositives + falsePositives);
        double recall = (truePositives * 1.0) / (truePositives + falseNegatives);
        double fscore = 0;
        if (recall + precision > 0) {
            fscore = (2 * (precision * recall)) / (precision + recall);
        }
        return fscore;

    }

    /**
     * Organize annotations by their type and then by their span (note that
     * spans are often unique)
     *
     * @return
     */
    public TreeMap<String, TreeMap<AnnotationSpan, Annotation>> getAnnotationByCategory() throws Exception {
        TreeMap<String, TreeMap<AnnotationSpan, Annotation>> annotationTypeSpanMap = new TreeMap<>();
        for (Annotation a : annotaionList) {
            if (annotationTypeSpanMap.containsKey(a.getType())) {
                if (annotationTypeSpanMap.get(a.getType()).containsKey(a.getAnnotationSpan())) {
                    throw new Exception("There is something wrong in the annotation file: " + this.name
                            + "\nfor the the term span of "
                            + a.getAnnotationSpan().toString() + " \n"
                            + "Each span can be annotated only as one type"
                    );
                }
                annotationTypeSpanMap.get(a.getType()).put(a.getAnnotationSpan(), a);

            } else {
                TreeMap<AnnotationSpan, Annotation> annotationSpanMap = new TreeMap<>();
                annotationSpanMap.put(a.getAnnotationSpan(), a);
                annotationTypeSpanMap.put(a.getType(), annotationSpanMap);
            }
        }

        return annotationTypeSpanMap;
    }

    public TreeMap<String, TreeMap<AnnotationSpan, Annotation>>
            getFilterdAnnotationByCategory(Set<AnnotationSpan> commonAnnotatedSegments) throws Exception {
        TreeMap<String, TreeMap<AnnotationSpan, Annotation>> annotationTypeSpanMap = new TreeMap<>();
        for (Annotation a : annotaionList) {
            if (commonAnnotatedSegments.contains(a.getAnnotationSpan())) {

                if (annotationTypeSpanMap.containsKey(a.getType())) {
                    if (annotationTypeSpanMap.get(a.getType()).containsKey(a.getAnnotationSpan())) {
                        throw new Exception("There is something wrong in the annotation file: " + this.name
                                + "\nfor the the term span of "
                                + a.getAnnotationSpan().toString() + " \n"
                                + "Each span can be annotated only as one type"
                        );
                    }
                    annotationTypeSpanMap.get(a.getType()).put(a.getAnnotationSpan(), a);

                } else {
                    TreeMap<AnnotationSpan, Annotation> annotationSpanMap = new TreeMap<>();
                    annotationSpanMap.put(a.getAnnotationSpan(), a);
                    annotationTypeSpanMap.put(a.getType(), annotationSpanMap);
                }
            }
        }

        return annotationTypeSpanMap;
    }

    public int countAllAnnatatedTerms() {
        return this.annotaionList.size();
    }

    public TreeSet<AnnotationSpan> getCommonMWESpan(AnnotationProfile secondProfile) {
        TreeSet<AnnotationSpan> commonSpans = new TreeSet<>();
        Map<AnnotationSpan, Annotation> annotationSpanMapSecondProf = secondProfile.getAnnotationSpanMap();
        for (AnnotationSpan as : annotationSpanMapSecondProf.keySet()) {
            if (this.getAnnotationSpanMap().containsKey(as)) {
                commonSpans.add(as);
            }
        }
        return commonSpans;
    }

    /**
     * Main method measuring kappa per category
     *
     * @param referencAnnotationFile
     * @return
     */
    public double getKappaForAnnotationPerCategory(AnnotationProfile referencAnnotationFile) throws Exception {

        double po = 0.0;
        double pe = 0.0;

        TreeSet<AnnotationSpan> commonMWESpan = getCommonMWESpan(referencAnnotationFile);
        TreeMap<String, TreeMap<AnnotationSpan, Annotation>> annotationMapTHIS = this.getFilterdAnnotationByCategory(commonMWESpan);
        TreeMap<String, TreeMap<AnnotationSpan, Annotation>> annotationMapRef = referencAnnotationFile.getFilterdAnnotationByCategory(commonMWESpan);

        TreeSet<String> allKeys = new TreeSet();
        allKeys.addAll(annotationMapTHIS.keySet());
        allKeys.addAll(annotationMapRef.keySet());

        for (String keyCategory : allKeys) {

            int countAllInRefForCategory = 0;
            int countAllInThisForCategory = 0;
            int countCommonForThisCategory = 0;

            if (annotationMapRef.containsKey(keyCategory)) {
                countAllInRefForCategory = annotationMapRef.get(keyCategory).size();
            }
            if (annotationMapTHIS.containsKey(keyCategory)) {
                countAllInThisForCategory = annotationMapTHIS.get(keyCategory).size();
            }

            if (annotationMapRef.containsKey(keyCategory) && annotationMapTHIS.containsKey(keyCategory)) {
                TreeMap<AnnotationSpan, Annotation> getVMEWRef = annotationMapRef.get(keyCategory);
                TreeMap<AnnotationSpan, Annotation> getVMEWThis = annotationMapTHIS.get(keyCategory);
                for (AnnotationSpan at : getVMEWRef.keySet()) {
                    if (getVMEWThis.containsKey(at)) {
                        if (getVMEWThis.get(at).getType().equalsIgnoreCase(keyCategory)) {
                            countCommonForThisCategory++;
                        }
                    }

                }
            }

  
            System.out.println("\t" + keyCategory + " A1#:" + countAllInRefForCategory
                    + " A2#" + countAllInThisForCategory + " AO#:"
                    + countCommonForThisCategory 
            );

            //int totalObservationInThisClass = countAllInRefForCategory + countAllInThisForCategory;
            po += countCommonForThisCategory;

            pe += (countAllInRefForCategory * countAllInThisForCategory);

        }
        po = po / commonMWESpan.size();
        pe = pe / (commonMWESpan.size() * commonMWESpan.size());
        double kappa = (po - pe) / (1 - pe);

        return kappa;
    }

}
