package test;

import java.io.File;

public class delTest
{

    public static void deleteFile(File file)
    {
        if(!file.exists()) {return;}// 不存在则返回空
        if(file.isFile()) {file.delete();}  //如果是文件，则删除该文件
        else //如果是一个文件夹，则对其中的子文件或子文件夹递归调用自身
        {
            File[] fileList = file.listFiles();
            for(int i = 0; i < fileList.length; i++)
            {
                deleteFile(fileList[i]);
            }
            file.delete();//d原本代码没有这条语句会导致仅将文件夹中的文件删除，所有文件夹都保留x
        }
    }

    public static void main(String[] args)
    {

    }
}
