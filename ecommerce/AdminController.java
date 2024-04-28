import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;

public class AdminController {

    private static final String BARANG_FILE = "barang.txt";

    public void menuAdmin(Scanner scanner) {
        ClearConsoleService clearConsole = new ClearConsoleService();
        AdminController adminCon = new AdminController();

        System.out.println("Silakan pilih menu admin:");
        System.out.println("1. Tambah Barang Baru");
        System.out.println("2. Edit Barang");
        System.out.println("3. Hapus Barang");
        System.out.println("4. List Barang");
        System.out.println("5. Back");

        System.out.print("Masukkan pilihan Anda: ");
        int menuAdmin = scanner.nextInt();
        scanner.nextLine(); // untuk membaca karakter newline setelah nextInt()

        clearConsole.clearConsole();

        if (menuAdmin == 5) { // Back
        }
        else {
            if (menuAdmin == 1) { // Tambah Barang Baru
                System.out.print("ID Barang: ");
                String idBarang = scanner.nextLine();
                System.out.print("Nama Barang: ");
                String namaBarang = scanner.nextLine();
                System.out.print("Qty: ");
                int qty = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Harga Satuan: ");
                int hargaSatuan = scanner.nextInt();
                scanner.nextLine();

                try (PrintWriter writer = new PrintWriter(new FileWriter(BARANG_FILE, true))) {
                    StringBuilder jsonBuilder = new StringBuilder();
                    jsonBuilder.append("{");
                    jsonBuilder.append("\"idbarang\":\"").append(idBarang).append("\",");
                    jsonBuilder.append("\"namabarang\":\"").append(namaBarang).append("\",");
                    jsonBuilder.append("\"qtybarang\":\"").append(qty).append("\",");
                    jsonBuilder.append("\"hargasatuan\":").append(hargaSatuan);
                    jsonBuilder.append("}");

                    String jsonString = jsonBuilder.toString();
    
                    writer.println(jsonString);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                clearConsole.clearConsole();
                System.out.println("Berhasil Tambah Barang " + idBarang + "!\n");
                adminCon.menuAdmin(scanner);
            }
            else if (menuAdmin == 2) { // Edit Barang
                System.out.print("ID Barang: ");
                String idBarang = scanner.nextLine();
                System.out.print("Nama Barang: ");
                String namaBarang = scanner.nextLine();
                System.out.print("Qty: ");
                int qty = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Harga Satuan: ");
                int hargaSatuan = scanner.nextInt();
                scanner.nextLine();

                try {
                    Scanner in = new Scanner(new FileReader(BARANG_FILE));
                    int idx = 0;
                    while(in.hasNext()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(in.next());
                        String outString = sb.toString();
                        String[] arrBarang = jsonDecodeBarang(outString);
        
                        String dataIdBarang = arrBarang[0];
                        String dataNamaBarang = arrBarang[1];
                        int dataQtyBarang = Integer.parseInt(arrBarang[2]);
                        int dataHargaSatuan = Integer.parseInt(arrBarang[3]);

                        if(idx == 0) {
                            try (PrintWriter writer = new PrintWriter(BARANG_FILE)) {
                                writer.print("");
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
        
                        if(dataIdBarang.equals(idBarang)) { // Jika ID Barang Ketemu
                            dataNamaBarang = namaBarang;
                            dataQtyBarang = qty;
                            dataHargaSatuan = hargaSatuan;
                        }

                        try (PrintWriter writer = new PrintWriter(new FileWriter(BARANG_FILE, true))) {
                            StringBuilder jsonBuilder = new StringBuilder();
                            jsonBuilder.append("{");
                            jsonBuilder.append("\"idbarang\":\"").append(dataIdBarang).append("\",");
                            jsonBuilder.append("\"namabarang\":\"").append(dataNamaBarang).append("\",");
                            jsonBuilder.append("\"qtybarang\":\"").append(dataQtyBarang).append("\",");
                            jsonBuilder.append("\"hargasatuan\":").append(dataHargaSatuan);
                            jsonBuilder.append("}");
        
                            String jsonString = jsonBuilder.toString();
            
                            writer.println(jsonString);
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        idx++;
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                clearConsole.clearConsole();
                System.out.println("Berhasil Edit Barang " + idBarang + "!\n");
                adminCon.menuAdmin(scanner);
            }
            else if (menuAdmin == 3) { // Hapus Barang
                System.out.print("ID Barang: ");
                String idBarang = scanner.nextLine();

                try {
                    Scanner in = new Scanner(new FileReader(BARANG_FILE));
                    int idx = 0;
                    while(in.hasNext()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(in.next());
                        String outString = sb.toString();
                        String[] arrBarang = jsonDecodeBarang(outString);
        
                        String dataIdBarang = arrBarang[0];
                        String dataNamaBarang = arrBarang[1];
                        int dataQtyBarang = Integer.parseInt(arrBarang[2]);
                        int dataHargaSatuan = Integer.parseInt(arrBarang[3]);

                        if(idx == 0) {
                            try (PrintWriter writer = new PrintWriter(BARANG_FILE)) {
                                writer.print("");
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
        
                        if(!dataIdBarang.equals(idBarang)) { // Skip Jika ID Barang Ketemu (Hapus Row)
                            try (PrintWriter writer = new PrintWriter(new FileWriter(BARANG_FILE, true))) {
                                StringBuilder jsonBuilder = new StringBuilder();
                                jsonBuilder.append("{");
                                jsonBuilder.append("\"idbarang\":\"").append(dataIdBarang).append("\",");
                                jsonBuilder.append("\"namabarang\":\"").append(dataNamaBarang).append("\",");
                                jsonBuilder.append("\"qtybarang\":\"").append(dataQtyBarang).append("\",");
                                jsonBuilder.append("\"hargasatuan\":").append(dataHargaSatuan);
                                jsonBuilder.append("}");
            
                                String jsonString = jsonBuilder.toString();
                
                                writer.println(jsonString);
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        idx++;
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                clearConsole.clearConsole();
                System.out.println("Berhasil Hapus Barang " + idBarang + "!\n");
                adminCon.menuAdmin(scanner);
            }
            else if (menuAdmin == 4) { // List Barang
                try {
                    Scanner in = new Scanner(new FileReader(BARANG_FILE));
                    System.out.println("===== LIST BARANG =====");
                    while(in.hasNext()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(in.next());
                        String outString = sb.toString();
                        String[] arrBarang = jsonDecodeBarang(outString);
        
                        String dataIdBarang = arrBarang[0];
                        String dataNamaBarang = arrBarang[1];
                        int dataQtyBarang = Integer.parseInt(arrBarang[2]);
                        int dataHargaSatuan = Integer.parseInt(arrBarang[3]);

                        System.out.println("ID Barang: " + dataIdBarang);
                        System.out.println("Nama Barang: " + dataNamaBarang);
                        System.out.println("Qty: " + dataQtyBarang);
                        System.out.println("Harga Satuan: " + dataHargaSatuan);
                        System.out.print("\n");
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                adminCon.menuAdmin(scanner);
            }
            else {
                System.out.println("Menu Not Available!\n");
            }
        }
    }

    private String[] jsonDecodeBarang(String jsonString) {
        String trimmedJson = jsonString.substring(1, jsonString.length() - 1);

        String[] keyValuePairs = trimmedJson.split(",");

        String idbarang = null;
        String namabarang = null;
        String qtybarang = null;
        String hargasatuan = null;

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");

            String key = keyValue[0].trim().replaceAll("\"", "");
            String value = keyValue[1].trim();

            if (key.equals("idbarang")) {
                idbarang = value.replaceAll("\"", "");
            }
            else if (key.equals("namabarang")) {
                namabarang = value.replaceAll("\"", "");
            }
            else if (key.equals("qtybarang")) {
                qtybarang = value.replaceAll("\"", "");
            }
            else if (key.equals("hargasatuan")) {
                hargasatuan = value.replaceAll("\"", "");
            }
        }

        String[] arrReturn = {idbarang, namabarang, qtybarang, hargasatuan};
        return arrReturn;
    }
}