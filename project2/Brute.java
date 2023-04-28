package project2;

public class Brute {

  public int match(String T, String P) {
    /** Your code goes here */
    int n = T.length();
    int m = P.length();
    if(m > n)
      return -1;
    for(int i = 0; i <= (n - m); i++){
      int j = 0;
      while(j < m && T.charAt(i + j) == P.charAt(j)){
        j++;
      }
      if(j == m)
        return i;      
    }
    return -1;
  }
  
}
