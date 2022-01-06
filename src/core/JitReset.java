package core;

import branchoperation.Branch;
import fileoperation.FileReader;
import gitobject.Commit;

import java.io.File;
import java.util.Map;

public class JitReset
{

    public static void resetMixed(int n) throws Exception
    {
        Commit toRestore = getLastNCommit(n);
        if(toRestore != null)
        {
            toRestore.restoreCommitIndex();
            System.out.println("已经成功将倒数第"+n+"次commit内容恢复到暂存区！");
        }
    }

    public static void resetHard(int n) throws Exception
    {
        Commit toRestore = getLastNCommit(n);
        if(toRestore != null)
        {
            toRestore.restoreCommitIndex();
            toRestore.restoreCommitFiles(".jit/restoreCommit");
            System.out.println("已经成功将倒数第"+n+"次commit内容恢复到暂存区以及restorCommit文件夹中！");
        }
    }

    public static void resetSuper(int n) throws Exception
    {
        Commit toRestore = getLastNCommit(n);
        if(toRestore != null)
        {
            toRestore.restoreCommitIndex();
            toRestore.restoreCommitFiles(".jit/restoreCommit");
            Map<String,String> branchmap = Branch.getBranchMap();
            branchmap.put(Branch.getCurrentBranch(),toRestore.getKey()+'.'+toRestore.getFmt());
            Branch.setBranchMap(branchmap);
            System.out.println("已经成功将倒数第"+n+"次commit内容恢复到暂存区以及restorCommit文件夹中，同时当前分支也回退到这次提交！");
        }
    }

    /**
     * 返回当前分支的倒数第N个Commit
     * @param n
     * @return
     * @throws Exception
     */
    private static Commit getLastNCommit(int n)throws Exception
    {
        String currentBranch = Branch.getCurrentBranch();
        Map<String, String> branchMap = Branch.getBranchMap();
        //通常情况下有前驱结点，则逐个打印value并向前走
        if (branchMap.containsKey(currentBranch))
        {
            Commit iter = Commit.deserialize(branchMap.get(currentBranch));
            for (int i = 1; i < n; i++)
            {
                iter = iter.getParentCommit();
                if ((iter == null))
                {
                    System.out.println("回滚次数超过当前分支长度");
                    return null;
                }
            }
            return iter;
        }
        //还没有提交记录
        else
        {
            System.out.println("目前还没有一次提交！");
            return null;
        }
    }
}
