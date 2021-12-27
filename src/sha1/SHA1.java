package sha1;

import gitobject.Blob;
import gitobject.Tree;

import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class SHA1
{
	

    /**
     * d对传入字符串求哈希值x
     * @param value
     * @return
     * @throws Exception
     */
    public static String hashString(String value) throws Exception
    {
        byte[] sha1 = hashStream(new ByteArrayInputStream(value.getBytes()));
        return showHash(sha1);
    }

    /**
     * 对传入的文件或者文件夹求得哈希值，返回字符
     * @param file
     * @return
     * @throws Exception
     */

    public static String hash(File file) throws Exception
    {
        MessageDigest total = MessageDigest.getInstance("SHA-1");//一开始就初始化一个总的
        try
        {
            if(file.isFile())
            {
                hashFile(file,total);
            }
            else if(file.isDirectory())
            {
                hashDirec(file.getCanonicalPath(), total);
            }
            else
            {
                throw new IOException("The path is not a directory or file!");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();;
        }
        String hashresult = showHash(total.digest());
        return hashresult;
        //System.out.println("the hash value of "+root + " is " +hashresult);
    }

    /**
     * 对输入流求哈希值，返回字节数组
     * @param is
     * @return
     * @throws Exception
     */
    public static byte[] hashStream(InputStream is) throws Exception
    {
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        int numRead = 0;
        while(numRead != -1) {
            numRead = is.read(buffer);
            if(numRead > 0) {
                complete.update(buffer,0,numRead);
            }
        }
        is.close();
        return complete.digest();
    }
    /**
     * 将字节数组哈希值转化为字符串
     * @param sha1
     * @return
     */
    private static String showHash(byte[]sha1)
    {
        //将byte数组转化为形式为16进制的字符串
        String result = "";
        for(int i = 0; i < sha1.length; i++)
        {
            result += Integer.toString(sha1[i]&0xFF, 16);
        }
        return result;
    }

    /**
     * 字典中数据更新，递归过程函数
     */
    private static void hashDirec(String path, MessageDigest complete)throws Exception
    {
        File dir = new File(path);
        File[] fs = dir.listFiles();
        order(fs);//对文件夹中的文件与子文件夹进行排序
        for(int i = 0; i < fs.length; i++)
        {
            if(fs[i].isFile())
            {
                //如果是文件就shaFile读入并更新complete
                hashFile(fs[i], complete);
            }
            if(fs[i].isDirectory())
            {
                byte[] folderName = fs[i].getName().getBytes();
                //d为了保证文件夹的哈希值能区分开内部结构不同，但子文件最终排序相同的文件夹，要将结构信息更新进哈希值x
                complete.update(folderName, 0, folderName.length);//对文件夹遍历前先把文件名更新进去
                //如果是文件夹就将其加入路径并对其内部文件与子文件夹进行递归
                hashDirec(path+File.separator+fs[i].getName(), complete);
                complete.update(folderName, 0, folderName.length);//对文件夹遍历后也把文件名更新进去
            }
        }
    }
    /**
     * 文件中数据更新，递归过程函数
     */
    private static void hashFile(File file, MessageDigest complete) throws Exception
    {
        FileInputStream is = new FileInputStream(file);
        byte[] buffer = new byte[1024];//缓冲区一次计算1024个byte
        int numRead = 0;
        do
        {
            //numRead是每次读入字节数，除最后一次外为1024个
            numRead = is.read(buffer);
            if(numRead > 0)
            {
                complete.update(buffer, 0, numRead);//根据buffer[0:numRead]内容更新hash值
            }
        } while(numRead != -1);//为-1时说明读完了
        is.close();
    }

    /**
     * 排序
     */
    public static void order(File[] files)
    {
        List fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>()
        {
            @Override
            public int compare(File o1, File o2)
            {
                //d二者一个文件，一个为目录时，保证目录排在文件前面x
                if (o1.isDirectory() && o2.isFile())
                    return -1;//o1为文件夹，小于o2文件夹
                if (o1.isFile() && o2.isDirectory())
                    return 1;//o1为文件，大于o2文件夹
                return o1.getName().compareTo(o2.getName());//二者类型相同时，根据命名排序
            }
        });
    }

}
