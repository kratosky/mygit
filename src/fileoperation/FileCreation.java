package fileoperation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import gitobject.*;



public class FileCreation
{
	/**
	 * Create a file and write text
	 * @param parentPath
	 * @param filename
	 * @param text
	 * @throws IOException
	 */
    //d传入文件所在文件夹，文件名，文本内容创建文件x
    public static void createFile(String parentPath, String filename, String text) throws IOException
    {
        //d如果传入文件父目录本身不是文件夹，抛出错误x
        if(!new File(parentPath).isDirectory())
        {
            throw new IOException("The path doesn't exist!");
        }
        //d如果传入的文件路径是一个文件夹，则报错x
        File file = new File(parentPath + File.separator + filename);
        if(file.isDirectory())
        {
            throw new IOException(filename + " already exists, and it's not a file.");
        }
        //d除此之外的情况，新建或覆盖原来的同名文件，写入text中的内容x
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(text);
        fileWriter.close();

    }
    
    /**
     * Create a dir.
     * @param parentPath
     * @param paths
     * @throws IOException
     */
    //d传入文件所在文件夹，文件夹名，文本内容创建文件x
    public static void createDirectory(String parentPath, String... paths) throws IOException
    {
        //d如果传入文件父目录本身不是文件夹，抛出错误x
        if(!new File(parentPath).isDirectory())
        {
            throw new IOException("The path doesn't exist!");
        }
        //假如存在在父目录中存在同名文件夹则删除
        String path = parentPath;
        FileDeletion.deleteFile(path + File.separator + paths[0]);//第一个参数是要创建文件夹嵌套的根文件夹
        //将路径参数用文件分隔符连在一起，生成有效的路径，并用该有效路径创建文件夹套娃
        for(int i = 0; i < paths.length; i++)
        {
            path += File.separator + paths[i];
        }
        new File(path).mkdirs();//一路的文件夹都被创建
    }

    /*
     * @description, 在指定的路径下，生成指定名称的文件
     */
    public static void generateFile(String absolutePath, String fileName)
    {
        File file= new File(absolutePath, fileName);
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    /*
     * 把value写进File
     */
    public static void putValueIntoFile(String absolutePath, String fileName,String value)
    {
        try
        {
            File file = new File(absolutePath,fileName);
            FileWriter os = new FileWriter(file);
            os.write(value);
            os.flush(); // 清空缓冲区
            os.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}


