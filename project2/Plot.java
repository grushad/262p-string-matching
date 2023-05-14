package project2;
import java.util.*;
import java.io.*;

public class Plot {
    
    public static void main(String[] args) {
    
        KMP kmp = new KMP();
        BMH bmh = new BMH();
        Brute brute = new Brute();
    
        inp1(kmp, bmh, brute);
        jfk(kmp, bmh, brute);        
    }

    static String generateRandomString(int len, Random rand){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < len; i++){
            int ascii = rand.nextInt(94) + 32; //32 -> 126 => 94 ascii characters
            char c = (char)(ascii + '0');
            str.append(c);
        }
        return str.toString();
    }   
    
    static void saveToFile(Map<Integer, Double> map, String fileName){
        File file = new File(fileName);
        BufferedWriter bf = null;  
        try {            
            bf = new BufferedWriter(new FileWriter(file));  
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {                
                bf.write(entry.getKey() + ":"+ entry.getValue());                  
                bf.newLine();
            }  
            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {               
                bf.close();
            }
            catch (Exception e) {
            }
        }
    }

    static String readFile(String fileName){
        StringBuilder sb = new StringBuilder();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              sb.append(myReader.nextLine());              
            }
            myReader.close();
          } catch (FileNotFoundException e) {            
          }
          return sb.toString();
    }

    static void readPatterns(String fileName, Map<Integer, List<String>> patterns){
        int startLen = 2, endLen = 10;
        try {
                File myObj = new File(fileName);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    int len = data.length();
                    if(len <= endLen && len >= startLen){
                        if(!patterns.containsKey(len)){
                            patterns.put(len, new ArrayList<>());
                        }
                        patterns.get(len).add(data);
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {            
            }
    }

    static void jfk(KMP kmp, BMH bmh, Brute brute){
        //run algo on jfk speech as text
        //read file as text
        String speechText = readFile("/Users/grushadharod/Desktop/spring 2023/262p/jfk-speech.txt");
        //read english words as pattern -> confine length to 10 
        Map<Integer, List<String>> patterns = new HashMap<>();
        readPatterns("/Users/grushadharod/Desktop/spring 2023/262p/proj1/words2.txt", patterns);
        // for(Map.Entry<Integer, List<String>> entry: patterns.entrySet()){
        //     System.out.println(entry.getKey() + " " + entry.getValue().size());
        // }

        Map<Integer, Double> kmpRuntimes = new HashMap<>();
        Map<Integer, Double> bmhRuntimes = new HashMap<>();
        Map<Integer, Double> bruteRuntimes = new HashMap<>();

        //for each pattern of length 2, 3, 4, 5, 6, ... 10 -> calculate avg runtime for each pattern length
        long durationKmp = 0, durationBmh = 0, durationBrute = 0;
        for(Map.Entry<Integer, List<String>> entry : patterns.entrySet()){
            int inpSize = entry.getKey();
            int size = 500;
            for(String pat: entry.getValue()){
                if(size == 0)
                    break;
                long startTime = System.nanoTime();                
                kmp.match(speechText, pat);
                durationKmp += System.nanoTime() - startTime;
                
                startTime = System.nanoTime();                
                bmh.match(speechText, pat);
                durationBmh += System.nanoTime() - startTime;

                startTime = System.nanoTime();                
                brute.match(speechText, pat);
                durationBrute += System.nanoTime() - startTime;  
                size--;                              
            }            
            kmpRuntimes.put(inpSize, (double)durationKmp / 500); 
            bmhRuntimes.put(inpSize, (double)durationBmh / 500); 
            bruteRuntimes.put(inpSize, (double)durationBrute / 500);          
        }

        // System.out.println("KMP: " + kmpRuntimes + "\n");
        // System.out.println("BMH: " + bmhRuntimes + "\n");
        // System.out.println("Brute: " + bruteRuntimes + "\n");

        saveToFile(kmpRuntimes, "./kmp-runtimes-jfk.txt");
        saveToFile(bmhRuntimes, "./bmh-runtimes-jfk.txt");
        saveToFile(bruteRuntimes, "./brute-runtimes-jfk.txt");
    }

    static void inp1(KMP kmp, BMH bmh, Brute brute){
        Random rand = new Random();        
        int textLen = 10000;

        String text = generateRandomString(textLen, rand);
        Map<Integer, Double> kmpRuntimes = new HashMap<>();
        Map<Integer, Double> bmhRuntimes = new HashMap<>();
        Map<Integer, Double> bruteRuntimes = new HashMap<>();

        long durationKmp = 0, durationBmh = 0, durationBrute = 0, startTime = 0;

        for(int i = 2; i <= 10; i++){
            for(int j = 0; j < 500; j++){
                String pattern = generateRandomString(i, rand);

                startTime = System.nanoTime();                
                kmp.match(text, pattern);
                durationKmp += System.nanoTime() - startTime;            
                
                startTime = System.nanoTime();            
                bmh.match(text, pattern);
                durationBmh += System.nanoTime() - startTime;            
                
                startTime = System.nanoTime();            
                brute.match(text, pattern);
                durationBrute += System.nanoTime() - startTime;                
            }
            kmpRuntimes.put(i, (double)durationKmp / 500);
            bmhRuntimes.put(i, (double)durationBmh / 500);
            bruteRuntimes.put(i, (double)durationBrute / 500);
        }

        // System.out.println("KMP: " + kmpRuntimes + "\n");
        // System.out.println("BMH: " + bmhRuntimes + "\n");
        // System.out.println("Brute: " + bruteRuntimes + "\n");

        saveToFile(kmpRuntimes, "./kmp-runtimes-ip1.txt");
        saveToFile(bmhRuntimes, "./bmh-runtimes-ip1.txt");
        saveToFile(bruteRuntimes, "./brute-runtimes-ip1.txt");

    }
}
