package com.company;

import java.awt.*;
import java.io.Console;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        SortEngine SE = new SortEngine();
        Scanner in = new Scanner(System.in);
        String[] arguments = args;

        while (!SE.analizeArguments(arguments))
        {
            System.out.println("Please enter correct arguments\n");
            if (in.hasNext())
                arguments = in.nextLine().split(" ");
        }

        if (SE.openInOutFile())
        {
            SE.mergeSort();
            SE.closeAll();
        }
    }
}
