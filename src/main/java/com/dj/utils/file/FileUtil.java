package com.dj.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    //파일 입력
    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<String>();
        
        try{
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    //파일 출력
    public static void writeFile(String path, List<String> lines) {
        try{
            File file = new File(path);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            if(file.isFile() && file.canWrite()) {
                for(String line: lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //exe 파일 입력
    public static List<String> exeInput(String path) {
        List<String> lines = new ArrayList<String>();

        try {
            Process process = new ProcessBuilder(path).start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    //exe 파일 출력
    public static void exeOutput(String path, List<String> lines) {

        BufferedWriter bw;
        try {
            Process process = new ProcessBuilder(path).start();
            OutputStream os = process.getOutputStream();
            OutputStreamWriter osr = new OutputStreamWriter(os);
            bw = new BufferedWriter(osr);

            for(String line: lines) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}