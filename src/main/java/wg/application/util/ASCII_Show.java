package wg.application.util;

public class ASCII_Show {
     public static void main(String[] args) {
          System.out.println("\t\tASCII码大写字母对照表")    ;
          char c;
          for (int i = 1, asci = 65; asci <= 90; i++, asci++)     {
          c = (char) asci;
          System.out.print(asci + "=" + c + "\t");
          if (i % 7 == 0)
              System.out.println();
          }
          System.out.println();
          System.out.println("\t\tASCII码小写字母对照表");
          for (int i = 1, asci = 97; asci <= 122; i++, asci++) {
           c = (char) asci;
           System.out.print(c + "=" + asci + "\t");
           if (i % 7 == 0)
                System.out.println();
            }
       }
}

