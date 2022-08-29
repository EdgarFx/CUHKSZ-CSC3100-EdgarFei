import java.io.*;
import java.util.*;

public class HuffmanDecompression {
    public static void main(String[] args) throws Exception {
        // args[0] is the file of compressed text
        // args[1] is the dictionary file
        // args[2] is the file of decompressed text to output
        //
        // For example: if your compressed text is:
        // 11011111111011110011010111011001100110001101100111101011111001110
        //
        // , and your dictionary file is:
        // 10:01110
        // 32:00
        // 65:010
        // 69:1111
        // 73:110
        // 83:11
        // 84:0110
        // 85:01111
        // 89:1110
        //
        // Then your expected output would be:
        // SUSIE SAYS IT IS EASY\n
        // where '\n' denotes linefeed character.
        // Note: 10 is the ASCII code of '\n', 32 is ' ', 65 is 'A', ...
        String compressed = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[0])), "US-ASCII");
        FileInputStream dictionary_is = new FileInputStream(args[1]);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dictionary_is));
        String str = null;
        String[] dic;
        Map<String, String> map = new HashMap<String, String>();
        while ((str=bufferedReader.readLine()) != null){
            dic=str.split(":");
            map.put(dic[1], dic[0]);
        }
        bufferedReader.close();
        FileWriter fwriter = new FileWriter(args[2], false);
        BufferedWriter bwriter = new BufferedWriter(fwriter);
        String mid="";
        for (int i=0;i<compressed.length();i++){
            mid+=compressed.charAt(i);
            if (map.containsKey(mid)){
                int ascii = Integer.parseInt(map.get(mid));
                bwriter.write((char)ascii);
                mid="";
                continue;
            }
        }
        bwriter.flush();
        bwriter.close();
        return;
    }
}
