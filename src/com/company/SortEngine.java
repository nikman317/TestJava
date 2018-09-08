package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 SortEngine - is main class that has interface with file-system,
 parser of command and also include merge-sort algorithm.

 analizeArguments - method which called Parser to pars command
 openInOutFile - method whick called FileManager to open files
 mergeSort - method which start sorting
 findNextLine - method whick find next line which would be written to out-file
 compare - method which compare to string(true - s2<s1(ascending) || s1<s2(descendingly))
 checkLines - check if line is unccorect in -i mod
 getNextLine - read next correct line from file
 */
public class SortEngine
{

    public SortEngine()
    {
        parser = new Parser();
        fileManager = new FileManager();
        lines = new HashMap<Integer, String>();
    }

    public boolean analizeArguments(String[] arguments) {
        try {
            parser.parsArguments(arguments);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public boolean openInOutFile() {
        try {
            fileManager.openFiles(parser.getOutputFileName(), parser.getInputFileNames());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void mergeSort()
    {
        int endsOfFiles = fileManager.getNumberOfFiles();
        lines.clear();
        dataType = parser.getDataType();
        sortType = parser.getSortType();

        String buffer;
        for (int i = 0; i < fileManager.getNumberOfFiles(); i++)
        {
            buffer = getNextLine(i);
            if (buffer != null)
                lines.put(i, buffer);
        }


        while (lines.size() != 0)
        {
            try
            {
                fileManager.writeLineToFile(findNextLine());
            }
            catch (Exception e)
            {
                System.out.println("can't write to out file");
                break;
            }
        }


    }

    public void closeAll()
    {
        lines.clear();
        fileManager.closeAll();
    }


    private String findNextLine()
    {
        int iterator = 0;
        while (!lines.containsKey(iterator) )
        {
            iterator++;
        }

        int iterator2 = iterator + 1;
        while (iterator2 < fileManager.getNumberOfFiles())
        {
            if (compare(lines.get(iterator), lines.get(iterator2)))
                iterator = iterator2;
            iterator2++;
        }

        String soughtLine = lines.remove(iterator);
        try
        {
            String buffer = getNextLine(iterator);
            if (buffer != null)
                lines.put(iterator, buffer);
        }
        catch (Exception e)
        {
            System.out.println("can't read line from file:" + iterator);
        }
        finally
        {
            return soughtLine;
        }
    }

    private boolean compare(String s1, String s2)
    {
        if (sortType == sortType.ascending) //по возрастанию, значит если s2<s1 -> true
        {
            if (dataType == dataType.string)
            {
                if (s1.compareTo(s2) > 0)
                    return true;
                else
                    return false;
            }
            if (dataType == dataType.integer) {
                if (Integer.parseInt(s1) > Integer.parseInt(s2))
                    return true;
                else
                    return false;
            }
        }
        else
        {
            if (dataType == dataType.string)
            {
                if (s1.compareTo(s2) < 0)
                    return true;
                else
                    return false;
            }
            if (dataType == dataType.integer) {
                if (Integer.parseInt(s1) < Integer.parseInt(s2))
                    return true;
                else
                    return false;
            }
        }
        return true;
    }

    private boolean checkLines(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch (Exception e)
        {
            System.out.println("can't translate line to integer:" + s);
            return false;
        }
    }

    private String getNextLine(int i)
    {
        try
        {
            String buffer = fileManager.getLineFromFile(i);
            while(buffer != null)
            {
                if (dataType == DataType.integer && (!checkLines(buffer)))
                {
                    buffer = fileManager.getLineFromFile(i);
                }
                else
                {
                    return buffer;
                }

            }
            return null;

        }
        catch (Exception e)
        {
            System.out.println("can't read line from file:" + i);
            return null;
        }
    }

    private DataType dataType;
    private SortType sortType;
    private HashMap<Integer, String> lines;
    private Parser parser;
    private FileManager fileManager;
}
