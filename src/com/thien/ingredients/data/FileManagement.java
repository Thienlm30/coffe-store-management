package com.thien.ingredients.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class have funciton to save and access data of Oject from file
 * This class can be reuse in other project
 * @author Thienlm30
 */
public class FileManagement {
    
    /**
     * This function load data of Object to file
     * Can be reuse in other project
     * @param <E> Object type
     * @param list list Object
     * @param pathFile 
     * @return 
     */
    public <E> boolean loadListFromFile(List<E> list, String pathFile) {
        list.clear();
        File f = new File(pathFile);
        if (!f.exists()) {
            return false;
        }
        try (FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            if (f.length() == 0) {
                System.err.println("File is empty");
            }

            while (fis.available() > 0){
                @SuppressWarnings("unchecked")
               E c = (E) ois.readObject();
               list.add(c);
            }

            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + pathFile);
            return false;
        } catch (IOException | ClassNotFoundException e) {
            if (f.length() != 0) {
                System.err.println("Error reading from file: " + pathFile + " " + e.getMessage());
                return false;
            }
        } catch (NumberFormatException e) {
            // log error or throw exception 
            System.err.println(e.getMessage());
            return false;
        } catch (ClassCastException e) {
            // log a warning/error or hanlde exceptionn
            System.err.println("Unchecked cast from Object to T " + e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * This function save Object to file
     * Can be reuse in other project
     * @param <E> Object type
     * @param list list Object
     * @param pathFile
     * @param msg
     * @return 
     */
    public <E> boolean saveListToFile(List<E> list, String pathFile, String msg) {

        try {

            File f = new File(pathFile);
            if (!f.exists()) {
                System.out.println("Empty list");
                return false;
            }

            ObjectOutputStream fileOut;
            try (FileOutputStream fos = new FileOutputStream(f)) {
                fileOut = new ObjectOutputStream(fos);
                for (E item : list) {
                    fileOut.writeObject(item);
                }
                fileOut.close();
                fos.close();
                System.out.println(msg);
                return true; //successful save
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("luu thanh cong tu file management saveToFile()");
        return false;
    }
    
    /**
     * This method convert Java collection Map to List
     * This list use in method saveMapToFile
     * @param <K>
     * @param <V>
     * @param map
     * @return
     */
    public <K, V> List<V> convertMapToList(Map<K, V> map) {
        List<V> list = new ArrayList<>();
        for (V item : map.values()) {
            list.add(item);
        }
        return list;
    }

}
