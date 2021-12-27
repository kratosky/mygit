package fileoperation;

import jitinitiation.JitInitiation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.LinkedList;

public class FileStatus
{
    /**
     * To check if the branch exists.
     * @param branchname
     * @return
     * @throws FileNotFoundException
     */
    public static boolean branchExist(String branchname)throws FileNotFoundException
    {
        File branch = new File(JitInitiation.getGitDir() + File.separator + "refs"
                + File.separator + "heads" + File.separator + branchname);

        return branch.isFile();
    }

    /**
     * d通过查看文件的最后一次修改时间是否改变判断文件是否改变x
     * @param path
     * @param filename
     * @param indexList
     * @return
     * @throws FileNotFoundException
     */
    public static boolean fileChanged(String path, String filename, LinkedList<String[]> indexList) throws FileNotFoundException
    {
        boolean change = false;//先假定没有改变
        File file = new File(path + File.separator + filename);//将路径名与文件名合在一起创建文件
        if(!file.isFile())//如果不是文件抛出异常
        {
            throw new FileNotFoundException(filename + " is not a file!");
        }
        String timeStamp = new Date(file.lastModified()).toString();//获得文件最后一次修改的时间
        for(String[] e :indexList)//遍历传入的文件名列表(index暗示是暂存区)，如果第二个元素有该文件复合名，则看时间戳是否改变
        {//d将原有的链表低效遍历方式转换为高效的见java进阶书P31 x
            //（假定第二个位置是文件名，第三个位置是时间戳）
            if(e[1].equals(filename))
            {
                change = e[2].equals(timeStamp);
                break;
            }
        }
        return change;
    }
}
