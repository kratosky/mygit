package test;

import commander.CLI;

public class TestGC
{
    public static void main(String[] args)throws Exception
    {
        CLI.main("git init".split(" "));
        CLI.main("git add tiger.txt".split(" "));
        CLI.main("git commit".split(" "));

        CLI.main("git checkout -b newbranch".split(" "));
        CLI.main("git add what".split(" "));
        CLI.main("git commit".split(" "));

        CLI.main("git checkout master".split(" "));
        CLI.main("git add what/farm".split(" "));
        CLI.main("git commit".split(" "));


        CLI.main("git branch -d newbranch".split(" "));

        try
        {
            Thread.currentThread().sleep(6000);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        CLI.main("git gc".split(" "));

    }
}
