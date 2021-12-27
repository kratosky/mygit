package test;

import fileoperation.FileDeletion;

import java.io.*;

public class Test
{
    public static void main(String[] args)throws Exception
    {

        File dir = new File("./GPA");
        dir.mkdirs();
        File dir2 = new File(".\\GPA\\shit\\what/the");
        dir2.mkdirs();
        File dir3 = new File("."+File.separator+"what"+File.separator+"fuck"+File.separator+".."+File.separator+"tim");
        dir3.mkdirs();
        System.out.println(dir3.getCanonicalPath());
        System.out.println(File.separator);

        String[] name = dir3.getCanonicalPath().replace(File.separator, " ").split(" ");
        System.out.println(name[name.length-1]);






    }
}
