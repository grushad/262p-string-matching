package project2;

public class KMP {
  
  public int match(String T, String P) {
    /** Your code goes here */
    int n = T.length(), m = P.length();
    int[] failure = new int[m];
    failureFunc(failure, P);
    int i = 0, j = 0;
    while(i < n){
      if(T.charAt(i) == P.charAt(j)){
        if(j == m - 1){
          return i - j;
        }else{
          i++;
          j++;
        }
      }else{
        if(j > 0)
          j = failure[j - 1];
        else
          i++;        
      }
    }
    return -1;
  }

  void failureFunc(int[] failure, String P){
    int m = P.length();
    int i = 0, j = 1;
    failure[0] = 0;
    while(j < m){
      if(P.charAt(i) == P.charAt(j))
        failure[j++] = ++i;        
      else if(i > 0)
        i = failure[i - 1];
      else
        failure[j++] = 0;                     
    }    
  }

}