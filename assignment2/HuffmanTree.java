public class HuffmanTree {
    public byte Byte;
    public int weight;
    public String hufcode;
    public HuffmanTree lnode, rnode;
}


class WeightNode implements Comparable<WeightNode> {
    public byte Byte;
    public int weight;

    public int compareTo(WeightNode node){
        if (this.weight<node.weight) {return 1;}
        else if (this.weight>node.weight){return -1;}
        return 0;
    }
}