package project2;
import java.util.HashMap;
import java.util.Map;
public class BMH {
  
  public int match(String T, String P) {
    /** Your code goes here */
    Map<Character, Integer> last = new HashMap<>();
    lastOccurrence(last, P);

    int n = T.length(), m = P.length();
    int i = m - 1, j = m - 1;
    while(i < n - 1){
      if(T.charAt(i) == P.charAt(j)){
        if(j == 0)
          return i;
        else{
          i--;
          j--;
        }
      }else{
        int l = last.getOrDefault(T.charAt(i), -1); // 4
        i = i + m - Math.min(j, l + 1);
        j = m -1;
      }
    }
    return -1;
  }

  void lastOccurrence(Map<Character, Integer> last, String P){
    int m = P.length();
    for(int i = 0; i < m; i++)
      last.put(P.charAt(i), i);    
  }

}
