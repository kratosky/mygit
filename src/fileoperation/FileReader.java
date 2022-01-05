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

}
