package fileoperation;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileDeletion
{
    /**
     * Delete file.
     * @param file
     */
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

    /**
     * 清空传入文件夹
     * @param file
     */
    public static void emptyDirec(File file)
    {
        if((!file.exists())||(!file.isDirectory()))
        {
            System.out.println("文件夹 "+file.getName()+"不存在！");
        }
        deleteFile(file);
        file.mkdirs();
    }

    /**
     * 重载，清空传入文件夹路径
     * @param path
     */
    public static void emptyDirec(String path)
    {
        emptyDirec(new File(path));
    }

	/**
	 * 进行重载，对以字符串传进来的路径转化成File类并调用上方函数处理
	 * @param path
	 */
    public static void deleteFile(String path)
    {
        deleteFile(new File(path));
    }

    /**
     * Delete the content of a file.
     * 通过写空内容来实现
     * @param file
     */
    public static void deleteContent(File file)
    {
        try
        {
            //如果传入文件不存在，则新创建一个
            if(!file.exists())
            {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            //写入空字符串
            fileWriter.write("");
            fileWriter.flush();//把write中的内容冲到文件内
            fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
