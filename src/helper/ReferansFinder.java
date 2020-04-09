package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReferansFinder {
    public ReferansFinder() {
    }

    public ArrayList<String> assemblyReferansReturner(File csproj) throws FileNotFoundException {
        ArrayList<String> assReferences = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(csproj);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();

                if (line.trim().contains("<HintPath>")){
                    line = line.trim();
                    line = line.substring(line.lastIndexOf("\\")+1,line.length()-11);
                    assReferences.add(line);
                }
            }
            return assReferences;
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }

    }

    public ArrayList<String> projectReferansReturner(File csproj) throws FileNotFoundException {
        ArrayList<String> proReferences = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(csproj);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();

                if (line.trim().contains("<Name>")){
                    line = line.trim();
                    line = line.substring(6,line.length()-7);
                    proReferences.add(line);
                }
            }
            return proReferences;
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }

    }
}
