package jsrc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cal {
    public static class PhoneNumber {
        private int areaCode;
        private String prefix;
        private String lineNumber;

        public PhoneNumber(int areaCode, String prefix, String lineNumber) {
            super();
            this.areaCode = areaCode;
            this.prefix = prefix;
            this.lineNumber = lineNumber;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + areaCode;
            result = prime * result + ((lineNumber == null) ? 0 : lineNumber.hashCode());
            result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PhoneNumber other = (PhoneNumber) obj;
            if (areaCode != other.areaCode)
                return false;
            if (lineNumber == null) {
                if (other.lineNumber != null)
                    return false;
            } else if (!lineNumber.equals(other.lineNumber))
                return false;
            if (prefix == null) {
                if (other.prefix != null)
                    return false;
            } else if (!prefix.equals(other.prefix))
                return false;
            return true;
        }

    }

    public static void main(String[] args) {
       /* String str = "abc";
        String str1 = "abc";
        String str2 = new String("abc");
        System.out.println(str == str1);
        System.out.println(str1 == "abc");
        System.out.println(str2 == "abc");
        System.out.println(str1 == str2);
        System.out.println(str1.equals(str2));
        System.out.println(str1 == str2.intern());
        System.out.println(str2 == str2.intern());
        System.out.println(str1.hashCode() == str2.hashCode());
        System.out.println(str.hashCode());
        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());*/
        String s1 = new String("Programming");
        System.out.println(s1.intern());
       /* String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1);
        System.out.println(s1 == s1.intern());*/
    }
}
