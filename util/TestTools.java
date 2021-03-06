package util;

import java.io.*;
import java.util.*;

public class TestTools {
//cd based on String path

    public static String cd(String currentDir, String destinationDir) {
        ProcessBuilder pb = new ProcessBuilder("cd", currentDir);
        pb.directory(new File(destinationDir));
//  System.out.println("StartDir: " + currentDir + "; EndDir: " +  pb.directory().getAbsolutePath());
        return pb.directory().getAbsolutePath();
    }

//cd based on abstract File path
    public static File cd(File cwd, String destinationPath) {
        String cwdPath;
        File nwd;
        String nwdPath;
        try {
            cwdPath = cwd.getAbsolutePath();
        } catch (NullPointerException npe) //  NB - ProcessBuilder default is to return a null  
        //  pointer for the abstract path to indicate that it 
        //  is using System.Properties "user.dir", i.e., the 
        //  current system working directory; hence the
        //  critical need to handle a NullPointerException.
        //  Also returns a null pointer if the directory
        //  doesn't exist. 
        {
            cwdPath = System.getProperty("user.dir");
//    System.out.println("cdFile - current working directory null");
            cwd = new File(cwdPath);
            cwdPath = cwd.getAbsolutePath();
        }
//  System.out.println(cwdPath);
//  dir(cwd);

        try {
            nwd = new File(destinationPath);
            nwdPath = nwd.getAbsolutePath();
//    debug code - to confirm correct directory
//    dir(nwd);
        } catch (NullPointerException npe) {
//    System.out.println("cdFile - new working directory null");
            nwd = cwd;
            nwdPath = nwd.getAbsolutePath();

//    debug code - to confirm correct directory
//    dir(nwd);
        }
//  System.out.println(nwdPath);  

        return nwd;
    }

//directory listing method  
    public static void dir(File cwd) {
        String cwdPath;
        String[] directoryListing;
        try {
            cwdPath = cwd.getAbsolutePath();
            directoryListing = cwd.list();
            for (String element : directoryListing) {
                System.out.println(element);
            }
        } catch (NullPointerException npe) //  NB - ProcessBuilder default is to return a null  
        //  pointer for the abstract path to indicate that it 
        //  is using System.Properties "user.dir", i.e., the 
        //  current system working directory; hence the
        //  critical need to handle a NullPointerException.
        //  Also returns a null pointer if the directory
        //  doesn't exist.  
        {
            cwdPath = System.getProperty("user.dir");
            System.out.println("dir - current working directory null");
            cwd = new File(cwdPath);
            cwdPath = cwd.getAbsolutePath();
            directoryListing = cwd.list();
            for (String element : directoryListing) {
                System.out.println(element);
            }
        }
//  System.out.println(cwdPath);
        return;
    }

//String Parser - blank String delimiter
    public static List<String> parseLine(String line) {
        Scanner inputLine = new Scanner(line);
        List<String> tokens = new ArrayList<String>();
//    System.out.print("token ArrayList: ");
        while (inputLine.hasNext()) {
            String nextToken = inputLine.next();
//      System.out.print(nextToken + " ");
            tokens.add(nextToken);
        }
//    System.out.println();
        return tokens;
    }
    
    public static boolean isWindows() {
        if (getOSName().startsWith("Windows")) {
            return true;
        } 
        
        return false;
    }
    
    private static String getOSName() {
        return System.getProperty("os.name");
    }
    
    // deletes directory and removes all children
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    // deletes directory and all children then remakes the directory in a fresh state
    public static void cleanDir(File dir) {
        deleteDir(dir);
        dir.mkdir();
    }
}
