import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginController controller = new LoginController();
        ClearConsoleService clearConsole = new ClearConsoleService();

        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            System.out.println("Selamat datang!");
            System.out.println("Silakan pilih opsi:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Keluar");

            System.out.print("Masukkan pilihan Anda: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // untuk membaca karakter newline setelah nextInt()

            clearConsole.clearConsole();

            switch (option) {
                case 1:
                    controller.login(scanner);
                    break;
                case 2:
                    controller.register(scanner);
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan layanan kami.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opsi tidak valid, silakan pilih kembali.");
            }
        }
        
        scanner.close();
    }
}