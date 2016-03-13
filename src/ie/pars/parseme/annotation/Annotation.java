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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class Annotation //implements Comparable<Annotation> {
{

    private String identifierMWE;
    private final List<String> tokenNumberIdentifier; //this cannot be integer because of token numbers such as x-y
    private final List<String> tokenStringList;
    private String type;
    private int sentenceNumber;
    private String keyUsedDuringConstruction;
    private int lineCountOffset;
    

    public void setKeyUsedDuringConstruction(String keyUsedDuringConstruction) {
        this.keyUsedDuringConstruction = keyUsedDuringConstruction;
    }

    public String getKeyUsedDuringConstruction() {
        return keyUsedDuringConstruction;
    }

    public void setIdentifierMWE(String identifierMWE) {
        this.identifierMWE = identifierMWE;
    }

    public void setSentenceNumber(int sentenceNumber) {
        this.sentenceNumber = sentenceNumber;
    }

    public void setLineCountOffset(int lineCountOffset) {
        this.lineCountOffset = lineCountOffset;
    }
    

    public void setType(String type) {
        this.type = type;
    }

    public Annotation(String type, int offset, int sentNumber) {
        this.tokenNumberIdentifier = new ArrayList<>();
        this.tokenStringList = new ArrayList();
        this.type = type;
        this.lineCountOffset = offset;
        this.sentenceNumber = sentNumber;
    }

    public void addTokenIdentifier(String token) {
        this.tokenNumberIdentifier.add(token);
    }

    public void addTokenString(String tokenString) {
        this.tokenStringList.add(tokenString);
    }

    public String getIdentifierMWE() {
        return identifierMWE;
    }

    public int getSentenceNumber() {
        return sentenceNumber;
    }

    public List<String> getTokenNumberIdentifier() {
        return tokenNumberIdentifier;
    }

    public List<String> getTokenStringList() {
        return tokenStringList;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (String token : tokenStringList) {
            b.append(token).append(" ");
        }
        return b.toString() + " / " + this.type;
    }

  
    public String toStringDebug() {
        StringBuilder b = new StringBuilder();
        StringBuilder t = new StringBuilder();
        for (int i = 0; i < tokenStringList.size(); i++) {
            b.append(tokenStringList.get(i)).append(" ");
            t.append(tokenNumberIdentifier.get(i)).append(" ");
        }
        return b.toString() + "\t / " + this.type +"\t token offset:" +t.toString()+ "\t / L:" + this.lineCountOffset +"\t / S:" + this.sentenceNumber;
    }



    public AnnotationSpan getAnnotationSpan() {
        return new AnnotationSpan(tokenNumberIdentifier, sentenceNumber,this.lineCountOffset);
    }

}
