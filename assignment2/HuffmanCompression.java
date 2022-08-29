import java.io.*;
import java.util.*;

public class HuffmanCompression {
    private static PriorityQueue<HuffmanTree> p_queue = null;

    public static String getCompressedCode(String inputText, String[] huffmanCodes) {
        String compressedCode = "";
        // Your code obtains the compressed code for inputText based on huffmanCodes
        // For example, if inputText="SUSIE SAYS IT IS EASY\n", and '\n' denotes linefeed.
        // , and huffmanCodes for inputText are: 
        // Space="00", A="010", T="0110", Linefeed="01110", U="01111", S="11",I="110",Y="1110",E="1111".
        // Then, your program will return the String shown in the following line:
        // "11011111111011110011010111011001100110001101100111101011111001110"
        //  S U    S I  E   SpS A  Y   S   I  T     I  S   E   A  S Y   Lf
        char[] c_arr=inputText.toCharArray();
        for (char c:c_arr){
            int c_ascii=Integer.valueOf(c);
            compressedCode+=huffmanCodes[c_ascii];
        }
        if (compressedCode.equals("")){return null;}
        else {return compressedCode;}
    }
    public static String[] getHuffmanCode(String inputText) {
        HufCompare new_cmp = new HufCompare();
        p_queue = new PriorityQueue<HuffmanTree>(new_cmp);
        HuffmanTree root = new HuffmanTree();
        String[] huffmanCodes = new String[128];
        // Your code would obtain huffmanCodes for inputText
        // huffmanCodes[i]: huffman code of character with ASCII code i
        // if a character does not appear in inputText, then its huffmanCodes = null
        // For example, if inputText="SUSIE SAYS IT IS EASY\n", and '\n' denotes linefeed.
        // A possible huffmanCodes would be:
        // Space="00",A="010",T="0110",Linefeed="01110",U="01111",S="11",I="110",Y="1110",E="1111".
        WeightNode[] w_nodes = new WeightNode[128];
        for (int i=0;i<128;i++){
            w_nodes[i] = new WeightNode();
            w_nodes[i].weight=0;
            w_nodes[i].Byte=(byte)i;
        }
        char[] c_arr=inputText.toCharArray();
        for (char c:c_arr){
            int c_ascii=Integer.valueOf(c);
            w_nodes[c_ascii].weight++;
        }
        Arrays.sort(w_nodes); 
        for (int i=0;i<128;i++){
            if (w_nodes[i].weight==0){huffmanCodes[i]=null;continue;}
            HuffmanTree t=new HuffmanTree();
            t.Byte=w_nodes[i].Byte;
            t.weight=w_nodes[i].weight;
            p_queue.add(t);
        }
        createHufTree(p_queue);
        root=p_queue.peek();
        computeHufCode(root, "", huffmanCodes);
        return huffmanCodes;
    }
    public static void computeHufCode(HuffmanTree root, String s, String[] huffmanCodes){
        if(root.lnode==null && root.rnode==null){
            root.hufcode=s;
            huffmanCodes[root.Byte]=root.hufcode;
            return;
        }
        if(root.lnode!=null){computeHufCode(root.lnode, s+'0', huffmanCodes);}
        if(root.rnode!=null){computeHufCode(root.rnode, s+'1', huffmanCodes);}
    }
    public static void createHufTree(PriorityQueue<HuffmanTree> p_queue){
        while (p_queue.size()>1){
            HuffmanTree t1=p_queue.poll();
            HuffmanTree t2=p_queue.poll();
            HuffmanTree subParent = new HuffmanTree();
            subParent.weight=t1.weight+t2.weight;
            subParent.lnode=t1;
            subParent.rnode=t2;
            p_queue.add(subParent);
        }
    }
    public static void main(String[] args) throws Exception {
        // obtain input text from a text file encoded with ASCII code
        String inputText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[0])), "US-ASCII");
        // get Huffman codes for each character and write them to a dictionary file
        String[] huffmanCodes = HuffmanCompression.getHuffmanCode(inputText);
        FileWriter fwriter1 = new FileWriter(args[1], false);
        BufferedWriter bwriter1 = new BufferedWriter(fwriter1);
        for (int i = 0; i < huffmanCodes.length; i++) 
            if (huffmanCodes[i] != null) {
                bwriter1.write(Integer.toString(i) + ":" + huffmanCodes[i]);
                bwriter1.newLine();
            }
        bwriter1.flush();
        bwriter1.close();
        // get compressed code for input text based on huffman codes of each character
        String compressedCode = HuffmanCompression.getCompressedCode(inputText, huffmanCodes);
        FileWriter fwriter2 = new FileWriter(args[2], false);
        BufferedWriter bwriter2 = new BufferedWriter(fwriter2);
        if (compressedCode != null) 
            bwriter2.write(compressedCode);
        bwriter2.flush();
        bwriter2.close();
    }
}
