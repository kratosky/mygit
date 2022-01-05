package core;

import fileoperation.FileDeletion;
import gitobject.*;
import indexoperation.Index;
import sha1.SHA1;

import java.io.*;
import java.util.Map;

public class JitAdd
{
    /**
     * Init repository in your working area.
     * @param relevantPath
     * @throws IOException
     */
    public static void add(String relevantPath) throws Exception
    {
        /* Todo: You should pass the filename in this function, and generate a hash file in your repository.*/
        // 这里要判断blob或tree对象，并调用响应的hash计算
        File file = new File(relevantPath);
        if(file.isFile())
        {
            System.out.printf("The file: %s will be added. A Blob may be generated.\n", relevantPath);
            Blob addblob = new Blob(file, relevantPath);


            //dx：反序列化index文件夹（暂存区）中的"trackedBlob"文件，生成键为相对路径加文件名，值为其在“.jit/objects”文件夹中对应文件名的Map
            Map<String, String> trackedMap = Index.getIndexMap();

            //对addBlob进行判定，并根据情况序列化入“.jit/objects”文件夹
            //判定该路径下该文件名文件是否已经存在，若已经存在，且该文件有了新的更改，则将原版文件对应的在objects文件夹里的序列化文件删除
            String serialAddress = addblob.getKey()+'.'+addblob.getFmt();
            if(trackedMap.containsKey(addblob.getRelevantPath()))
            {
                //若发生变动，将原先的Blob从objects库中删去
                if(!trackedMap.get(addblob.getRelevantPath()).equals(serialAddress))
                {
                    File oldBlob = new File(".jit"+File.separator+"objects"+File.separator+trackedMap.get(addblob.getRelevantPath()));
                    FileDeletion.deleteFile(oldBlob);
                    // 压缩写入.jit/objects
                    addblob.compressSerialize();
                    System.out.printf("100644 Blob " + addblob.getKey() + "  updated at " +  addblob.getTime() +"\n");
                }
                //d否则，说明新增添的这一文件已经增加过了，x则无需将“.jit/objects”文件夹写入新的Blob
                else
                {
                    Blob old = Blob.deserialize(trackedMap.get(addblob.getRelevantPath()));
                    System.out.printf("100644 Blob " + addblob.getKey() + " already exists, made at " +  old.getTime() +"\n");
                }

            }
            //若该路径文件是新建的，则增添到“.jit/objects”文件夹中，如果这个文件和之前提交的文件哈希值相同不会真的存入objects库中，
            // 但是会在index的map中以本次路径作为键对原来存在gitobjects中的文件进行追踪，节省了空间，也记录了本次提交的信息
            else
            {
                addblob.compressSerialize();
                System.out.printf("100644 Blob " + addblob.getKey() + "  added to Index at " +  addblob.getTime() +"\n");
            }

            //更新trackedMap（无论是新增，旧键新值，还是旧键旧值都是一样的），并反序列化回去
            trackedMap.put(addblob.getRelevantPath(), serialAddress);
            Index.setIndexMap(trackedMap);
        }
        else if(file.isDirectory())
        {
            System.out.printf("The directory: %s will be added. Files inside it will be generated as Blobs.\n",relevantPath);
            File[] filesToAdd= file.listFiles();
            SHA1.order(filesToAdd);
            for(int i = 0; i < filesToAdd.length; i++)
            {
                JitAdd.add(relevantPath+"/"+filesToAdd[i].getName());
            }
        }
        else
        {
            throw new IOException("The path is not a directory or file!");
        }
    }
}
