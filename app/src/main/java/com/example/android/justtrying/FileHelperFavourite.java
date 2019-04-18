package com.example.android.justtrying;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FileHelperFavourite {

    private static final String FILENAME = "favinfo.dat";
    private static final String FILENAME2 = "favinfo.dat";

//    public static void writeData(ArrayList<String> items, Context context) {
//        try{
//            FileOutputStream fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(items);
//            oos.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static ArrayList<String> readData(Context context) {
//        ArrayList<String> itemList = null;
//
//        try {
//            FileInputStream fis = context.openFileInput(FILENAME);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            itemList = (ArrayList<String>) ois.readObject();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return itemList;
//    }


    public static void writeData(String textToAdd, Context context) {

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME,Context.MODE_APPEND);
            fos.write("\n".getBytes());
            fos.write(textToAdd.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static ArrayList<String> readData(Context context) {
        FileInputStream fis = null;

        ArrayList<String> items = new ArrayList<String>();

        try {
            fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String passText;


            while( (text = br.readLine()) != null) {
                sb.append(text).append("\n");
                passText = sb.toString();
                items.add(passText);
                sb.delete(0,sb.length());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return items;

    }


    public static void removeData (String textToAdd, Context context) {

        FileInputStream fis = null;
        FileOutputStream fos = null;
        String passText;

        try {
            fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String newText = null;



            while( (text = br.readLine()) != null) {
                sb.append(text).append("\n");
                passText = sb.toString();
                if(passText.equals(textToAdd)) {
                    Toast.makeText(context, "FOUND!", Toast.LENGTH_SHORT).show();
                    sb.delete(0,sb.length());
                    continue;
                }
                sb.append(text).append("\n");
                newText = sb.toString();
            }

            File file = new File(FILENAME);
            file.delete();


//            fos = context.openFileOutput(FILENAME,0);
//
//            fos = context.openFileOutput(FILENAME,MODE_PRIVATE);
//            fos.write(newText.getBytes());




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis!=null && fos!=null) {
                try {
                    fos.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }




    }

    public static int check (String word, Context context) {

        FileInputStream fis = null;

        try {
            fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String passText;


            while( (text = br.readLine()) != null) {
                sb.append(text);
                passText = sb.toString();
                if(passText.equals(word))
                    return 1;
                sb.delete(0,sb.length());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;


    }


}
