package test;

public class TestZlib
{
    public static void main(String[] args)throws Exception
    {
        /*
        //d经过测试，决定不用zlib了x

        File file = new File("tiger.txt");
        byte[] content = FileReader.getContent(file).getBytes();
        byte[] aftercom = ZLib.compress(content);
        OutputStream out = new FileOutputStream(new File("presstiger.txt"));
        ZLib.compress(aftercom,out);
        InputStream in = new FileInputStream(new File("presstiger.txt"));
        byte[] beforedecom = ZLib.decompress(in);
        byte[] afterdecom = ZLib.decompress(beforedecom);
        File newfile = new File("newtiger.txt");
        FileWriter fileWriter = new FileWriter(newfile);
        fileWriter.write(afterdecom.toString());
        fileWriter.close();
         */
    }

}
