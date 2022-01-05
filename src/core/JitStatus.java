package core;

import indexoperation.Index;

import java.io.File;

public class JitStatus
{
    public static void status()throws Exception
    {
        JitBranch.showBranch();
        System.out.println("\n暂存区里的文件路径以及其对应的序列化文件名为：");
        Index.showIndex();
        Index.restoreWork(".jit"+ File.separator+"restoreCommit");
        System.out.println("\n当前暂存区文件体系已在restoreCommit文件夹重建，请查看！");
    }

}
