import java.util.LinkedList;

public class Node implements Comparable<Node>{
    int[] arr;
    int move;
    int sum;
    int zero_pos;
    LinkedList<Node> moved_path;
    public Node(int[] arr, int move, int zero_pos){
        this.moved_path=new LinkedList<Node>();
        this.arr=arr;
        this.move=move;
        this.zero_pos=zero_pos;
        this.setSum();
    }
    public void setSum(){
        this.sum=this.move;
        for (int i=0;i<9;i++){
            if (arr[i]!=0){
                int x=i%3;
                int y=i/3;
                this.sum+=Math.abs(x-(arr[i]-1)%3)+Math.abs(y-(arr[i]-1)/3);
            }
        }
    }
    public int compareTo(Node o){
        return (this.sum-o.sum);
    }
    public static void swap(Node x, int a, int b){
        int[] temp=x.arr;
        int k=temp[a];
        temp[a]=temp[b];
        temp[b]=k;
    }  
    public Node setPath(LinkedList<Node> path){
        this.moved_path = new LinkedList<Node>();
        for (Node k : path) {
          this.moved_path.add(k);
        }
        return this;
    }
    public static Node copy(Node x){
        Node copy_node=new Node(new int[9],x.move,x.zero_pos);
        for (int i=0;i<9;i++){
            copy_node.arr[i]=x.arr[i];
        }
        return copy_node;
    }
    public static String arrayToString(int[] x){
        StringBuilder sb = new StringBuilder();
        for (int k : x) {
            sb.append(k).append('-');
        }
        return sb.toString();
    }
    static int[][] moveAble=new int[][]{
        {1,3,-1,-1},
        {0,2,4,-1},
        {1,5,-1,-1},
        {0,4,6,-1},
        {1,3,5,7},
        {2,4,8,-1},
        {3,7,-1,-1},
        {4,6,8,-1},
        {5,7,-1,-1}};
    static LinkedList<Node> now_path;
}
