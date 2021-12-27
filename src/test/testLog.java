package test;

import java.io.File;

import core.JitInit;
import core.JitLog;
import gitobject.Commit;
import java.io.File;
import java.io.IOException;
import core.JitCommit;

public class testLog
{

        public static void main(String[] args) throws Exception
        {
            JitInit.init(".");
            JitCommit.commit("ddx", "ddx", "yhc is my son!");
            JitCommit.commit("ddx", "ddx", "yhc is my son!");
            JitCommit.commit("ddx", "ddx", "yhc is my son!");
            JitCommit.commit("ddx", "ddx", "yhc is my son!");
            JitLog.log();

        }

}
