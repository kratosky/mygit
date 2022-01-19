package core;

import branchoperation.Branch;
import fileoperation.FileDeletion;
import fileoperation.FileReader;
import gitobject.Commit;
import gitobject.Tree;
import gitobject.Blob;
import indexoperation.Index;
import jitinitiation.JitInitiation;
import serialization.ZipSerial;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JitGC
{
    /**
     * 垃圾清理，清理所有未被跟踪的分支上的所有文件（由git reset --super， git branch -d 会导致出现未被跟踪分支 ）
     * @throws Exception
     */
    public static void gc() throws Exception
    {
        //将object里面的所有文件存入set
        Set<String> objectMap = initObjectMap();
        //将branchMap里面所有branch里的文件从set中删除
        Map<String, String> branchMap = Branch.getBranchMap();
        branchMap.forEach((branch,serialFileName) ->
        {
            try { scanBranch(branch, objectMap);}
            catch (Exception e) { e.printStackTrace();}
        });
        //再将未被扫描到的垃圾文件删除
        delGarbage(objectMap);
    }

    /**
     *将objects中的所有文件都加入到set中
     * @return
     * @throws Exception
     */
    private static Set<String> initObjectMap() throws Exception
    {
        Set<String> objset = new HashSet<>();
        File objectsFile = new File( ".jit" + File.separator + "objects" );
        if(!objectsFile.isDirectory())
        {
            System.out.println("仓库未被创建！");
            return null;
        }
        else
        {
            File[] objects = objectsFile.listFiles();
            for(int i = 0; i < objects.length; i++)
            {
                objset.add(objects[i].getName());
            }
            return objset;
        }
    }

    /**
     * 对当前分支内的所有文件进行扫描，将出现的文件从set中删除
     * @param branchName
     * @param objectSet
     * @throws Exception
     */
    private static void scanBranch(String branchName,Set<String> objectSet) throws Exception
    {
        Commit iter = Branch.getLastCommit(branchName);
        while(iter!=null)
        {
            scanCommit(iter,objectSet);
            iter = iter.getParentCommit();
        }

    }

    /**
     * 对当前commit内的commit，commitTree和其中的blob从set删除
     * @param commit
     * @param objectSet
     * @throws Exception
     */
    private static void scanCommit(Commit commit,Set<String> objectSet) throws Exception
    {
        objectSet.remove(commit.getKey()+"."+commit.getFmt());//删掉commit
        objectSet.remove(commit.getTreeSerial());//删掉commit里的commitTree
        Tree commitTree = commit.getTree();
        objectSet.removeAll(commitTree.getTreeMap().values());//删掉commitTree中所有的文件
    }


    /**
     * 将set中未被遍历到的文件全部删除
     * @param objectSet
     * @throws Exception
     */
    private static void delGarbage(Set<String> objectSet) throws Exception
    {
        objectSet.forEach((serialFileName) ->
        {
            try { FileDeletion.deleteFile(".jit/objects/"+serialFileName);System.out.println(serialFileName+" is deleted!");}
            catch (Exception e) { e.printStackTrace();}
        });

    }

}
