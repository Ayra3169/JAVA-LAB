import java.util.*;
import java.util.stream.*;

public class StreamAPIDemo {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(
            10, 15, 20, 25, 30, 35, 40, 45, 50
        );
        System.out.println("Original List: " + numbers);
        System.out.println("\nEven Numbers:");
        numbers.stream()
               .filter(n -> n % 2 == 0)
               .forEach(System.out::println);
        System.out.println("\nSquares of Numbers:");
        numbers.stream()
               .map(n -> n * n)
               .forEach(System.out::println);
        System.out.println("\nSorted Squares of Even Numbers:");
        List<Integer> result = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(result);
        long count = numbers.stream()
                .filter(n -> n > 25)
                .count();
        System.out.println("\nNumbers greater than 25: " + count);
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("Sum using Stream API: " + sum);
        System.out.println("\n===== Using Normal Loop =====");
        List<Integer> loopResult = new ArrayList<>();
        for (int n : numbers) {
            if (n % 2 == 0) {
                loopResult.add(n * n);
            }
        }
        Collections.sort(loopResult);
        System.out.println("Loop Result: " + loopResult);
        System.out.println("\n===== Using Stream API =====");
        List<Integer> streamResult = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Stream Result: " + streamResult);
        System.out.println("\nBoth Results are Equal: "
                + loopResult.equals(streamResult));
    }
}
