package com.company;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nikman317 on 06.09.2018.
 */

enum SortType
{
    descendingly,
    ascending;
}

enum DataType
{
    integer,
    string;
}


public class Parser
{

    public Parser()
    {
        sT = SortType.ascending;
        outputFileName = "";
        inputFileNames = new ArrayList<String>();
    }

    public void parsArguments(String[] arguments) throws Exception
    {
        sT = SortType.ascending;
        outputFileName = "";
        inputFileNames.clear();

        if (arguments.length < 3)
            throw new ParseException("Too few arguments");

        for (String item : arguments)
        {
            if ((item.startsWith("-")) && (item.length() == 2))
            {
                switch (item)
                {
                    case "-i":
                        dT = DataType.integer;
                        break;
                    case "-s":
                        dT = DataType.string;
                        break;
                    case "-d":
                        sT = SortType.descendingly;
                        break;
                    case "-a":
                        sT = SortType.ascending;
                        break;
                    default:
                        throw new ParseException("unknow arguments");
                }
            }
            else
            {
                if (outputFileName.isEmpty())
                    outputFileName = item;
                else
                    inputFileNames.add(item);
            }
        }
    }

    public List<String> getInputFileNames()
    {
        return inputFileNames;
    }

    public String getOutputFileName()
    {
        return outputFileName;
    }

    public SortType getSortType()
    {
        return sT;
    }

    public DataType getDataType()
    {
        return dT;
    }

    private List<String> inputFileNames;
    private String outputFileName;
    private SortType sT;
    private DataType dT;
}
