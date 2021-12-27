package serialization;

import java.io.*;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 这个类用于对对象的压缩存入，与读取
 */
public class ZipSerial
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


    /**
     * d将当前暂存区中的indexmap反序列化取出x
     * @return
     * @throws Exception
     */
    public static Map<String, String> getBranchMap() throws Exception
    {
        //dx：反序列化index文件夹（暂存区）中的"trackedBlob"文件，生成键为相对路径加文件名，值为其在“.jit/objects”文件夹中对应文件名的Map
        File branchMapFile = new File(".jit"+File.separator+"branches"+File.separator+"branchMap");
        ObjectInputStream oi =new ObjectInputStream(new FileInputStream(branchMapFile));
        Map<String, String> branchMap = (Map<String, String>)oi.readObject();
        oi.close();
        return branchMap;
    }

    /**
     * 将传入的Map更新到index中
     * @param branchMap
     * @throws Exception
     */
    public static void setBranchMap(Map<String, String> branchMap) throws Exception
    {
        File branchMapFile = new File(".jit"+File.separator+"branches"+File.separator+"branchMap");
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(branchMapFile));
        oo.writeObject(branchMap);
        oo.flush();
        oo.close();
    }

}
