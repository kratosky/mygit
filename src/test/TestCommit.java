package test;

import commander.CLI;

public class TestCommit
{
    public static void main(String[] args) throws Exception
    {
        CLI.main("git init".split(" "));
        CLI.main("git add what".split(" "));
        CLI.main("git commit".split(" "));

        try
        {
            Thread.currentThread().sleep(3000);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        CLI.main("git add tiger.txt".split(" "));
        CLI.main("git commit".split(" "));
    }


}
