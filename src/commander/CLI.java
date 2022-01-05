package commander;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;


import core.*;



public class CLI
{
	
	/**
	 * Command 'jit init'
	 * @param args
	 * @throws IOException
	 */

	/*主函数，程序通过命令行输入jit命令，对输入字符数组进行判断，并执行相应命令*/
	public static void main(String[] args) throws Exception
	{
		if(args[1].equals("help"))
		{// jit help
			jitHelp();
		}
		else if(args[1].equals("init"))
		{// jit init
			jitInit(args);
		}
		else if(args.length == 3 && args[1].equals("add"))
		{// jit add path
			jitAdd(args);
		}
		else if(args[1].equals("reset"))
		{// jit add path
			jitReset(args);
		}
		else if(args.length == 2 && args[1].equals("commit"))
		{// jit commit path
			jitCommit(args);
		}
		else if(args.length == 2 && args[1].equals("log"))
		{// jit log
			jitLog();
		}
		else if(args.length == 2 && args[1].equals("gc"))
		{// jit log
			jitGc();
		}
		else if(args.length == 3 && args[1].equals("rm"))
		{// jit rm
			jitRm(args);
		}
		else if(args.length == 3 && args[1].equals("merge"))
		{// jit rm
			jitMerge(args);
		}
		else if(args[1].equals("branch"))
		{// jit commit path
			jitBranch(args);
		}
		else if(args[1].equals("checkout"))
		{// jit commit path
			jitCheckout(args);
		}
		else
		{
			for(int i = 0 ; i < args.length ; i++) System.out.printf(args[i] + " ");
			System.out.println(" is not a git command. See 'git help'.");
		}
	}

	/*创建一个空的repository或者将已有的repository初始化 */
	public static void jitInit(String[] args) throws IOException
	{
		String path = "";
		if(args.length <= 2)
		{ //如果输入的是 jit init
			path = new File(".").getCanonicalPath();
			JitInit.init(path);
		}
		else if(args[2].equals("-help"))
		{ //输入git init -help 查看git init的说明
			System.out.println("usage: jit init [<path>] [-help]\r\n" + "\r\n" +
					"jit init [<path>]:	Create an empty jit repository or reinitialize an existing one in the path or your default working directory.");
		}
		else//对输入字符串数超过2进行判断,此时在自己指定的文件夹创建.jit文件夹
		{
			path = args[2];
			if(!new File(path).isDirectory())
			{ //如果第三位字符串不是一个文件夹，提醒
				System.out.println(path + "is not a legal directory. Please init your reposiroty again. See 'jit init -help'.");
			}
			else
			{//否则对第三位字符串所对应的文件夹进行创建
				JitInit.init(path);
			}
		}
	}


	public static void jitAdd(String args[]) throws IOException
	{
		String path = args[2];// args[2] is the file/directory to be added.
		try
		{
			JitAdd.add(path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void jitCommit(String args[]) throws IOException
	{
		try
		{
			/*
			Scanner input = new Scanner(System.in);
			System.out.println("请输入author信息：");
			String author = input.nextLine();
			System.out.println("请输入commiter信息：");
			String commiter = input.nextLine();
			System.out.println("请输入提交信息：");
			String message = input.nextLine();
			JitCommit.commit(author, commiter,message);*/
			JitCommit.commit("ddx","ddx","ddx");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Command 'jit help'.
	 */
	public static void jitHelp()
	{
		System.out.println("usage: jit [--version] [--help] [-C <path>] [-c name=value]\r\n" +
				"           [--exec-path[=<path>]] [--html-path] [--man-path] [--info-path]\r\n" +
				"           [-p | --paginate | --no-pager] [--no-replace-objects] [--bare]\r\n" +
				"           [--git-dir=<path>] [--work-tree=<path>] [--namespace=<name>]\r\n" +
				"           <command> [<args>]\r\n" +
				"\r\n" +
				"These are common Jit commands used in various situations:\r\n" +
				"\r\n" +
				"start a working area\r\n" +
				"   init       Create an empty Jit repository or reinitialize an existing one\r\n" +
				"\r\n" +
				"work on the current change\r\n" +
				"   add        Add file contents to the index\r\n" +
				"   reset      Reset current HEAD to the specified state\r\n" +
				"   rm         Remove files from the working tree and from the index\r\n" +
				"\r\n" +
				"examine the history and state\r\n" +
				"   log        Show commit logs\r\n" +
				"   status     Show the working tree status\r\n" +
				"\r\n" +
				"grow, mark and tweak your common history\r\n" +
				"   branch     List, create, or delete branches\r\n" +
				"   checkout   Switch branches or restore working tree files\r\n" +
				"   commit     Record changes to the repository\r\n" +
				"   diff       Show changes between commits, commit and working tree, etc\r\n" +
				"   merge      Join two or more development histories together\r\n" +
				"\r\n" +
				"'jit help -a' and 'jit help -g' list available subcommands and some\r\n" +
				"concept guides. See 'jit help <command>' or 'jit help <concept>'\r\n" +
				"to read about a specific subcommand or concept.");
	}

	public static void jitLog() throws IOException
	{
		try
		{
			JitLog.log();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void jitRm(String args[]) throws IOException
	{
		String path = args[2];// args[2] is the file/directory to be removed.
		try
		{
			JitRm.rm(path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void jitReset(String args[]) throws IOException
	{
		try
		{
			if(args.length == 3)
			{
				if(args[2].equals("-help"))
				{
					System.out.println("usage: jit reset [--mode] ([--recursionTimes])\r\n" +
									"\r\n" +
									"jit reset --mixed ([--recursionTimes]): Reset the index to the commit n times before.\r\n" +
									"\r\n" +
									"jit reset --hard ([--recursionTimes]): Reset the worktree and index to the commit n times before."+
									"\r\n" +
									"jit reset --super ([--recursionTimes]): Reset the head, current branch and above to the commit n times before.");
				}
				else if(args[2].equals("--mixed"))
				{
					JitReset.resetMixed(1);
				}
				else if(args[2].equals("--hard"))
				{
					JitReset.resetHard(1);
				}
				else if(args[2].equals("--super"))
				{
					JitReset.resetSuper(1);
				}
				else
				{
					System.out.println("输入不合法，请输入“jit reset -help”查看操作说明后重新输入");
				}

			}
			else
			{
				int times = Integer.parseInt(args[3]);
				if(args[2].equals("-mixed"))
				{
					JitReset.resetMixed(times);
				}
				else if(args[2].equals("-hard"))
				{
					JitReset.resetHard(times);
				}
				else if(args[2].equals("-super"))
				{
					JitReset.resetSuper(times);
				}
				else
				{
					System.out.println("输入不合法，请输入“jit reset -help”查看操作说明后重新输入");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void jitCheckout(String args[]) throws IOException
	{
		try
		{
			if(args.length > 2 && args[2].equals("-help"))
			{ //'jit checkout -help'
				System.out.println("usage: jit checkout [branch-name] [-b branch-name] [-help]\r\n" +
						"\r\n" +
						"jit checkout [branch]: Switch to the branch.\r\n" +
						"\r\n" +
						"jit checkout -b [branch]: Delete the branch.");
			}
			else if(args.length == 3)
			{ //'jit checkout [branch]'
				String branchName = args[2];// args[2] is the branch to be checked out.
				JitCheckout.checkout(branchName);
				System.out.println("Switched to " + branchName);
			}
			else if(args.length == 4 && args[2].equals("-b"))
			{
				String branchName = args[3];
				JitBranch.branch(branchName);
				System.out.println("Built and switched to " + branchName);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void jitBranch(String args[]) throws Exception
	{

		try
		{
			if(args.length > 2 && args[2].equals("-help"))
			{ //'jit branch -help'
				System.out.println("usage: jit branch [branch-name] [-d branch-name] [-help]\r\n" +
						"\r\n" +
						"jit branch: List all local branches.\r\n" +
						"\r\n" +
						"jit branch [branch-name]: Create the branch.\r\n" +
						"\r\n" +
						"jit branch -d [branch-name]: Delete the branch.");
			}
			else if(args.length == 2)
			{ //'jit branch'
				JitBranch.showBranch();
			}
			else if(args.length == 3)
			{ //'jit branch [branch-name]'
				String branchName = args[2];// args[2] is the branch to be checked out.
				JitBranch.branch(branchName);
			}
			else if(args.length == 4 && args[2].equals("-d"))
			{ //'jit branch -d [branch-name]'
				String branchName = args[3];
				JitBranch.delBranch(branchName);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void jitMerge(String args[]) throws IOException
	{
		String branchName = args[2];// args[2] is the branch to be merged.
		try
		{
			JitMerge.merge(branchName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public static void jitGc() throws IOException
	{
		try
		{
			JitGC.gc();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
