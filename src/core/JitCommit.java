package core;

import gitobject.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class JitCommit
{
    /**
     * Init repository in your working area.
     * @throws IOException
     */
    public static void commit(String author, String committer, String message) throws Exception
    {

        Commit currentcommit = new Commit(author, committer, message);
        //将commit对象序列化入“.jit/objects”中
        currentcommit.compressSerialize();

    }
}
