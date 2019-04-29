package com.example.android.justtrying;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
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
import static android.support.constraint.Constraints.TAG;

public class FileHelperFavourite {

    //definition of the file names used to stopre the information
    private static final String FILENAME = "favinfo.dat";
    private static final String FILENAME2 = "recentsinfo.dat";


    public static void writeDataRecents(String textToAdd, Context context) {

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME2,Context.MODE_APPEND);
            fos.write(textToAdd.getBytes());
            fos.write("\n".getBytes());
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

    public static ArrayList<String> readDataRecents(Context context) {
        FileInputStream fis = null;

        ArrayList<String> items = new ArrayList<String>();

        try {
            fis = context.openFileInput(FILENAME2);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String passText;


            while( (text = br.readLine()) != null) {
                sb.append(text);
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



    public static void writeDataFav(String textToAdd, Context context) {

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME,Context.MODE_APPEND);
            fos.write(textToAdd.getBytes());
            fos.write("\n".getBytes());
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


    public static ArrayList<String> readDataFav(Context context) {
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

        String passText = null;
        int size,i;
        ArrayList<String> newFileFav = new ArrayList<>();

        try {
            fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String newText = null;



            while( (text = br.readLine()) != null) {
                sb.append(text);
                passText = sb.toString();

                if(passText.equals(textToAdd)) {
                    Log.e(TAG, "removeData: inside check condition ");
                    sb.delete(0,sb.length());
                    passText = null;
                    continue;
                }
                if(passText == null)
                    continue;
                else
                    newFileFav.add(passText +"\n");

                sb.delete(0,sb.length());
            }



            fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);

            size = newFileFav.size();
            for(i= 0; i< size; i++) {
                passText = newFileFav.get(i);
                fos.write(passText.getBytes());
            }


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
