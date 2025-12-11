package com.ini8labs.healthcare_portal.util;

import java.io.IOException;
import java.io.InputStream;

public class FileTypeDetector {
    private FileTypeDetector(){};

    public static boolean isPdf(InputStream in) throws IOException{
        in.mark(8);
        byte[] header = new byte[4];
        int r = in.read(header, 0, 4);
        in.reset();
        if(r<4)
            return false;
        return header[0] == '%' && header[1] == 'P' && header[2] == 'D' && header[3] == 'F';
    }

}
