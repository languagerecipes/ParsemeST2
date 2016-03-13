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

import java.util.List;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class AnnotationSpan implements Comparable<AnnotationSpan> {

    private List<String> tokenSpan;
    private final int sentenceNumber;
    private int lineCountOffset ;

    public AnnotationSpan(List<String> tokenSpan, int sentenceNumber, int lineCountOffset) {
        this.tokenSpan = tokenSpan;
        this.sentenceNumber = sentenceNumber;
        this.lineCountOffset = lineCountOffset;
    }

    public static int compareTokenList(
            List<String> first,
            List<String> second
    ) {
        int firstSize = first.size();
        int secondSize = second.size();
        int size = Math.min(firstSize, secondSize);
        for (int i = 0; i < size; ++i) {
            String t1i = first.get(i);
            String t2i = second.get(i);
            int result = t1i.compareTo(t2i);
            if (result != 0) {
                return result;
            }
        }
        return firstSize - secondSize;
    }

    @Override
    public int compareTo(AnnotationSpan t) {
        int compareTokenList = compareTokenList(this.tokenSpan, t.tokenSpan);
        if (compareTokenList == 0) {
            //if (this.sentenceNumber != t.sentenceNumber) {
            return Integer.compare(this.sentenceNumber, t.sentenceNumber);
            //}

        } else {
            return compareTokenList;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String tokenString : tokenSpan) {
            sb.append(">").append(tokenString);
        }
        return "offset:  " + this.lineCountOffset+" sentence:" + this.sentenceNumber + ">tokens" + sb.toString();
    }

    

}
