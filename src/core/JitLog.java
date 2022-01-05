package core;

import branchoperation.Branch;
import fileoperation.FileReader;
import gitobject.Commit;

import java.io.File;
import java.util.Map;

public class JitLog
{
    public static void log() throws Exception
    {
        String branchName = Branch.getCurrentBranch();
        Map<String, String> branchMap = Branch.getBranchMap();
        //通常情况下有前驱结点，则逐个打印value并向前走
        if (branchMap.containsKey(branchName))
        {
            Commit iter =Commit.deserialize(branchMap.get(branchName));

            while(iter!=null)
            {
                System.out.println(iter.getValue());
                System.out.println("\n-----------------------------------------------------------------------------------------------------------------------\n");
                iter = iter.getParentCommit();
            }
            System.out.println("已经遍历完毕！");
        }
        //还没有提交记录
        else
        {
            System.out.println("目前还没有一次提交！");
        }
    }



}
