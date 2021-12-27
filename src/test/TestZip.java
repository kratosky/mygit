package test;

import serialization.ZipSerial;
import gitobject.Blob;

public class TestZip
{
    //经过测试，决定用zip替代原来的Zlib进行对象的压缩，与读入

    public static void main (String[] args)
    {
        try
        {
            //Blob blob = Blob.deserialize("c0f4f99da4e8b9c43f65.Blob");
            Blob blob = new Blob();
            ZipSerial.compressWriteObj("compressfile.Blob",blob);


            try
            {
                Thread.currentThread().sleep(3000);

            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println(new Blob());
            Blob newblob = ZipSerial.readCompressFile("compressfile.Blob", Blob.class);
            System.out.println(newblob);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }



}
