import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PasswordSecurityAnalyzer {

    static final String CHARS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    static final String[] COMMON_PASSWORDS = {
            "123456",
            "password",
            "admin",
            "india123",
            "welcome",
            "qwerty",
            "abc123",
            "letmein"
    };

    static AtomicBoolean found = new AtomicBoolean(false);

    static String foundPassword = "";

    static long attempts = 0;

    // ================= PASSWORD STRENGTH =================

    public static void analyzeStrength(String password) {

        int score = 0;

        if (password.length() >= 8)
            score++;

        if (password.matches(".*[A-Z].*"))
            score++;

        if (password.matches(".*[a-z].*"))
            score++;

        if (password.matches(".*[0-9].*"))
            score++;

        if (password.matches(".*[!@#$%^&*()].*"))
            score++;

        System.out.println("\n====== PASSWORD STRENGTH ======");

        if (score <= 2)
            System.out.println("Strength: WEAK");

        else if (score <= 4)
            System.out.println("Strength: MEDIUM");

        else
            System.out.println("Strength: STRONG");

        int charsetSize = 26;

        if (password.matches(".*[A-Z].*"))
            charsetSize += 26;

        if (password.matches(".*[0-9].*"))
            charsetSize += 10;

        double entropy =
                password.length() *
                        (Math.log(charsetSize) / Math.log(2));

        System.out.printf("Entropy : %.2f bits\n", entropy);

        double combinations =
                Math.pow(charsetSize, password.length());

        double attemptsPerSecond = 1000000;

        double seconds =
                combinations / attemptsPerSecond;

        System.out.printf(
                "Estimated Crack Time: %.2f seconds\n",
                seconds
        );

        System.out.println("==============================\n");
    }

    // ================= DICTIONARY ATTACK =================

    public static boolean dictionaryAttack(String target) {

        System.out.println(
                "[INFO] Starting Dictionary Attack...\n"
        );

        for (String word : COMMON_PASSWORDS) {

            attempts++;

            System.out.println("[TRYING] " + word);

            if (word.equals(target)) {

                foundPassword = word;

                System.out.println(
                        "\n[SUCCESS] Password Found using Dictionary Attack!"
                );

                return true;
            }
        }

        System.out.println(
                "\n[INFO] Dictionary Attack Failed."
        );

        return false;
    }

    // ================= BRUTE FORCE =================

    public static void bruteForce(
            String target,
            String current,
            int maxLength
    ) {

        if (found.get())
            return;

        attempts++;

        if (attempts % 5000 == 0) {

            System.out.println(
                    "[ATTEMPT " +
                            attempts +
                            "] Trying: " +
                            current
            );
        }

        if (current.equals(target)) {

            found.set(true);

            foundPassword = current;

            System.out.println(
                    "\n[SUCCESS] Password Cracked!"
            );

            return;
        }

        if (current.length() >= maxLength)
            return;

        for (int i = 0; i < CHARS.length(); i++) {

            bruteForce(
                    target,
                    current + CHARS.charAt(i),
                    maxLength
            );
        }
    }

    // ================= THREAD CLASS =================

    static class CrackThread extends Thread {

        String target;

        char start;

        char end;

        CrackThread(
                String target,
                char start,
                char end
        ) {

            this.target = target;

            this.start = start;

            this.end = end;
        }

        @Override
        public void run() {

            for (char c = start; c <= end; c++) {

                bruteForce(
                        target,
                        String.valueOf(c),
                        target.length()
                );
            }
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println(
                "========================================"
        );

        System.out.println(
                " INTELLIGENT PASSWORD SECURITY ANALYZER "
        );

        System.out.println(
                "========================================"
        );

        System.out.print("\nEnter Password: ");

        String password = sc.nextLine();

        analyzeStrength(password);

        long startTime =
                System.currentTimeMillis();

        boolean success =
                dictionaryAttack(password);

        CrackThread t1 = null;
        CrackThread t2 = null;
        CrackThread t3 = null;

        if (!success) {

            System.out.println(
                    "\n[INFO] Starting Multithreaded Brute Force...\n"
            );

            t1 = new CrackThread(password, 'a', 'i');

            t2 = new CrackThread(password, 'j', 'r');

            t3 = new CrackThread(password, 's', 'z');

            t1.start();
            t2.start();
            t3.start();

            try {

                t1.join();
                t2.join();
                t3.join();

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        long endTime =
                System.currentTimeMillis();

        // ================= FINAL REPORT =================

        System.out.println(
                "\n=================================="
        );

        System.out.println(
                "            FINAL REPORT          "
        );

        System.out.println(
                "=================================="
        );

        if (found.get() || success) {

            System.out.println(
                    "Password Cracked : " +
                            foundPassword
            );

        } else {

            System.out.println(
                    "Password Not Found"
            );
        }

        System.out.println(
                "Total Attempts : " + attempts
        );

        System.out.println(
                "Time Taken : " +
                        (endTime - startTime) +
                        " ms"
        );

        System.out.println(
                "=================================="
        );

        sc.close();
    }
}
