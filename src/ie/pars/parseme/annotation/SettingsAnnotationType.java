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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Behrang QasemiZadeh <zadeh at phil.hhu.de>
 */
public class SettingsAnnotationType {

    private Set<String> annotationTypes = null;
    private boolean restricted = true;

    final static String LANG_CATEGORY_RESOURCE = "resource/vmwe.cat";
    

    public SettingsAnnotationType(String language)  {
        annotationTypes = new HashSet<>();
        try {
            
            BufferedReader br = new BufferedReader(new FileReader(new File(LANG_CATEGORY_RESOURCE)));
            String line="";
            while((line=br.readLine())!=null){
              String thisLang=  line.split("=")[0].toLowerCase();
                if(thisLang.equalsIgnoreCase(language)){
                  String[] types = line.split("=")[1].split(",");
                  for(String type: types){
                      if(type.trim().length()>0){
                      annotationTypes.add(type.trim());}
                  }
                  break;
                }
            }
            br.close();
            System.out.println("* VMWE categories are loaded and limited to " + annotationTypes);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingsAnnotationType.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Setting for the specificed language are missing.\nPlease make sure that you have the file vmwe.cat in the resource folder and it includes language specific categories.\n");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(SettingsAnnotationType.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

//    public  SettingsAnnotationType(String file) throws FileNotFoundException, IOException {
//        File f = new File(file);
//        if (f.exists()) {
//            annotationTypes = new HashSet<>();
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] split = line.split(" ");
//                annotationTypes.addAll(Arrays.asList(split));
//            }
//            if (!annotationTypes.isEmpty()) {
//                restricted = true;
//                System.err.println("Annotation types are now limited to " + annotationTypes.size() + " class/classes");
//                br.close();
//            }
//        }
//        if (!restricted) {
//            System.out.println("No annotation type is given: your file is parsed without any restriction on their types.");
//        }
//
//    }

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
