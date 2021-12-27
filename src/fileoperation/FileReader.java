package fileoperation;

import java.io.*;
import java.util.ArrayList;

public class FileReader
{
    /**
     * Get every line in the given file.
     * d获得给定文件内容value的每一行，以Arraylist<String>形式返回，元素为每一行字符串x
     * @param value
     * @return
     */
    public static ArrayList<String> readByBufferReader(String value) throws FileNotFoundException
    {
        //d将文件内容（字符串）转换为byte序列并读入输入流，后面会自动转化会字符串
        InputStream is = new ByteArrayInputStream(value.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ArrayList<String> stringList =  new ArrayList<>();
        try
        {
            //dline为每行的字符串x
            String line=br.readLine();
            while (line!=null)
            {
                stringList.add(line);
                line=br.readLine();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //d最后关上输入流x
            try
            {
                br.close();
                is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return stringList;
    }

    /**
     * Get the format of the object. The param "line" is a line in a tree object, like"100644 blob *** a.txt"
     * @param line
     * @return
     */
    public static String readObjectFmt(String line)
    {
        String [] arr = line.split("\\s+");//正则表达式，一次或多次出现空格dx
        return arr[1];//获得第二位，也就是object的类型dx
    }

    /**
     * Get the key of the object.
     * @param line
     * @return
     */
    public static String readObjectKey(String line)
    {
        String [] arr = line.split("\\s+");
        return arr[2];//获得第三位，也就是object的key（也就是哈希值）dx
    }

    /**
     * Get the filename of the object.
     * @param line
     * @return
     */
    public static String readObjectFileName(String line)
    {
        String [] arr = line.split("\\s+");
        return arr[3];//dx获得第四位，也就是object的文件名
    }

    /**
     * Get the tree from a commit value.
     * 传入的value是一个元素为字符串数组的数组
     * 以下均是对commit的操作
     * @param value
     * @return
     * @throws FileNotFoundException
     */
    //读出其包含的提交树
    public static String readCommitTree(String value) throws FileNotFoundException
    {
        ArrayList<String> stringList = readByBufferReader(value);
        String [] arr = stringList.get(0).split("\\s+");
        return arr[1];
    }
    //读出上一次commit
    public static String readCommitParent(String value) throws FileNotFoundException
    {
        ArrayList<String> stringList = readByBufferReader(value);
        String [] arr = stringList.get(1).split("\\s+");
        return (arr.length > 1) ? arr[1] : null;
    }
    //读出作者dx
    public static String readCommitAuthor(String value) throws FileNotFoundException
    {
        ArrayList<String> stringList = readByBufferReader(value);
        String [] arr = stringList.get(2).split("\\s+");
        String author = arr[1];
        for(int i = 2; i < arr.length; i++){ author += " " + arr[i]; }
        return author;
    }
    //读出提交者
    public static String readCommitter(String value) throws FileNotFoundException
    {
        ArrayList<String> stringList = readByBufferReader(value);
        String [] arr = stringList.get(3).split("\\s+");
        String committer = arr[1];
        for(int i = 2; i < arr.length; i++){ committer += " " + arr[i]; }
        return committer;
    }
    //读出提交信息
    public static String readCommitMsg(String value) throws FileNotFoundException
    {
        ArrayList<String> stringList = readByBufferReader(value);
        return stringList.get(4);
    }

    /**
     * d静态方法，获得文件里的内容x
     * @param file
     * @return String
     * @throws IOException
     */

    public static String getContent(File file) throws IOException
    {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s = new String();
        String filevalue = new String();
        while((s = bufferedreader.readLine()) != null)
        {
            filevalue += s;
        }
        return filevalue;
    }

    /*
     * @description, 输入一个file对象，返回String类型的文件内容
     */
    public static String getValue(File file) throws Exception
    {
        String value = "";
        FileInputStream is = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int tempReadLength;
        while((tempReadLength=is.read(buffer))>0)
        {
            value += new String(buffer,0,tempReadLength);
        }
        is.close();
        return value;
    }

    /*
     * @description, 在指定的路径下，读取指定文件的内容，以字符串的方式返回。
     */
    public static String getValue(String absolutePath, String fileName) throws Exception
    {
        File file = new File(absolutePath + File.separator + fileName);
        return getValue(file);
    }
}
