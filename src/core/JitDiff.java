package core;
import java.util.*;
import java.io.*;
/*
算法的运作是依赖于A和B文件构成的有向编辑图,
图中A为X轴, B为Y轴, 假定A和B的长度分别为m, n, 每个坐标代表了各自字符串中的一个字符.
在图中沿X轴前进代表删除A中的字符, 沿Y轴前进代表插入B中的字符.
在横坐标于纵坐标字符相同的地方, 会有一条对角线连接左上与右下两点, 表示不需任何编辑, 等价于路径长度为0.
算法的目标, 就是寻找到一个从坐标(0, 0)到(m, n)的最短路径
*/
public class JitDiff
{
    public static void diff(String file_one, String file_two)
    {

        String a = file_one;
        String b = file_two;
        /*
        String content = "";
        StringBuilder builder_one = new StringBuilder();
        StringBuilder builder_two = new StringBuilder();

        try {
            InputStreamReader streamReader_one = new InputStreamReader(new FileInputStream(file_one));
            BufferedReader bufferedReader_one = new BufferedReader(streamReader_one);
            InputStreamReader streamReader_two = new InputStreamReader(new FileInputStream(file_two));
            BufferedReader bufferedReader_two = new BufferedReader(streamReader_two);
            while ((content = bufferedReader_one.readLine()) != null)
                builder_one.append(content);
            while ((content = bufferedReader_two.readLine()) != null)
                builder_two.append(content);
            a = builder_one.toString();
            b = builder_one.toString();
        }
        catch (Exception e){e.printStackTrace();}

         */
        Stack<operation> operation_stack = new Stack<>();


        char[] aa = a.toCharArray();
        char[] bb = b.toCharArray();
        int max = aa.length + bb.length;
        int[] v = new int[max * 2];
        List<Snake> snakes = new ArrayList<>();

        for (int d = 0; d <= aa.length + bb.length; d++) {
            //System.out.println("D:" + d);
            for (int k = -d; k <= d; k += 2) {
                //System.out.print("k:" + k);
                // down or right?
                boolean down = (k == -d || (k != d && v[k - 1 + max] < v[k + 1 + max]));
                int kPrev = down ? k + 1 : k - 1;

                // start point
                int xStart = v[kPrev + max];
                int yStart = xStart - kPrev;

                // mid point
                int xMid = down ? xStart : xStart + 1;
                int yMid = xMid - k;

                // end point
                int xEnd = xMid;
                int yEnd = yMid;

                // follow diagonal
                int snake = 0;
                while (xEnd < aa.length && yEnd < bb.length && aa[xEnd] == bb[yEnd]) {
                    xEnd++;
                    yEnd++;
                    snake++;
                }
                // save end point
                v[k + max] = xEnd;
                // record a snake
                snakes.add(0, new Snake(xStart, yStart, xEnd, yEnd));
                //System.out.print(", start:("+xStart+","+yStart+"), mid:("+xMid+","+yMid+"), end:("+xEnd+","+yEnd + ")\n");
                // check for solution
                if (xEnd >= aa.length && yEnd >= bb.length) {
                    /* solution has been found */
                    //System.out.println("found");
                    /* print the snakes */
                    Snake current = snakes.get(0);
                    operation_stack.push(new operation(current.getxEnd(), current.getyEnd(), current.getxStart(), current.getyStart()));
                    //System.out.println(String.format("(%2d, %2d)<-(%2d, %2d)", current.getxEnd(), current.getyEnd(), current.getxStart(), current.getyStart()));
                    for (int i = 1; i < snakes.size(); i++) {
                        Snake tmp = snakes.get(i);
                        if (tmp.getxEnd() == current.getxStart()
                                && tmp.getyEnd() == current.getyStart()) {
                            current = tmp;
                            operation_stack.push(new operation(current.getxEnd(), current.getyEnd(), current.getxStart(), current.getyStart()));
                            //System.out.println(String.format("(%2d, %2d)<-(%2d, %2d)", current.getxEnd(), current.getyEnd(), current.getxStart(), current.getyStart()));
                            if (current.getxStart() == 0 && current.getyStart() == 0) {
                                break;
                            }
                        }
                    }
                    operation temp;
                    while(!operation_stack.empty()){
                        temp = operation_stack.pop();
                        if(temp.yStart == -1) temp.yStart = 0;
                        if(temp.xStart == -1) temp.xStart = 0;
                        System.out.printf(String.format("(%2d, %2d)->(%2d, %2d) ", temp.xStart, temp.yStart, temp.xEnd, temp.yEnd));
                        //System.out.println(String.format("删除原字符串位置: %d 的字符: %c", ));
                        if(temp.xStart == temp.yStart && temp.xEnd == temp.yEnd){System.out.println();continue;}
                        else if(temp.xStart == temp.yStart){
                            if(temp.xEnd > temp.yEnd) System.out.printf(String.format("删除原文件位置为%d的字符: %c ", temp.xEnd, a.charAt(temp.xEnd-1)));
                            if(temp.xEnd < temp.yEnd) System.out.printf(String.format("插入目标文件位置为%d的字符: %c ", temp.yStart, b.charAt(temp.yStart)));
                        }
                        else if(temp.xEnd == temp.yEnd){
                            if(temp.xStart > temp.yStart) System.out.printf(String.format("插入目标文件位置为%d的字符: %c ", temp.xStart, b.charAt(temp.xStart-1)));
                            if(temp.xStart < temp.yStart) System.out.printf(String.format("删除原文件位置为%d的字符: %c ", temp.yStart, a.charAt(temp.yStart-1)));
                        }
                        else if(temp.xStart == temp.xEnd){
                            System.out.printf(String.format("插入目标文件位置为%d的字符: %c ", temp.yStart, b.charAt(temp.yStart)));
                        }
                        else if(temp.yStart == temp.yEnd){
                            System.out.printf(String.format("删除目标文件位置为%d的字符: %c ", temp.xStart, a.charAt(temp.xStart)));
                        }
                        else if(temp.xEnd - temp.xStart > temp.yEnd - temp.yStart){
                            System.out.printf(String.format("删除原文件位置为%d的字符: %c ", temp.xStart, a.charAt(temp.xStart)));
                        }
                        else if(temp.xEnd - temp.xStart < temp.yEnd - temp.yStart){
                            System.out.printf(String.format("插入目标文件位置为%d的字符: %c ", temp.yEnd-1, b.charAt(temp.yEnd-1)));
                        }
                        System.out.println();
                    }
                    return;
                }
            }
        }
    }

    public static class Snake {
        private int xStart;
        private int yStart;
        private int xEnd;
        private int yEnd;

        public Snake(int xStart, int yStart, int xEnd, int yEnd) {
            this.xStart = xStart;
            this.yStart = yStart;
            this.xEnd = xEnd;
            this.yEnd = yEnd;
        }

        public int getxStart() {
            return xStart;
        }

        public void setxStart(int xStart) {
            this.xStart = xStart;
        }

        public int getyStart() {
            return yStart;
        }

        public void setyStart(int yStart) {
            this.yStart = yStart;
        }

        public int getxEnd() {
            return xEnd;
        }

        public void setxEnd(int xEnd) {
            this.xEnd = xEnd;
        }

        public int getyEnd() {
            return yEnd;
        }

        public void setyEnd(int yEnd) {
            this.yEnd = yEnd;
        }
    }
}
class operation
{
    public int xEnd, yEnd, xStart, yStart;
    operation(){}
    operation(int xend, int yend, int xstart, int ystart)
    {
        xEnd = xend;
        yEnd = yend;
        xStart = xstart;
        yStart = ystart;
    }
}