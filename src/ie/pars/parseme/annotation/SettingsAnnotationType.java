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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Behrang QasemiZadeh <zadeh at phil.hhu.de>
 */
public class SettingsAnnotationType {

    private Set<String> annotationTypes = null;
    private boolean restricted = false;

    public  SettingsAnnotationType(String file) throws FileNotFoundException, IOException {
        File f = new File(file);
        if (f.exists()) {
            annotationTypes = new HashSet<>();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                annotationTypes.addAll(Arrays.asList(split));
            }
            if (!annotationTypes.isEmpty()) {
                restricted = true;
                System.err.println("Annotation types are now limited to " + annotationTypes.size() + " class/classes");
                br.close();
            }
        }
        if (!restricted) {
            System.out.println("No annotation type is given: your file is parsed without any restriction on their types.");
        }

    }

    public boolean accepts(String annotationType) {
        if(this.restricted){
          if (this.annotationTypes.contains(annotationType)) {
              return true;
          }else{
              return false;
          }
        }else{
            return true;
        }
    }

}
