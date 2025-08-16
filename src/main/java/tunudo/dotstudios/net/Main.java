package tunudo.dotstudios.net;

import tunudo.dotstudios.net.calculations.PI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the amount of digits you want to calculate: ");
        long digits = sc.nextInt();
        System.out.println("Algorithms for PI calculation: ");
        System.out.println("Digits: " + digits);
        System.out.println(" - Values: ");
        PI.Algorithms[] algorithms = PI.Algorithms.values();
        for (PI.Algorithms algorithm : algorithms) {
            System.out.println("  - " + algorithm.name().replace("_", " ") + ": " + new PI().calculate(algorithm, digits));
        }
    }
}