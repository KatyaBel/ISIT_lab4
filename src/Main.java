import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    static ArrayList<Choice> vote_list = new ArrayList<>();
    public static void main(String[] args) {
        System.out.print("Введите кол-во кандидатов: ");
        int n = scan.nextInt();
        int[] mas1 = new int[n];
        for(int i = 0; i < n; i++) {
            mas1[i] = i;
        }
        System.out.println("Введите кол-во проголосовавших для каждой альтенативы:");
        make_alters(mas1, n);
        System.out.println("\n1) Модель относительного большинства");
        int[] sum = new int[n];
        for(int i = 0; i < vote_list.size(); i++) {
            sum[alphabet.indexOf(vote_list.get(i).alter.substring(0, 1))] += vote_list.get(i).voices;
        }
        System.out.println("Кол-во голосов:");
        int j = -1, max = -1;
        for(int i = 0; i < sum.length; i++) {
            System.out.println(alphabet.charAt(i)+" = "+sum[i]);
            if (sum[i] > max) {
                max = sum[i];
                j = i;
            }
        }
        System.out.println("Выиграл кандидат: "+alphabet.charAt(j));
        System.out.println("\n2) Модель Кондорсе (правило Симпсона)");
        ArrayList<Choice> vote_list_k = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            for(j = 0; j < n; j++) {
                if (i != j) {
                    vote_list_k.add(new Choice(alphabet.charAt(i)+""+alphabet.charAt(j), -1));
                }
            }
        }
        for(int i = 0; i < vote_list_k.size(); i++) {
            int voices = 0;
            for(j = 0; j < vote_list.size(); j++) {
                if (vote_list.get(j).alter.indexOf(vote_list_k.get(i).alter.charAt(0)) < vote_list.get(j).alter.indexOf(vote_list_k.get(i).alter.charAt(1))) {
                    voices += vote_list.get(j).voices;
                }
            }
            vote_list_k.get(i).voices = voices;
            System.out.println("'"+vote_list_k.get(i).alter.charAt(0)+" < "+vote_list_k.get(i).alter.charAt(1)+"' = "+vote_list_k.get(i).voices);
        }
        int[] N = new int[n];
        for(int i = 0; i < n; i++) {
            int min = 10000;
            for(j = 0; j < vote_list_k.size(); j++) {
                if (vote_list_k.get(j).alter.charAt(0) == alphabet.charAt(i) && min > vote_list_k.get(j).voices) {
                    min = vote_list_k.get(j).voices;
                }
            }
            N[i] = min;;
        }
        System.out.println("Оценки Симпсона:");
        j = -1; max = -1;
        for(int i = 0; i < N.length; i++) {
            System.out.println(alphabet.charAt(i)+" = "+N[i]);
            if (N[i] > max) {
                max = N[i];
                j = i;
            }
        }
        System.out.println("Выиграл кандидат: "+alphabet.charAt(j));
        System.out.println("\n3) Модель Борда");
        sum =  new int[n];
        System.out.println("Кол-во голосов:");
        for(int i = 0; i < n; i++) {
            System.out.print(alphabet.charAt(i)+": ");
            for(j = 0; j < n; j++) {
                int sum1 = 0;
                for(int k = 0; k < vote_list.size(); k++) {
                    if (vote_list.get(k).alter.charAt(j) == alphabet.charAt(i)) {
                        sum1 += vote_list.get(k).voices;
                    }
                }
                System.out.print(sum1+"*"+(n-j-1));
                if (j != n-1) {
                    System.out.print(" + ");
                } else {
                    System.out.print(" = ");
                }
                sum[i] += sum1*(n-j-1);
            }
            System.out.println(sum[i]);
        }
        j = -1; max = -1;
        for(int i = 0; i < sum.length; i++) {
            if (sum[i] > max) {
                max = sum[i];
                j = i;
            }
        }
        System.out.println("Выиграл кандидат: "+alphabet.charAt(j));
    }
    static void make_alters(int[] mas, int n) {
        if (diff_elem(mas)) {
            String option = "";
            System.out.print("для '");
            for(int i = 0; i < mas.length; i++) {
                option += alphabet.charAt(mas[i]);
                System.out.print(alphabet.charAt(mas[i]));
                if (i != mas.length - 1) {
                    System.out.print(" < ");
                }
            }
            System.out.print("' = ");
            int voices = scan.nextInt();
            vote_list.add(new Choice(option, voices));
        }
        int index = -1;
        for(int i = mas.length - 1; i >= 0; i--) {
            if (mas[i] < n-1) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        } else {
            int[] mas1 = new int[mas.length];
            for(int i = 0; i < mas1.length; i++) {
                if (i < index) {
                    mas1[i] = mas[i];
                } else if (i == index) {
                    mas1[i] = mas[index] + 1;
                } else {
                    mas1[i] = 0;
                }
            }
            make_alters(mas1, n);
        }
    }
    static boolean diff_elem(int[] mas) {
        boolean answer = true;
        for(int i = 0; i < mas.length; i++) {
            int count = 0;
            for(int j = 0; j < mas.length; j++) {
                if (i != j && mas[i] == mas[j]) {
                    count++;
                }
            }
            if (count > 0) {
                answer = false;
                break;
            }
        }
        return answer;
    }
}