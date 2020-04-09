package helper;


import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class FileFinder {

    public FileFinder() {
    }

    public ArrayList<File> getDirectories(File root){
        try {
            ArrayList<File> directories = new ArrayList<File>();
            // gelen dosya yolundaki tum dosya ve klasorleri diziye at
            File[] liste = root.listFiles();
            // eger dosya veya klasor varsa
            if (liste != null) {
                if (liste.length > 0) {
                    // tum diziyi gez
                    for (int i = 0; i < liste.length; i++) {
                        // eger dizide klasor varsa bunu klasor listesine ekle
                        // klasor icinde de herhangi bir klasor varsa bulmak icin
                        // fonksiyonu tekrar cagir
                        if (liste[i].isDirectory()) {
                            // bulunan dosyayi diziye ekle
                            //directories.add(liste[i]);
                            directories.addAll(getDirectories(liste[i]));
                        }
                    }
                }
            }
            directories.add(root);
            return directories;
        } catch (Exception er) {
            System.out.println(er.getLocalizedMessage());
            return null;
        }

    }

    public ArrayList<File> getSlnFiles(ArrayList<File> directories) {
        ArrayList<File> slnFiles = new ArrayList<File>();
        if (directories!=null){
            for (File directory:directories) {
                try {
                    FilenameFilter textFilter = new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".sln");
                        }
                    };
                    File[] files = directory.listFiles(textFilter);

                    for (File file : files) {
                        slnFiles.add(file);
                    }
                } catch (Exception er) {
                    System.out.println(er.getLocalizedMessage());
                }
            }
        }
        return slnFiles;
    }
    public ArrayList<File> getCsprojFiles(ArrayList<File> directories) {
        ArrayList<File> csprojFiles = new ArrayList<File>();
        if (directories!=null){
            for (File directory:directories) {
                try {
                    FilenameFilter textFilter = new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".csproj");
                        }
                    };
                    File[] files = directory.listFiles(textFilter);

                    for (File file : files) {
                        csprojFiles.add(file);
                    }
                } catch (Exception er) {
                    System.out.println(er.getLocalizedMessage());
                }
            }
        }
        return csprojFiles;
    }
}
