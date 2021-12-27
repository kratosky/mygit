package test;

import core.*;

public class testBranchCheckout
{
    public static void main(String[] args) throws Exception
    {
        JitInit.init(".");
        JitCommit.commit("ddx", "ddx", "yhc is my son!");
        JitCommit.commit("ddx", "ddx", "yhc is my son!");
        JitCommit.commit("ddx", "ddx", "yhc is my son!");
        JitCommit.commit("ddx", "ddx", "yhc is my son!");
        JitLog.log();
        JitCheckout.checkout("holyman!");
        JitBranch.branch("holyman!");
        JitCheckout.checkout("holyman!");
    }
}
