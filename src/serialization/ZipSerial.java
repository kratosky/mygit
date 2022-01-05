package serialization;

import java.io.*;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 这个类用于对对象的压缩存入，与读取
 */
abstract public class ZipSerial
{
    /**把一个可序列化的对象压缩写入制定路径下的文件中
     *
     * @param path
     * @param o
     */
    public static void compressWriteObj(String path, Serializable o)
    {
        try
        {
            ObjectOutputStream oo = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File(path))));
            oo.writeObject(o);
            oo.flush();
            oo.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 从给定路径中，以给定类型解压缩读出对象
     * @param filepath
     * @param expectedClass
     * @param <T>
     * @return
     */
    public static <T extends Serializable> T readCompressFile(String filepath, Class<T> expectedClass)
    {
        try
        {
            ObjectInputStream oi =new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File(filepath))));
            T obj = expectedClass.cast(oi.readObject());
            oi.close();
            return obj;
        }
        catch(IOException | ClassCastException | ClassNotFoundException e)//这里是短路或 | ，无论前面的异常是否发生，后面的异常也会被捕获
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


}
