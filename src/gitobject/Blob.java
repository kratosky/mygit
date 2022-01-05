package gitobject;

import fileoperation.FileReader;
import sha1.SHA1;

import java.io.*;

public class Blob extends GitObject
{

    public Blob(){}


    /**
     * Constructing blob object from a file
     * @param file
     * @throws Exception
     */
    public Blob(File file, String relevantPath) throws Exception
    {
        try
        {
            this.relevantPath = relevantPath;
            this.fmt = "Blob";
            this.mode = "100644";
            this.value = FileReader.getContent(file);//获得文件内容x
            this.key = SHA1.hash(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void toFile(String parentPath)
    {
        try
        {
            //System.out.println(relevantPath);
            String[] relepath = this.relevantPath.split("/");
            //将路径参数用文件分隔符连在一起，生成有效的路径，并用该有效路径创建文件夹套娃
            for(int i = 0; i < relepath.length-1; i++)
            {
                parentPath += "/" + relepath[i];
            }
            //System.out.println(parentPath);
            new File(parentPath).mkdirs();//一路的文件夹都被创建
            //d如果传入的文件路径是一个文件夹，则报错x
            String filename = parentPath + '/' + relepath[relepath.length-1];
            //System.out.println(filename);


            File file = new File(filename);
            //d除此之外的情况，新建或覆盖原来的同名文件，写入Blob对象value中的内容x
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(this.getValue());
            fileWriter.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void toFile()
    {
        try
        {
            toFile("");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Deserialize a blob object with filename and its path.
     * @param filename
     * @return
     * @throws IOException
     */
    public static Blob deserialize(String filename) throws Exception
    {
            return (Blob)GitObject.deserialize(filename);

    }


    @Override
    public String toString()
    {
        return "100644 blob " + " " + key + " " + value + " " + generated_time +  " ";
    }

}
