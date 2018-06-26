package isssr.ticketsystem.util;

import org.apache.tomcat.util.codec.binary.Base64;


import java.io.FileOutputStream;


public class FileManager {

    public static void convertStringToFile(String base64,String fileName,String relativePath) {
        try {
            String[] tokens = base64.split(",");
            String[] tokens_bis = tokens[0].split("/");
            String[] tokens_ter = tokens_bis[1].split(";");
            String format = tokens_ter[0];
            byte[] imageByteArray = decodeFile(tokens[1]);

            FileOutputStream fileOutFile = new FileOutputStream(relativePath+fileName+"."+format);
            fileOutFile.write(imageByteArray);
            fileOutFile.flush();
            fileOutFile.close();
        } catch (Exception e) {
            System.err.println("ERRORE");
            e.printStackTrace();
        }
    }


    public static byte[] decodeFile(String fileDataString) {
        return Base64.decodeBase64(fileDataString.getBytes());
    }
}
