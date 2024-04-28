import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginController {

    private static final String USER_DATA_FILE = "user_data.txt";

    public boolean login(Scanner scanner) {
        ClearConsoleService clearConsole = new ClearConsoleService();

        System.out.println("Silakan pilih tipe login:");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.println("3. Back");

        System.out.print("Masukkan pilihan Anda: ");
        int loginType = scanner.nextInt();
        scanner.nextLine(); // untuk membaca karakter newline setelah nextInt()

        clearConsole.clearConsole();

        if (loginType == 3) { // Back
            return false;
        }

        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();

        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        AuthenticationService authService = new AuthenticationService();

        if (authService.authenticate(username, password, loginType)) {
            AdminController adminCon = new AdminController();
            String fullname = authService.getAttribute("fullname").toString();
            clearConsole.clearConsole();
            System.out.println("Selamat Datang " + fullname + "!\n");
            adminCon.menuAdmin(scanner);
            return true;
        } else {
            clearConsole.clearConsole();
            System.out.println("Username atau password salah. Login gagal.\n");
            return false;
        }
    }

    public void register(Scanner scanner) {
        ClearConsoleService clearConsole = new ClearConsoleService();

        System.out.println("Silakan pilih tipe registrasi:");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.println("3. Back");

        System.out.print("Masukkan pilihan Anda: ");
        int registerType = scanner.nextInt();
        scanner.nextLine(); // untuk membaca karakter newline setelah nextInt()

        clearConsole.clearConsole();

        if(registerType != 3) { // Jika Bukan "Back"
            System.out.print("Masukkan username: ");
            String newUsername = scanner.nextLine();

            System.out.print("Masukkan Nama Lengkap: ");
            String newFullname = scanner.nextLine();
    
            System.out.print("Masukkan password: ");
            String newPassword = scanner.nextLine();
    
            // Menyimpan data registrasi ke dalam file
            try (PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE, true))) {
                // Buat string JSON secara manual
                StringBuilder jsonBuilder = new StringBuilder();
                jsonBuilder.append("{");
                jsonBuilder.append("\"username\":\"").append(newUsername).append("\",");
                jsonBuilder.append("\"fullname\":\"").append(newFullname).append("\",");
                jsonBuilder.append("\"password\":\"").append(newPassword).append("\",");
                jsonBuilder.append("\"type\":").append(registerType);
                jsonBuilder.append("}");

                // Hasilkan string JSON dari StringBuilder
                String jsonString = jsonBuilder.toString();

                writer.println(jsonString);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace(); // Menampilkan informasi kesalahan jika terjadi
            }

            clearConsole.clearConsole();
    
            System.out.println("Registrasi berhasil sebagai " + newFullname + ". Silakan login.\n");
        }
    }
}