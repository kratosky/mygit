package core;

import branchoperation.Branch;
import fileoperation.FileDeletion;
import fileoperation.FileReader;
import gitobject.Commit;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class JitCheckout
{
    public static void checkout(String branch) throws Exception
    {
        Map<String, String> branchMap = Branch.getBranchMap();
        //分支名存在则判断是否为当前HEAD指针所指branch
        if (branchMap.containsKey(branch))
        {
            File HEAD = new File(".jit" + File.separator + "branches"+File.separator+"HEAD.txt");
            String currentBranchName = FileReader.getContent(HEAD);
            //要checkout的分支为当前所在分支时，给出提醒,
            if(currentBranchName.equals(branch))
            {
                System.out.printf("要checkout的%s分支为当前所在分支！\n", branch);
            }
            //要checkout的分支为其他分支时，更改HEAD指针，将要checkout的分支写入
            else
            {
                FileWriter fileWriter = new FileWriter(HEAD);
                fileWriter.write(branch);
                fileWriter.close();
                System.out.printf("成功checkout到%s分支！\n", branch);
            }
            //无论checkout的是不是当前分支，都将该分支最新commit版本恢复到restoreCommit文件夹
            FileDeletion.emptyDirec(".jit/restoreCommit");
            Commit checkTo = Commit.deserialize(branchMap.get(branch));
            checkTo.restoreCommitFiles(".jit/restoreCommit");
            System.out.printf("已将该分支最新commit版本恢复到restoreCommit文件夹！\n");
        }
        //分支名不存在，checkout失败！
        else
        {
            System.out.printf("%s 分支不存在，转移HEAD指针失败！\n", branch);
        }
    }
}
