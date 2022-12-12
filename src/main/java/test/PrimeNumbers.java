package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimeNumbers {

    @Test
    public void testPrintPrimeNumbers() {
        assertEquals("2 3 5 7 11 13 17 19", printPrimeNumbers(20));
        assertEquals("2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97 101", printPrimeNumbers(101));
        assertEquals("2 3 5 7", printPrimeNumbers(8));

        assertEquals("No prime number found", printPrimeNumbers(1));
        assertEquals("No prime number found", printPrimeNumbers(0));
        assertEquals("No prime number found", printPrimeNumbers(-1));
    }

    /**
     * Complete the method printPrimeNumbers that takes an integer parameter and
     * returns a String containing a space separated list of all of the prime numbers starting at 2 and
     * all the way up to and including the given integer.
     * If the given number is negative or no prime numbers are found, return "No prime number found".
     * <p>
     * Hint: you can reuse isPrime method in this Class (that is make use of method calls) to complete this question.
     * Note that there is no extra space at the end of the String returned.
     * @return
     */
    public String printPrimeNumbers(int num) {
        // Answer
        //check for every number from 2 to N
        int i;
        String answer = "";

        for (i = 2; i <= num; i++) {
            //check if current number is prime
            if (isPrime(i)) {
//                System.out.print(i + " ");
                String s = String.valueOf(i);
                // return (s + " "); // nirereturn mo kase agad, complete mo muna dapat bago mo return
                answer = answer + s + " ";
            }
        }

        if (num < 0 || answer.length() == 0) {
            return "No prime number found";
        } else {
            return answer.trim(); // trim() para lang tanggalin yung space sa dulo
        }

//        if (num < 0 || !isPrime(i)) {
//            return "No prime number found";
//        }

    }

    public boolean isPrime(int num) {

        //since 0 and 1 is not prime return false.
        if (num <= 1) {
            return false;
        }

        //Run a loop from 2 to n-1
        for (int i = 2; i < num; i++) {
            if (num % i == 0)
                return false;
        }
        //otherwise, num is prime number.
        return true;
    }
}
