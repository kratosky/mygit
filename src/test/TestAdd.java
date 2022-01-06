package test;

import commander.CLI;

public class TestAdd
{
    public static void main(String[] args)throws Exception
    {
        CLI.main("git init".split(" "));
        CLI.main("git add what".split(" "));
        CLI.main("git add tiger.txt".split(" "));
    }
}
