package gitobject;

import indexoperation.Index;
import sha1.SHA1;

import java.io.*;
import java.util.*;


public class Tree extends GitObject
{


    private Map<String, String> treeMap; //dx 文件 in tree,键为其相对路径，值为其在objects文件名
    public Map<String, String> getTreeMap()
    {
        return this.treeMap;
    }


    public Tree() {}
    /**
     * Constructor
     * @param file
     * @throws Exception
     */
    public Tree(File file, String dirpath) throws Exception
    {
        this.relevantPath = dirpath;
        this.treeMap = new HashMap<>();
        this.fmt = "Tree";
        this.mode = "040000";
        this.value = "";
        this.key = genKey(file);
        indexToTreeMap();
        //genTreeMap(dirpath);
    }

    /**
     * Deserialize a tree object with treeId and its path.
     * @param filename
     * @return
     * @throws IOException
     */
    public static Tree deserialize(String filename) throws Exception
    {
        return (Tree)GitObject.deserialize(filename);
    }




    /**
     * Generate the key of a tree object.
     * @param dir
     * @return String
     * @throws Exception
     */
    private String genKey(File dir) throws Exception
    {
        return SHA1.hash(dir);
    }

    @Override
    public String toString()
    {
        return "040000 tree " + " " + key + " " + value + " " + generated_time +  " ";
        //return "040000 tree " + key;
    }


    /**
     * 通过给定文件夹路径生成树的TreeMap
     * @param dirpath
     * @throws Exception
     */
    private void genTreeMap(String dirpath) throws Exception
    {
        File dir = new File(dirpath);
        if(!dir.isDirectory())
        {
            throw new Exception("Direc not exist!");
        }
        File[] fs = dir.listFiles();
        SHA1.order(fs);
        for(int i = 0; i < fs.length; i++)
        {
            if(fs[i].isFile())
            {
                String filepath = dirpath+File.separator+fs[i].getName();
                Blob tempblob = new Blob(fs[i], filepath);
                this.treeMap.put(tempblob.getRelevantPath(),tempblob.getKey()+"."+tempblob.getFmt());
            }
            if(fs[i].isDirectory())
            {
                String filepath = dirpath+File.separator+fs[i].getName();
                genTreeMap(filepath);
            }
        }
    }

    /**
     * 将当前暂存区生成树的TreeMap
     */
    private void indexToTreeMap() throws Exception
    {
        this.treeMap =  Index.getIndexMap();
    }

    /**
     * d将一颗树代表的文件夹全部恢复到dirPath中x
     * @param dirPath
     * @throws Exception
     */
    public void treeToDirec(String dirPath) throws Exception
    {

        this.treeMap.forEach((relevantPath,serialFileName) ->
        {
            try { Blob.deserialize(serialFileName).toFile(dirPath);}
            catch (Exception e) { e.printStackTrace();}
        });
    }

    /**
     * d将一颗树代表的文件夹全部恢复到Index中x
     * @throws Exception
     */
    public void treeToIndex() throws Exception
    {
        Index.setIndexMap(this.treeMap);
    }

}
