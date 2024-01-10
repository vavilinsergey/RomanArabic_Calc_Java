import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Array;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Calc {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String userInput = console.nextLine();
        System.out.println(MainLogic.mainLogic(userInput)); //main вызывает calc(userInput) и выводит результат
    }

     static class RomanArabicConverter {
        private static Map<String, Integer> romanToArabicMap = new LinkedHashMap<>();
        private static Map<Integer, String> arabicToRomanMap = new LinkedHashMap<>();

        static {
            addPair("C", 100);
            addPair("XC", 90);
            addPair("L", 50);
            addPair("XL", 40);
            addPair("X", 10);
            addPair("IX", 9);
            addPair("V", 5);
            addPair("IV", 4);
            addPair("I", 1);
        }//алфавит

        private static void addPair(String roman, int arabic) {
            romanToArabicMap.put(roman, arabic);
            arabicToRomanMap.put(arabic, roman);
        }

        public static Integer getArabic(String roman) {
            int num = 0;
            for (Map.Entry<String, Integer> entry : romanToArabicMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                while (roman.startsWith(key)) {
                    num += value;
                    roman = roman.substring(key.length());
                }
            }
            return num;
        } //конвертер римских в арабские,  получает строку, возвращает число

        public static String getRoman(int arabic) {
            StringBuilder roman = new StringBuilder();
            for (Map.Entry<Integer, String> entry : arabicToRomanMap.entrySet()) {
                while (arabic >= entry.getKey()) {
                    roman.append(entry.getValue());
                    arabic -= entry.getKey();
                }
            }
            return roman.toString();
        }//конветртер арабских в римские, получает строку возвращает строку
    }

     class ExpressionEvaluator {

        public static int evaluate(int operand1, char operator, int operand2) throws IllegalArgumentException {
            if (operand1 > 10 || operand2 > 10) {
                throw new IllegalArgumentException("");
            }

            switch (operator) {
                case '+':
                    return operand1 + operand2;
                case '-':
                    return operand1 - operand2;
                case '*':
                    return operand1 * operand2;
                case '/':
                    if (operand2 == 0) return 0; // Возвращаем 0 в случае деления на ноль
                    return operand1 / operand2;
                default:
                    return 0; // Возвращаем 0 для неизвестного оператора
            }
        }
    }

     class MainLogic {
        public static String isArabicOrRoman(String expression) {
             if (expression.matches("^[0-9]+\\s*[+\\-*/]\\s*[0-9]+$")) {
                 return "arabic";
             } else if (expression.matches("^[IVX]+\\s*[+\\-*/]\\s*[IVX]+$")) {
                 return "roman";
             } else {
                 return "mixed";
             }
         }
        public static String mainLogic(String input) {
            String[] tokens = input.split(" ");

            if (tokens.length != 3) {
                throw new RuntimeException("Error"); // Выбрасываем исключение, если формат входной строки некорректен
            }

            try {
                switch (isArabicOrRoman(input)) {
                    case "arabic":
                        int operand1Arabic = Integer.parseInt(tokens[0]);
                        char operator = tokens[1].charAt(0);
                        int operand2Arabic = Integer.parseInt(tokens[2]);
                        return String.valueOf(ExpressionEvaluator.evaluate(operand1Arabic, operator, operand2Arabic));
                    case "roman":
                        int operand1Roman = RomanArabicConverter.getArabic(tokens[0]);
                        operator = tokens[1].charAt(0);
                        int operand2Roman = RomanArabicConverter.getArabic(tokens[2]);
                        int result = ExpressionEvaluator.evaluate(operand1Roman, operator, operand2Roman);

                        if (result < 1) {
                            throw new RuntimeException("Error");
                        }

                        return RomanArabicConverter.getRoman(result);
                    case "mixed":
                        throw new RuntimeException("Error");
                    default:
                        throw new RuntimeException("Error");
                }
            } catch (Exception e) {
                throw new RuntimeException("Error");
            }
        }
    }
}

