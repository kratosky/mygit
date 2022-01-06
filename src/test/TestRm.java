package test;

import commander.CLI;

import java.io.File;

public class TestRm
{

    public static void main(String[] args)throws Exception
    {
        CLI.main("git init".split(" "));
        CLI.main("git add what".split(" "));
        CLI.main("git add tiger.txt".split(" "));

        try
        {
            Thread.currentThread().sleep(5000);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        CLI.main("git rm tiger.txt".split(" "));
        try
        {
            Thread.currentThread().sleep(2000);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        CLI.main("git rm what".split(" "));


    }
}
