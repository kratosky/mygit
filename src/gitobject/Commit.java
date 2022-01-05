package gitobject;

import java.io.*;
import java.util.Map;

import branchoperation.Branch;
import fileoperation.FileDeletion;
import fileoperation.FileReader;
import indexoperation.Index;
import sha1.SHA1;

public class Commit extends GitObject
{
    private String commitTree; 		// the serialized filename of present committed tree
    private String parent; 	// the serial Filename of the parent commit
    private String author; 	// the author's name and timestamp
    private String committer; // the committer's info
    private String message; 	// the commit memo



    public String getParent(){return parent;}
    public String getTreeSerial(){return commitTree;}
    public String getAuthor(){return author;}
    public String getCommitter(){return committer;}
    public String getMessage(){return message;}


    /**
     * Construct a commit directly from a file.
     * @param
     * author, committer, message参数在git commit命令里创建
     * @throws Exception
     */


    //dx Commit构造函数
    public Commit(String author, String committer, String message) throws Exception
    {
        this.fmt = "Commit"; 	//type of object
        this.author = author;
        this.committer = committer;
        this.message = message;

        //将暂存区的多个文件在清空后的".jit/restoreCommit"文件夹下恢复为带有文件夹的文件系统
        File restoreCommit = new File(".jit/restoreCommit");
        FileDeletion.emptyDirec(restoreCommit);
        Index.restoreWork(".jit/restoreCommit");


        //将".jit/restoreCommit"文件夹生成一颗树,并存入".jit/objects"（一定要在清空暂存区之前）

        Tree treeToCommit = new Tree(restoreCommit,".jit/restoreCommit");
        this.commitTree = treeToCommit.getKey()+"."+treeToCommit.getFmt();
        treeToCommit.compressSerialize();

        //清空暂存区
        Index.initIndex();

        //清空".jit/ToCommit"文件夹
        FileDeletion.emptyDirec(restoreCommit);

        //将生成的树的序列化文件的文件名赋给本次Commit对象的commitTree属性
        this.commitTree = treeToCommit.getKey()+"."+treeToCommit.getFmt();


        //将commitTree, parent, author, committer, message信息写入value之中，用于对commit对象哈希码的生成
        /*Content of this commit, like this:
         *tree bd31831c26409eac7a79609592919e9dcd1a76f2
         *parent d62cf8ef977082319d8d8a0cf5150dfa1573c2b7
         *author xxx  1502331401 +0800
         *committer xxx  1502331401 +0800
         *message "这次修理了儿子糟蹋的代码"
         *Time 2021-12-21 at 21:13:02
         * */
        String hashvalue = this.getTreeSerial() + this.getAuthor() + this.getCommitter() +
                this.getMessage() + System.currentTimeMillis();
        //System.out.println(this.value);

        //生成commit对象的哈希值赋给key
        this.key = SHA1.hashString(hashvalue);
        this.parent = updateBranch();

        //将commitTree, parent, author, committer, message信息写入value之中，用于对commit对象哈希码的生成
        /*Content of this commit, like this:
         *tree bd31831c26409eac7a79609592919e9dcd1a76f2
         *parent d62cf8ef977082319d8d8a0cf5150dfa1573c2b7
         *author xxx  1502331401 +0800
         *committer xxx  1502331401 +0800
         *message "这次修理了儿子糟蹋的代码"
         *Time 2021-12-21 at 21:13:02
         * */
        this.value = "tree " + this.getTreeSerial() + "\nparent " + this.getParent()+
                "\nauthor " + this.getAuthor() + "\ncommitter " + this.getCommitter() +
                "\nmessage  " + this.getMessage() +"\nTime: "+ this.getTime();

        System.out.printf("Commit " + this.getKey() + " at " +  this.getTime() +"\n");


    }



    /**
     * Deserialize a tree object with treeId and its path.
     * @param filename
     * @return
     * @throws IOException
     */
    public static Commit deserialize(String filename) throws Exception
    {
        return (Commit)GitObject.deserialize(filename);
    }

    /**
     * 将本次commit更新到branchMap中，同时返回上一次的commit序列化文件名
     * @return
     * @throws IOException
     */
    private String updateBranch() throws Exception
    {
        String branchName = Branch.getCurrentBranch();
        Map<String, String> branchMap = Branch.getBranchMap();
        //通常情况下有前驱结点，从而更新branch
        if (branchMap.containsKey(branchName))
        {
            String parent = branchMap.get(branchName);
            branchMap.replace(branchName, this.getKey() + "." + this.getFmt());
            Branch.setBranchMap(branchMap);
            return parent;
        }
        //初始化master branch
        else
        {
            branchMap.put("master", this.getKey() + "." + this.getFmt());
            Branch.setBranchMap(branchMap);
            return null;
        }
    }






    /**
     * Get the parent commit from the this commit.
     * @return
     * @throws IOException
     */
    public Commit getParentCommit() throws Exception
    {
        //如果不是头结点的话
        if(this.getParent()!=null)
        {
            Commit parentCommit = Commit.deserialize(this.getParent());
            return parentCommit;
        }
        else
        {
            return null;
        }
    }

    /**
     * Get the tree from the HEAD file. 。
     * @return
     * @throws IOException
     */
    public Tree getTree() throws Exception
    {
        Tree commitTree = Tree.deserialize(this.getTreeSerial());
        return commitTree;
    }

    /**
     * 将commit的文件恢复到指定路径
     * @param restorePath
     * @throws Exception
     */
    public void restoreCommitFiles(String restorePath) throws Exception
    {
        Tree commitTree = this.getTree();
        commitTree.treeToDirec(restorePath);
    }

    /**
     * 将commit的文件恢复到暂存区
     * @throws Exception
     */
    public void restoreCommitIndex() throws Exception
    {
        Tree commitTree = this.getTree();
        commitTree.treeToIndex();
    }


}

