import java.util.Comparator;

public class HufCompare implements Comparator<HuffmanTree>{
    public int compare(HuffmanTree t1, HuffmanTree t2) {
        if (t1.weight<t2.weight){return -1;}
        else if (t1.weight>t2.weight){return 1;}
        return 0;
    }
}
