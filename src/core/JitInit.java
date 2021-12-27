package core;

import java.io.IOException;
import fileoperation.FileDeletion;
import jitinitiation.JitInitiation;

public class JitInit
{
	/**
	 * Init repository in your working area. The workTree should never be null.
	 * @param workTree
	 * @throws IOException
	 */

    public static void init(String workTree) throws IOException
    {
        JitInitiation repo = new JitInitiation(workTree); // 传入workTree是存放.jit文件夹的文件夹
        if (repo.exist())// workTree中已经存在.jit文件，或文件夹，那么分类讨论
        {
            if (repo.isDirectory()) {// 若是文件夹则要删除这个.jit文件夹
                FileDeletion.deleteFile(JitInitiation.getGitDir());
            }
            else if (repo.isFile())// 如果是一个文件的话则直接抛出错误
            {
                throw new IOException("It is a file, please check");
            }
        }
        //创建.jit文件夹下各项文件与文件夹，并输出信息
        if (repo.createRepo())
        {
            System.out.println("Jit repository has been initiated successfully.");
        }
    }
}
