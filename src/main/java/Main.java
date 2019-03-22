import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String[] lines = new String[n];
        in.nextLine();

        for (int i = 0; i < n; i++) {
            lines[i] = in.nextLine();
        }

        for (int i = 0; i < n; i++) {
            char[] chars = lines[i].toCharArray();
            f1(chars);
            f2(chars);
            String res = "";
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == '$') {
                    break;
                }
                res += chars[j];
            }
            System.out.println(res);
        }
    }


    public static char[] f1(char[] chars) {
        int count = 0;
        for (int i = 0; i < chars.length - 2 - count; i++) {
            if (chars[i] == chars[i + 1] && chars[i] == chars[i + 2]) {
                fix(chars, i);
                i--;
                count++;
            }
        }
        return chars;
    }

    public static char[] f2(char[] chars) {
        int count = 0;
        for (int i = 0; i < chars.length - 3 - count; i++) {
            if (chars[i] == chars[i + 1] && chars[i + 2] == chars[i + 3]) {
                fix(chars, i + 3);
                count++;
                i--;
            }
        }
        return chars;
    }

//    public static char[] f3(char[] chars) {
//
//    }

    public static void fix(char[] chars, int index) {
//        char[] newChars = new char[chars.length - 1];
//        for (int i = 0; i < index; i++) {
//            newChars[i] = chars[i];
//        }
        for (int i = index; i < chars.length - 1; i++) {
            chars[i] = chars[i + 1];
        }
        chars[chars.length - 1] = '$';
    }

}
