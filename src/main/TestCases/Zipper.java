package main.TestCases;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    public void zipFile() throws IOException {
        String s  = GenerateReport.htmlText;
        this.getClass().getResource("./Report.zip");
        File f = new File("./Report.zip");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
        ZipEntry e = new ZipEntry("htmlrepo.html");
        out.putNextEntry(e);

        byte[] data = s.getBytes();
        out.write(data, 0, data.length);
        out.closeEntry();
        System.out.println("Zip created");
        out.close();
    }
}
