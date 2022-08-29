import java.io.*;
import java.util.*;

public class AddSparseMatrix {
    public static void main(String[] args) throws Exception{
        File file1=new File(args[0]);
        FileInputStream fis1=new FileInputStream(file1);
        InputStreamReader isr1=new InputStreamReader(fis1);
        BufferedReader br1=new BufferedReader(isr1);

        File file2=new File(args[1]);
        FileInputStream fis2=new FileInputStream(file2);
        InputStreamReader isr2=new InputStreamReader(fis2);
        BufferedReader br2=new BufferedReader(isr2);

        FileWriter fwriter = new FileWriter(args[2], false);
        BufferedWriter bwriter = new BufferedWriter(fwriter);

        String line1=br1.readLine();
        String temp1[]=line1.split(",");
        int r1=Integer.parseInt(temp1[0]);
        int c1=Integer.parseInt(temp1[1].substring(1));
        Matrix matrix1=new Matrix(r1,c1);
        line1 = br1.readLine();
        String[] strArr1;
        while (line1!=null){
            temp1=line1.split(" ");
            if (!temp1[1].equals(":")){
                for (int i=1;i<temp1.length;i++){
                    strArr1=temp1[i].split(":");
                    NonZero nz1=new NonZero(Integer.parseInt(temp1[0]),Integer.parseInt(strArr1[0]),Integer.parseInt(strArr1[1]));
                    matrix1.list.add(nz1);
                }
            }
            line1=br1.readLine();
        }

        String line2=br2.readLine();
        String temp2[]=line2.split(",");
        int r2=Integer.parseInt(temp2[0]);
        int c2=Integer.parseInt(temp2[1].substring(1));
        Matrix matrix2=new Matrix(r2,c2);
        line2 = br2.readLine();
        String[] strArr2;
        while (line2!=null){
            temp2=line2.split(" ");
            if (!temp2[1].equals(":")){
                for (int i=1;i<temp2.length;i++){
                    strArr2=temp2[i].split(":");
                    NonZero nz2=new NonZero(Integer.parseInt(temp2[0]),Integer.parseInt(strArr2[0]),Integer.parseInt(strArr2[1]));
                    matrix2.list.add(nz2);
                }
            }
            line2=br2.readLine();
        }

        Matrix matrix=Matrix.add(matrix1,matrix2);

        bwriter.write(Integer.toString(r1)+", "+Integer.toString(c1));
        ArrayList<NonZero> list=matrix.list;
        boolean flag=false;
        int num=1;
        for (int i=0;i<list.size();i++){
            NonZero nz = list.get(i);
            int now_row=nz.r;
            if (now_row==num){
                flag=false;
                num++;
            }else if (now_row>num){
                while(num<now_row){
                    bwriter.newLine();
                    bwriter.write(Integer.toString(num)+" :");
                    num++;
                }
                bwriter.newLine();
                bwriter.write(Integer.toString(now_row)+" ");
                bwriter.write(Integer.toString(nz.c)+":"+Integer.toString(nz.value)+" ");
                num=now_row+1;
                continue;
            }
            if (!flag){
                bwriter.newLine();
                bwriter.write(Integer.toString(now_row)+" ");
                flag=true;
            }           
            bwriter.write(Integer.toString(nz.c)+":"+Integer.toString(nz.value)+" ");
        }
        bwriter.newLine();
        br1.close();
        br2.close();
        bwriter.flush();
        bwriter.close();
    }
}
