import java.util.*;
import java.io.*;

public class Puzzle8{
    public static void main(String[] args) throws Exception{
        String inputText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[0])), "US-ASCII");
        FileWriter fwriter = new FileWriter(args[1], false);
        BufferedWriter bwriter = new BufferedWriter(fwriter);
        int[] start=new int[9];
        int zeroPos=0;
        int n=0;
        for (int i=0;i<inputText.length();i++){
            char ch=inputText.charAt(i);
            if (Character.isDigit(ch)){
                int num=Character.getNumericValue(ch);
                start[n]=num;
                if (num==0){zeroPos=n;}
                n++;
            }
        }
        int[][] moveAble=Node.moveAble;
        Map<String, Boolean> myMap=new HashMap<String, Boolean>();
        Queue<Node> que=new PriorityQueue<>();
        String goal_code="1-2-3-4-5-6-7-8-0-";
        que.add(new Node(start,0,zeroPos));
        myMap.put(Node.arrayToString(start), true);
        for (int j=0;j<9;j++){
            if (j==2||j==5){
                bwriter.write(Integer.toString(start[j]));
            }
            else if (j==3||j==6){
                bwriter.newLine();
                bwriter.write(Integer.toString(start[j])+" ");
            }
            else if (j==8){
                bwriter.write(Integer.toString(start[j]));
                bwriter.newLine();
                bwriter.newLine();
            }
            else{
                bwriter.write(Integer.toString(start[j])+" ");
            }
        }
        while(!que.isEmpty()){
            Node e=que.poll();
            int pos=e.zero_pos;
            for (int i=0;i<4;i++){
                Node ie=Node.copy(e);
                Node.now_path=new LinkedList<Node>();
                Node.now_path.addAll(e.moved_path);
                if (moveAble[pos][i]!=-1){
                    Node.swap(ie, pos, moveAble[pos][i]);
                    Node nn=new Node(ie.arr, e.move + 1, moveAble[pos][i]).setPath(Node.now_path);
                    Node.now_path.add(nn);
                    String k=Node.arrayToString(ie.arr);
                    if(k.equals(goal_code)){
                        for (Node node:Node.now_path){
                            for (int j=0;j<9;j++){
                                if (j==2||j==5){
                                    bwriter.write(Integer.toString(node.arr[j]));
                                }
                                else if (j==3||j==6){
                                    bwriter.newLine();
                                    bwriter.write(Integer.toString(node.arr[j])+" ");
                                }
                                else if (j==8){
                                    bwriter.write(Integer.toString(node.arr[j]));
                                    if (!Node.arrayToString(node.arr).equals(goal_code)){bwriter.newLine();bwriter.newLine();}
                                }
                                else{
                                    bwriter.write(Integer.toString(node.arr[j])+" ");
                                }
                            }
                        }
                        bwriter.flush();
                        bwriter.close();
                        return;
                    }
                    if(!myMap.containsKey(k)){
                        que.add(new Node(ie.arr,e.move+1,moveAble[pos][i]).setPath(Node.now_path));
                        myMap.put(k, true);
                    }
                }
            }
        }
        bwriter.flush();
        bwriter.close();
    }
}