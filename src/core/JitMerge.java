package core;

import fileoperation.FileDeletion;
import gitobject.Blob;
import gitobject.Commit;
import gitobject.Tree;
import indexoperation.Index;
import branchoperation.Branch;
import core.JitDiff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JitMerge
{
    public static void merge(String branch) throws Exception
    {
        //参数只有一个，默认另一个是当前分支
        String main_branch = Branch.getCurrentBranch();

        // 现在需要在branchMap中的取出两个Commit id
        Map<String, String> branchMap = Branch.getBranchMap();
        Commit currentCommit = Commit.deserialize(branchMap.get(main_branch));
        Commit targetCommit = Commit.deserialize(branchMap.get(branch));
        Tree currentTree = currentCommit.getTree();
        Tree targetTree = targetCommit.getTree();
        Map<String, String> currentMap = currentTree.getTreeMap();
        Map<String, String> targetMap = targetTree.getTreeMap();
        Map<String, String> mergeResult = new HashMap<>();

        FileDeletion.emptyDirec(".jit"+File.separator+"restoreCommit");
        //主分支上的全部放进去
        Iterator<Map.Entry<String, String>> it = currentMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            Blob normalFile = Blob.deserialize(entry.getValue());
            normalFile.toFile(".jit"+File.separator+"restoreCommit");
            mergeResult.put(entry.getKey(), currentMap.get(entry.getKey()));
        }

        boolean flag = true;
        //副分支上的
        it = targetMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if(mergeResult.containsKey(entry.getKey()) && !targetMap.get(entry.getKey()).equals(currentMap.get(entry.getKey())))
            {
                flag = false;
                System.out.println("clashed file! file name is: " + entry.getKey());
                Blob sourceFile = Blob.deserialize(currentMap.get(entry.getKey()));
                Blob clashedFile = Blob.deserialize(entry.getValue());
                clashedFile.toFile(".jit"+File.separator+"restoreCommit"+File.separator + "clashed-Files-Of-Vice-Branch");
                JitDiff.diff(sourceFile.getValue(), clashedFile.getValue());// compare the distance
            }
            else
            {
                mergeResult.put(entry.getKey(), targetMap.get(entry.getKey()));
                Blob noClashFile = Blob.deserialize(entry.getValue());
                noClashFile.toFile(".jit"+File.separator+"restoreCommit");
            }
        }
        if (flag == false)
        {
            System.out.println("Due to clashed files, commit is not generated!");
            return;
        }
        else
        {
            System.out.println("成功合并!");
            Index.setIndexMap(mergeResult);
            Commit mergeCommit = new Commit(currentCommit.getAuthor(), currentCommit.getCommitter(), String.format("branches merge result of %s and %s\n", main_branch, branch));
            mergeCommit.compressSerialize();
        }

    }

}
