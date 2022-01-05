package core;

import branchoperation.Branch;
import fileoperation.FileReader;

import java.io.File;
import java.util.Map;

public class JitBranch
{
    public static void branch(String branch) throws Exception
    {
        Map<String, String> branchMap = Branch.getBranchMap();
        //分支名不存在则将其存进
        if (!branchMap.containsKey(branch))
        {
            File HEAD = new File(".jit" + File.separator + "branches"+File.separator+"HEAD.txt");
            String currentBranchName = FileReader.getContent(HEAD);
            String currentCommit = branchMap.get(currentBranchName);
            branchMap.put(branch,currentCommit);
            Branch.setBranchMap(branchMap);
            System.out.printf("%s 分支已已经成功创建！\n", branch);
        }
        //分支名已经存在，创建分支失败！
        else
        {
            System.out.println("分支名已经存在，创建分支失败！\n");
        }


    }

    public static void showBranch() throws Exception
    {
        //分别展示每一个分支
        Map<String, String> branchMap = Branch.getBranchMap();
        branchMap.forEach((branchName,commitSerialFileName) ->
        {
            System.out.println(branchName + "       " +  commitSerialFileName);
        });
        //打印头结点指针指向的分支
        File HEAD = new File(".jit" + File.separator + "branches"+File.separator+"HEAD.txt");
        String currentBranchName = FileReader.getContent(HEAD);
        System.out.println( "Current HEAD points to the branch\n" +  currentBranchName + "!");
    }

    public static void delBranch(String branch) throws Exception
    {
        Map<String, String> branchMap = Branch.getBranchMap();
        //分支名存在则将其删除
        if (branchMap.containsKey(branch))
        {
            File HEAD = new File(".jit" + File.separator + "branches"+File.separator+"HEAD.txt");
            String currentBranchName = FileReader.getContent(HEAD);
            //不可删除当前HEAD所在分支，否则会使得头指针置空
            if(currentBranchName.equals(branch))
            {
                System.out.printf("要删除的%s分支为当前分支，请先checkout到其他分支再进行删除！\n", branch);
            }
            else
            {
                branchMap.remove(branch);
                Branch.setBranchMap(branchMap);
            }
        }
        //分支名不存在，删除分支失败！
        else
        {
            System.out.printf("%s 分支不存在，删除分支失败！\n", branch);
        }
    }

}
