//$Id$
package mrs.pos;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * DESCRIPTION GOES HERE<br>
 * <br>
 * Copyright (c) 2015 MICROS Retail
 *
 * @author yaseen
 * @created Nov 30, 2015
 * @version $Revision$
 */
public class MakeMyLifeSimpler {
  
  public static void main(String gg[]){
    try{
      File file = new File("../mrs_pos/src/mrs/pos/Abc.txt");
      File generatedFile = new File("../mrs_pos/src/mrs/pos/generated.data");
      RandomAccessFile generatedRandomAccessFile = new RandomAccessFile(generatedFile,"rw");
      RandomAccessFile raf = new RandomAccessFile(file, "r");
      String status = "property";
      while(raf.getFilePointer()<raf.length()){
        String name = raf.readLine();
        
        StringBuilder newName= new StringBuilder();
       int i=0;
       while(i<name.length()){
         
           newName.append(Character.toLowerCase(name.charAt(i)));
         if(name.charAt(i) == '_'){
           newName.append(name.charAt(i+1));
           i++;
         }
         i++;
       }
       String propertyName = newName.toString().replace("_", "");
       String propertyNameForGetterSetter = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
       generatedRandomAccessFile.seek(generatedRandomAccessFile.length());
       if(status.equals("property")){
         generatedRandomAccessFile.writeBytes(getPropertyName(propertyName)+"\n");
         if(raf.getFilePointer()==raf.length()){
           raf.seek(0);
           status = "setter";
           continue;
         }
       }
       if(status.equals("setter")){
         generatedRandomAccessFile.writeBytes(getSetter(propertyNameForGetterSetter, propertyName, name));
         if(raf.getFilePointer()==raf.length()){
           raf.seek(0);
           status = "getter";
           continue;
         }
       }
       if(status.equals("getter")){
         generatedRandomAccessFile.writeBytes(getGetter(propertyNameForGetterSetter, propertyName));
       }
      }
      raf.close();
      generatedRandomAccessFile.close();
    }catch(Throwable t){
      System.out.println(t);
    }
    
    
    /*String name = "ABC_DEF_Gh";
    
    StringBuilder newName= new StringBuilder();
    boolean foundUnderScore = false;
   int i=0;
   while(i<name.length()){
     
       newName.append(Character.toLowerCase(name.charAt(i)));
     if(name.charAt(i) == '_'){
       newName.append(name.charAt(i+1));
       i++;
     }
     i++;
   }
   String propertyName = newName.toString().replace("_", "");
   String propertyNameForGetterSetter = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);*/
   /*System.out.println(getPropertyName(propertyName));
   System.out.println(getSetter(propertyNameForGetterSetter, propertyName, name));
   System.out.println(getGetter(propertyNameForGetterSetter, propertyName));*/
  }
  public static String getSetter(String propertyNameForGetterSetter, String propertyName, String name){
    StringBuilder setterString = new StringBuilder();
    setterString.append("\n");
    setterString.append("@XmlElement(name=\""+name+"\")");
    setterString.append("\n");
    setterString.append("public void set"+propertyNameForGetterSetter+"(String "+propertyName+"){");
    setterString.append("\n");
    setterString.append("\t this."+propertyName+" = "+propertyName);
    setterString.append("\n");
    setterString.append("}");
    return setterString.toString();
  }
  
  public static String getGetter(String propertyNameForGetterSetter, String propertyName){
    StringBuilder getterString = new StringBuilder();
    getterString.append("\n");
    getterString.append("public String get"+propertyNameForGetterSetter+"(){\n");
    getterString.append("\t return "+propertyName+"\n");
    getterString.append("}");
    return getterString.toString();
  }
  
  public static String getPropertyName(String propertyName){
    return "private String "+propertyName+";";
  }
}
