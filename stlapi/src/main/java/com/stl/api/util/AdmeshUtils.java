package com.stl.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

public class AdmeshUtils {

//    public static void main(String[] args) {
//        BigDecimal volume = getVolume("testfile.stl");
//        if (volume != null) {
//            System.out.println("got result string: " + volume.toPlainString());
//        }
//    }

    public static BigDecimal getVolume(String absolutePath) {
        BigDecimal volume = null;
        
        ProcessBuilder pb = new ProcessBuilder("admesh", absolutePath);
        Process p = null;
        try {
            p = pb.start();
            int returnValue = p.waitFor();
            String resultString = slurp(p.getInputStream(), 512);

            String currentDir = System.getProperty("user.dir");
            System.out.println("currentDir= " + currentDir);
            System.out.println("Returned without exception. Return code: " + returnValue);
            // TODO parse return code, need 0... 1 means failure
//            System.out.println("got result string: " + resultString);
            int indexOfVolume = resultString.indexOf("Volume   :  ");
            if (indexOfVolume > 0) {
                //System.out.println("index of volume: " + indexOfVolume);
//                System.out.println("Volume:" + resultString.substring(indexOfVolume+12, indexOfVolume+21) );
                String volumeString = resultString.substring(indexOfVolume+12, indexOfVolume+21);
                volume = new BigDecimal(volumeString);
            }
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
            System.out.println("Critical error - IO error or interrupted...");
        }
        return volume;
    }
    
    private static String slurp(final InputStream is, final int bufferSize) {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(is, "UTF-8")) {
            for (;;) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
        }
        catch (UnsupportedEncodingException ex) {
            /* ... */
        }
        catch (IOException ex) {
            /* ... */
        }
        return out.toString();
    }
}
