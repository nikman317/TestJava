package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikman317 on 06.09.2018.
 */
public class FileManager
{
    public FileManager()
    {
        inFileReaders = new ArrayList<BufferedReader>();
    }

    public void openFiles(String out, List<String> in) throws Exception
    {
        inFileReaders.clear();
        try
        {
            File file = new File(out);
            if (file.createNewFile())
                outFileWriter = new BufferedWriter(new FileWriter(file));
            else
                throw new Exception("can't create file - " + out);

            for (String item : in)
            {
                try
                {
                    BufferedReader br = new BufferedReader (new FileReader(item));
                    inFileReaders.add(br);
                }
                catch(IOException e)
                {
                    throw new Exception("can't open file - " + item);
                }
            }
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public String getLineFromFile(int i) throws Exception
    {
        try
        {
            String s = inFileReaders.get(i).readLine();
            return s;
        }
        catch (IOException e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public void writeLineToFile(String s) throws Exception
    {
        try
        {
            outFileWriter.write(s + "\n");
        }
        catch (IOException e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public int getNumberOfFiles()
    {
        return inFileReaders.size();
    }

    public void closeAll() {
        try
        {
            outFileWriter.close();
            for (BufferedReader item : inFileReaders)
                item.close();

        }
        catch (Exception e)
        {

        }
    }

    private ArrayList<BufferedReader> inFileReaders;
    private BufferedWriter outFileWriter;


}
