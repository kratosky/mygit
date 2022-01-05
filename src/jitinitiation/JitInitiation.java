package jitinitiation;

import java.io.*;

import branchoperation.Branch;
import indexoperation.Index;

import fileoperation.*;

/**
 * Todo: Add your own code. JitInit.init("worktree") 会创建 repository "worktree/.jit" ,
 *       which contains all the default files and other repositories inside.
 *       如果directory已经存在则删除原来的并创建一个新的
 *       请不要修改核心 directory 中的代码。
 */

public class JitInitiation
{
    private static String workTree;	//working directory 整个工作区
    private static String gitDir;	//jit repository path


    /**
     * workTree属性设置为传入路径，通常为“.”，也就是当前路径
     * gitDir则是当前路径中的.jit文件，也就是repository
     * @param path
     * @throws IOException
     */
    public JitInitiation(String path) throws IOException
    {
        this.workTree = path;
        this.gitDir = path + File.separator + ".jit";
    }

    public JitInitiation() throws IOException
    {
        this(".");
    }



    public static String getGitDir() {
        return gitDir;
    }

    public static String getWorkTree() {
        return workTree;
    }
    
    /**
     * 一下三个函数是在判定repository的路径是否存在以及是否为文件与文件夹
     * @return
     */
    public boolean exist(){ return new File(gitDir).exists(); }// 判定给定文件夹中是否已经存在.jit文件或文件夹是否存在

    public boolean isFile(){ return new File(gitDir).isFile(); }// 判定给定路径是否已经存在.jit文件

    public boolean isDirectory(){ return new File(gitDir).isDirectory(); }// 判定给定路径是否已经存在.jit文件夹


    /**
     * 创建repository和里面的子文件和子目录
     * @return boolean
     * @throws IOException
     */

    public boolean createRepo() throws IOException
    {
     //如果传入的workTree不是一个有效的目录则报错，实际在CLI中已经进行过判断
        if(!new File(workTree).isDirectory())
        {
            throw new IOException("The path doesn't exist!");
        }
        //然后创建workTree中的.jit文件夹dx
        String path = gitDir;
        new File(path).mkdirs();
        // 以上完成了.jit文件夹的生成
        //生成.jit文件夹下的其余子文件
        FileCreation.createDirectory(gitDir, "branches");
        //生成头指针
        FileCreation.createFile(gitDir+File.separator+ "branches", "HEAD.txt", "master");//初始时没有HEAD指针，提交后才初始化
        Branch.initBranches();

        FileCreation.createDirectory(gitDir, "objects");
        FileCreation.createDirectory(gitDir, "recoverCommit");

        FileCreation.createDirectory(gitDir, "index");// 暂存区
        Index.initIndex();
        return true;//全部创建成功，则返回true
    }


}
