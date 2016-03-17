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
public class AnnotationBits {

    private String tokenIdentifier;
    private String token;
    private String mweIdentifier1;
    private String mweIdentifier2;
    private String mweType1X;
    private String mweType2;
    final int NO_SENTEMCE_MARKER = -2;
    private int sentenceNumber = NO_SENTEMCE_MARKER;
    private int lineNumeber = -2;

    /**
     * Just read and keep what we are interested in and ignore the rest of the
     * content
     *
     * @param line
     * @param sentenceNumber
     * @return
     */
    /**
     * for possible annotation in the first col
     *
     * @return
     */
    public String getAnnotationBit1Key() {
        return toStringBitKey(sentenceNumber + "") + toStringBitKey(mweIdentifier1) + toStringBitKey("-");
    }

    public void setLineNumeber(int lineNumeber) {
        this.lineNumeber = lineNumeber;
    }

    public int getLineNumeber() {
        return lineNumeber +2;
    }

    public String getAnnotationBit2Key() {
        return toStringBitKey(sentenceNumber + "") + toStringBitKey("-") + toStringBitKey(mweIdentifier2);
    }

    private String toStringBitKey(String bit) {
        String delimiter = "-";
        if (bit == null) {
            return "-" + delimiter;
        } else {
            return bit + delimiter;
        }
    }

    public static AnnotationBits fromString(String line, int sentenceNumber, int lineNumber) throws Exception {

        if (line.trim().length() != 0) {
            AnnotationBits lb = new AnnotationBits();
            String[] splitBits = line.split("\t");
            if (splitBits.length < 2) {
                System.err.println("* Fix at line number " + (lineNumber+2 ) + " which maps to sentence number " + sentenceNumber + " --> " + line);
                throw new Exception("Something wrong in line " + (lineNumber +2));
            }
            lb.tokenIdentifier = splitBits[0];
            lb.token = splitBits[1];
            if (splitBits.length > 4) {
                String trim = splitBits[4].trim();
                if (trim.length() > 0) {
                    lb.mweIdentifier1 = trim;
                }
            }
            if (splitBits.length > 5) {
                String trim = splitBits[5].trim();
                if (trim.length() > 0) {
                    lb.mweType1X = trim.toUpperCase();
                }
            }

            if (splitBits.length > 7) {
                String trim = splitBits[7].trim();
                if (trim.length() > 0) {
                    lb.mweIdentifier2 = trim;
                }
            }
            if (splitBits.length > 8) {
                String trim = splitBits[8].trim();
                if (trim.length() > 0) {
                    lb.mweType2 = trim.toUpperCase();
                }
            }
            lb.sentenceNumber = sentenceNumber;
            lb.lineNumeber = lineNumber;
            return lb;

        } else {
            System.err.println("Error in parsing line " + lineNumber );
            throw new Exception("Error in parsing line");
        }
    }

        public String toDebugString() {
            String delimiter = "\t";
        return toStringBit(tokenIdentifier, delimiter)
                + toStringBit(token, delimiter)
                // +nsb
                + toStringBit(mweIdentifier1, delimiter)
                + toStringBit(mweType1X, delimiter)

                + toStringBit(mweIdentifier2, delimiter)
                + toStringBit(mweType2, delimiter);

    }

//    public String toString(String delimiter) {
//        return toStringBit(tokenIdentifier, delimiter)
//                + toStringBit(token, delimiter)
//                // +nsb
//                + delimiter // for nsb
//                + delimiter // for mwt
//                + toStringBit(mweIdentifier1, delimiter)
//                + toStringBit(mweType1X, delimiter)
//                + delimiter // for the sel
//                + toStringBit(mweIdentifier2, delimiter)
//                + toStringBit(mweType2, delimiter)
//                + delimiter; // for the
//
//    }

    final static public String replaceStringForNullValueInAnnotation = "";

    private String toStringBit(String bit, String delimiter) {
        if (bit == null) {
            return replaceStringForNullValueInAnnotation + delimiter;
        } else {
            return bit + delimiter;
        }
    }

    public String getMweIdentifier1() {
        return mweIdentifier1;
    }

    public String getMweIdentifier2() {
        return mweIdentifier2;
    }

    public String getMweType1() {
        return mweType1X;
    }

    public String getMweType2() {
        return mweType2;
    }

    public String getToken() {
        return token;
    }

    public String getTokenIdentifier() {
        return tokenIdentifier;
    }

    public int getSentenceNumber() {
        return sentenceNumber;
    }

    public boolean hasMweIdentifier1() {
        return mweIdentifier1 != null;
    }

    public boolean hasMweIdentifier2() {
        return mweIdentifier2 != null;
    }

    public boolean hasMweType1() {
        return mweType1X != null;
    }

    public boolean hasMweType2() {
        return mweType2 != null;
    }

    public boolean hasToken() {
        return token != null;
    }

    public boolean hasTokenIdentifier() {
        return tokenIdentifier != null;
    }

    public boolean hasSentenceNumber() {
        return sentenceNumber != NO_SENTEMCE_MARKER;
    }

    public void setMweIdentifier1(String mweIdentifier1) {
        this.mweIdentifier1 = mweIdentifier1;
    }

    public void setMweIdentifier2(String mweIdentifier2) {
        this.mweIdentifier2 = mweIdentifier2;
    }

    public void setMweType1(String mweType1) {
        this.mweType1X = mweType1;
    }

    public void setMweType2(String mweType2) {
        this.mweType2 = mweType2;
    }

    public void setSentenceNumber(int sentenceNumber) {
        this.sentenceNumber = sentenceNumber;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    public void setTokenIdentifier(String tokenIdentifier) {
//        this.tokenIdentifier = tokenIdentifier;
//    }
}
