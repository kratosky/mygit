package test;

import gitobject.Commit;

public class testNullCommit
{
    public static void main(String[] args)
    {
        Commit mycommit =new Commit();
        System.out.println(mycommit.getFmt()==null);
    }

}
