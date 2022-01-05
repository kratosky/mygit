package branchoperation;

import fileoperation.FileReader;
import gitobject.Commit;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Branch
{
    public static void initBranches()
    {
        try
        {
            //初始化生成branches序列化的hashMap，键为分支名，值为其所指向的commit序列化文件名，文件名叫“branchMap”
            Map<String,String> testmap = new HashMap<>();
            File indexFile = new File( ".jit" + File.separator + "branches" + File.separator +"branchMap" );
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(indexFile));
            oo.writeObject(testmap);
            oo.flush();
            oo.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * d将当前branches中的branchMap反序列化取出x
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

    /**
     * 从给定的branch中返回最后一个Commit
     * @param branch
     * @return
     * @throws Exception
     */
    public static Commit getLastCommit(String branch) throws Exception
    {
        Map<String, String> branchMap = Branch.getBranchMap();
        if(!branchMap.containsKey(branch))
        {
            System.out.println("分支名不存在");
            return null;
        }
        else
        {
            Commit last = Commit.deserialize(branchMap.get(branch));
            return last;
        }
    }

    /**
     * 获得当前的branch
     * @return
     * @throws Exception
     */
    public static String getCurrentBranch() throws Exception
    {
        File HEAD = new File(".jit" + File.separator + "branches"+File.separator+"HEAD.txt");
        String branchName = FileReader.getContent(HEAD);
        return branchName;
    }

}
