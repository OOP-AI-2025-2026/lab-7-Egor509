package ua.opnu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {

        // завдання 1
        System.out.println("Задание 1 (Простые числа)");
        Predicate<Integer> isPrime = n -> {
            if (n <= 1) return false;
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) return false;
            }
            return true;
        };

        int[] numbers = {1, 2, 3, 4, 5, 11, 15, 17, 20};
        System.out.print("Простые числа из массива " + Arrays.toString(numbers) + ": ");
        for (int n : numbers) {
            if (isPrime.test(n)) System.out.print(n + " ");
        }
        System.out.println();


        // завдання 2
        System.out.println("\nЗадание 2 (Фильтрация студентов - должники)");
        Student[] students = {
                new Student("Иванов", "КН-21", new int[]{90, 85, 100}),
                new Student("Петров", "КН-21", new int[]{60, 59, 70}),
                new Student("Сидоров", "КН-22", new int[]{40, 30, 50}),
                new Student("Коваленко", "КН-22", new int[]{95, 92, 88})
        };

        Predicate<Student> hasDebts = s -> {
            for (int mark : s.getMarks()) {
                if (mark < 60) return true;
            }
            return false;
        };

        Student[] debtors = filterStudents(students, hasDebts);
        System.out.println("Найденные должники:");
        for (Student s : debtors) {
            System.out.println(s);
        }


        // завдання 3
        System.out.println("\nЗадание 3 (Два предиката: четное и > 5)");
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isGreaterThanFive = n -> n > 5;

        Integer[] nums = {2, 4, 6, 8, 3, 10, 1};
        Integer[] filteredNums = genericFilter(nums, isEven, isGreaterThanFive);
        System.out.println("Входной массив: " + Arrays.toString(nums));
        System.out.println("Результат фильтрации: " + Arrays.toString(filteredNums));


        // завдання 4
        System.out.println("\nЗадание 4 (Consumer: Фамилия + Имя)");
        Consumer<Student> printName = s -> System.out.println("Студент: " + s.getName().toUpperCase() + " (Группа: " + s.getGroup() + ")");

        forEachStudent(students, printName);


        // завдання 5
        System.out.println("\nЗадание 5 (Predicate + Consumer)");
        System.out.println("Выводим числа, которые делятся на 3:");
        processIf(numbers, n -> n % 3 == 0, n -> System.out.println(" -> Число " + n + " делится на 3"));


        // завдання 6
        System.out.println("\nЗадание 6 (Function: 2^n)");
        Function<Integer, Integer> powerOfTwo = n -> (int) Math.pow(2, n);

        int[] powersInput = {1, 2, 3, 4, 5};
        int[] powersOutput = mapIntArray(powersInput, powerOfTwo);
        System.out.println("Степени: " + Arrays.toString(powersInput));
        System.out.println("Результат: " + Arrays.toString(powersOutput));


        // завдання 7
        System.out.println("\nЗадание 7 (Числа в слова)");
        int[] digits = {0, 1, 5, 9, 2};
        Function<Integer, String> numberToWord = n -> {
            String[] words = {"нуль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
            if (n >= 0 && n < words.length) return words[n];
            return "неизвестно";
        };

        String[] stringified = stringify(digits, numberToWord);
        System.out.println("Цифры: " + Arrays.toString(digits));
        System.out.println("Слова: " + Arrays.toString(stringified));
    }

    public static Student[] filterStudents(Student[] input, Predicate<Student> p) {
        List<Student> result = new ArrayList<>();
        for (Student s : input) {
            if (p.test(s)) {
                result.add(s);
            }
        }
        return result.toArray(new Student[0]);
    }

    public static <T> T[] genericFilter(T[] input, Predicate<T> p1, Predicate<T> p2) {
        List<T> result = new ArrayList<>();
        Predicate<T> combined = p1.and(p2);

        for (T item : input) {
            if (combined.test(item)) {
                result.add(item);
            }
        }
        return result.toArray(Arrays.copyOf(input, 0));
    }

    public static void forEachStudent(Student[] input, Consumer<Student> action) {
        for (Student s : input) {
            action.accept(s);
        }
    }

    public static void processIf(int[] input, Predicate<Integer> p, Consumer<Integer> c) {
        for (int i : input) {
            if (p.test(i)) {
                c.accept(i);
            }
        }
    }

    public static int[] mapIntArray(int[] input, Function<Integer, Integer> f) {
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = f.apply(input[i]);
        }
        return result;
    }

    public static String[] stringify(int[] input, Function<Integer, String> f) {
        String[] result = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = f.apply(input[i]);
        }
        return result;
    }
}