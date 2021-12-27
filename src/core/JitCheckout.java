package core;

import fileoperation.FileReader;
import jitinitiation.JitInitiation;
import serialization.ZipSerial;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class JitCheckout
{
    public static void checkout(String branch) throws Exception
    {
        Map<String, String> branchMap = ZipSerial.getBranchMap();
        //分支名存在则判断是否为当前HEAD指针所指branch
        if (branchMap.containsKey(branch))
        {
            File HEAD = new File(".jit" + File.separator + "branches"+File.separator+"HEAD.txt");
            String currentBranchName = FileReader.getContent(HEAD);
            //要checkout的分支为当前所在分支时，什么也不用做，给出提醒
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
        }
        //分支名不存在，checkout失败！
        else
        {
            System.out.printf("%s 分支不存在，转移HEAD指针失败！\n", branch);
        }
    }
}
