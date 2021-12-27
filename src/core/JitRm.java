package core;

import fileoperation.FileDeletion;
import indexoperation.Index;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class JitRm
{
    /**
     * 删除一个文件/文件夹
     * @param relevantPath
     * @throws Exception
     */
    public static void rm(String relevantPath) throws Exception
    {
        //dx：反序列化index文件夹（暂存区）中的"trackedBlob"文件，生成键为相对路径加文件名，值为其在“.jit/objects”文件夹中对应文件名的Map
        Map<String, String> trackedMap = Index.getIndexMap();
        //如果该路径下是一个文件，则可以在trackedMap中找到，从而可以从objects中删除，再从暂存区中除名
        if (trackedMap.containsKey(relevantPath))
        {
            File delBlob = new File(".jit" + File.separator + "objects" + File.separator + trackedMap.get(relevantPath));
            FileDeletion.deleteFile(delBlob);
            trackedMap.remove(relevantPath);
            System.out.printf("The file: %s is deleted\n", relevantPath);
        }
        //不在trackedMap中有两种情况，一种是文件不存在，一种是要删除的是文件夹
        else
        {
            int i = 0;//用于记录是否有文件被删除

            Iterator<Map.Entry<String, String>> it = trackedMap.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry<String, String> tempentry = it.next();
                if (tempentry.getKey().indexOf(relevantPath) == 0)
                {
                    i++;//计数增加
                    File delBlob = new File(".jit" + File.separator + "objects" + File.separator + tempentry.getValue());
                    FileDeletion.deleteFile(delBlob);
                    it.remove();
                    System.out.printf("The file: %s is deleted\n", tempentry.getKey());
                }

            }
            if (i == 0)
            {
                System.out.println("本次删除文件/文件夹不存在");
            }
            Index.setIndexMap(trackedMap);

        }
    }
}