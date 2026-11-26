
package hotel.presentation;

import java.util.Scanner;
import hotel.service.LogInService;

public class LogInMenu {

    private Scanner scanner;
    private LogInService logInService;

    public LogInMenu(Scanner scanner, LogInService loginService) {
        this.scanner = scanner;
        this.logInService = loginService;
    }

    public boolean display() {

        try {

            clearScreen();

            // LOGIN HEADER
            displayLine("top");
            displayLine("blank");

            System.out.println("║                 THE ISLAND SUITES                ║");
            System.out.println("║              LUXURY HOTEL MANAGEMENT             ║");

            displayLine("blank");
            displayLine("middle");
            displayLine("blank");

            System.out.println("║                    ADMIN LOGIN                   ║");

            displayLine("blank");

            // USERNAME INPUT
            System.out.print("║  Username: ");
            String username = scanner.nextLine();

            // PASSWORD INPUT
            System.out.print("║  Password: ");
            String password = readPassword();

            displayLine("blank");
            displayLine("bottom");

            // LOGIN VALIDATION
            if (logInService.login(username, password)) {

                clearScreen();

                // SUCCESS HEADER
                displayLine("top");
                displayLine("blank");

                System.out.println("║                 THE ISLAND SUITES                ║");
                System.out.println("║              LUXURY HOTEL MANAGEMENT             ║");

                displayLine("blank");
                displayLine("middle");
                displayLine("blank");

                System.out.println("║                LOGIN SUCCESSFUL!                 ║");

                displayLine("blank");

                System.out.println("║             Welcome, Administrator!              ║");

                displayLine("blank");

                System.out.println("║                THE ISLAND SUITES                 ║");

                displayLine("blank");
                displayLine("bottom");

                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();

                return true;

            } else {

                System.out.println("\nInvalid Username or Password!");
                System.out.println("Please check your login credentials.");

                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();

                return false;
            }

        } catch (Exception e) {

            System.out.println("\nAn error occurred: " + e.getMessage());
            System.out.println("Please try again.");

            return false;
        }
    }

    // REUSABLE BOX LAYOUT
    private void displayLine(String position) {

        if (position.equals("top")) {

            System.out.println("╔══════════════════════════════════════════════════╗");

        } else if (position.equals("middle")) {

            System.out.println("╠══════════════════════════════════════════════════╣");

        } else if (position.equals("bottom")) {

            System.out.println("╚══════════════════════════════════════════════════╝");

        } else {

            System.out.println("║                                                  ║");
        }
    }

    
    private String readPassword() {

        try {
            java.io.File tempFile =
                java.io.File.createTempFile("hotelpass", ".txt");

            String filePath = tempFile.getAbsolutePath()
                .replace("'", "''");

            ProcessBuilder processBuilder = new ProcessBuilder(
                "powershell.exe",
                "-NoProfile",
                "-Command",

                "$p = ''; " +
                "while ($true) { " +

                "$k = [Console]::ReadKey($true); " +

                "if ($k.Key -eq 'Enter') { break }; " +

                "if ($k.Key -eq 'Backspace') { " +
                    "if ($p.Length -gt 0) { " +
                        "$p = $p.Substring(0, $p.Length - 1); " +
                        "[Console]::Write([char]8); " +
                        "[Console]::Write(' '); " +
                        "[Console]::Write([char]8); " +
                    "} " +
                "} elseif (-not [char]::IsControl($k.KeyChar)) { " +
                    "$p += $k.KeyChar; " +
                    "[Console]::Write('*'); " +
                "} " +

                "}; " +

                "[System.IO.File]::WriteAllText('" +
                filePath + "', $p); " +

                "[Console]::WriteLine()"
            );

            processBuilder.inheritIO();

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                tempFile.delete();
                return "";
            }

            String password =
                java.nio.file.Files.readString(tempFile.toPath());

            tempFile.delete();

            return password;

        } catch (Exception e) {

            System.out.println("\nUnable to read password: "
                + e.getMessage());

            return "";
        }
    }

    // CLEAR SCREEN
    private void clearScreen() {

        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
}
