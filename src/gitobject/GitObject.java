package gitobject;
import jitinitiation.JitInitiation;

import serialization.ZipSerial;
import java.util.Date;
import java.text.SimpleDateFormat;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public abstract class GitObject implements Serializable
{
    protected String fmt;                  //d type of object, for example, blob or tree, noted by x.
    protected String key;                  //key of object,哈希码
    protected String mode;                 //d mode of object, for example 100644 for blob and 040000 for tree. noted by x.
    protected static String path = JitInitiation.getGitDir() + File.separator + "objects";   //.jit文件夹中的objects文件夹之中
    protected String value;                //value of object
    protected String generated_time;        // it is used to record the time when the file is generated.
    protected String relevantPath;          // blob会用到，记录文件相对路径



    public String getFmt(){
        return fmt;
    }
    public String getKey() { return key; }
    public String getMode(){
        return mode;
    }
    public String getPath() {
        return path;
    }
    public String getValue(){
        return value;
    }
    public String getTime(){
        return generated_time;
    }
    public String getRelevantPath(){return relevantPath;}

    public GitObject()
    {
        this.generated_time = createTime();
    }

    private String createTime()
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }



    /**
     * 将该对象压缩存入repository中
     * @throws Exception
     */
    public void compressSerialize() throws Exception
    {
        try
        {
            String storePath = ".jit/objects/" + getKey() + "."+getFmt();
            ZipSerial.compressWriteObj(storePath, this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Deserialize a Gitobject from an existed hash file in .jit/objects.
     * @param filename
     * @throws IOException
     */
    public static GitObject deserialize(String filename) throws Exception
    {
        try
        {
            String storePath = ".jit/objects/" + filename;
            String classType = filename.split("\\.")[1];//取出对应类类型的字符

            Map<String,Class> map = new HashMap<>();
            map.put("Tree", Tree.class);
            map.put("Blob", Blob.class);
            map.put("Commit", Commit.class);

            GitObject deserial = (GitObject) ZipSerial.readCompressFile(storePath, map.get(classType));
            return deserial;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }


    }

}
