import java.io.*;
import java.util.*;
public class LibraryManagementSystem_Fine {
 public static void main(String[] args) throws IOException {
 Scanner sc = new Scanner(System.in);
 String booksFile = "books.txt";
 String issuedFile = "issued.txt";
 File bf = new File(booksFile);
 File ifile = new File(issuedFile);
 if (!bf.exists()) bf.createNewFile();
 if (!ifile.exists()) ifile.createNewFile();
 while (true) {
 System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
 System.out.println("1. Admin Login");
 System.out.println("2. Student Login");
 System.out.println("3. Exit");
 System.out.print("Enter choice: ");
 int choice = sc.nextInt();
 sc.nextLine();
 if (choice == 1) {
 System.out.print("Enter admin password: ");
 if (sc.nextLine().equals("admin"))
 adminMenu(sc, booksFile, issuedFile);
 else
 System.out.println("Wrong password!");
 } else if (choice == 2) {
 studentMenu(sc, booksFile, issuedFile);
 } else if (choice == 3) {
 System.out.println("Exiting...");
 break;
 } else {
 System.out.println("Invalid choice.");
 }
 }
 sc.close();
 }
 static void adminMenu(Scanner sc, String booksFile, String issuedFile) throws
IOException {
 while (true) {
 System.out.println("\n--- Admin Menu ---");
 System.out.println("1. Add Book");
 System.out.println("2. Remove Book");
 System.out.println("3. View Issued Books");
 System.out.println("4. Logout");
 System.out.print("Enter choice: ");
 int ch = sc.nextInt();
 sc.nextLine();
 if (ch == 1) addBook(sc, booksFile);
 else if (ch == 2) removeBook(sc, booksFile);
 else if (ch == 3) viewIssuedBooks(issuedFile);
 else if (ch == 4) break;
 else System.out.println("Invalid choice.");
 }
 }
 static void studentMenu(Scanner sc, String booksFile, String issuedFile)
throws IOException {
 while (true) {
 System.out.println("\n--- Student Menu ---");
 System.out.println("1. Search Book");
 System.out.println("2. Issue Book");
 System.out.println("3. Return Book");
 System.out.println("4. Logout");
 System.out.print("Enter choice: ");
 int ch = sc.nextInt();
 sc.nextLine();
 if (ch == 1) searchBook(sc, booksFile);
 else if (ch == 2) issueBook(sc, booksFile, issuedFile);
 else if (ch == 3) returnBook(sc, booksFile, issuedFile);
 else if (ch == 4) break;
 else System.out.println("Invalid choice.");
 }
 }
 static void addBook(Scanner sc, String file) throws IOException {
 System.out.print("Enter book title: ");
 String title = sc.nextLine();
 System.out.print("Enter author: ");
 String author = sc.nextLine();
 BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
 bw.write(title + "," + author + ",available\n");
 bw.close();
 System.out.println("Book added.");
 }
 static void removeBook(Scanner sc, String file) throws IOException {
 System.out.print("Enter title to remove: ");
 String title = sc.nextLine();
 BufferedReader br = new BufferedReader(new FileReader(file));
 String[] lines = new String[500];
 int count = 0;
 String line;
 boolean removed = false;
 while ((line = br.readLine()) != null) {
 String[] p = line.split(",");
 if (p[0].equalsIgnoreCase(title)) removed = true;
 else lines[count++] = line;
 }
 br.close();
 BufferedWriter bw = new BufferedWriter(new FileWriter(file));
 for (int i = 0; i < count; i++) bw.write(lines[i] + "\n");
 bw.close();
 System.out.println(removed ? "Book removed." : "Book not found.");
 }
 static void viewIssuedBooks(String file) throws IOException {
 BufferedReader br = new BufferedReader(new FileReader(file));
 String line;
 System.out.println("\n--- Issued Books ---");
 while ((line = br.readLine()) != null)
 System.out.println(line);
 br.close();
 }
 static void searchBook(Scanner sc, String file) throws IOException {
 System.out.print("Enter title or author keyword: ");
 String key = sc.nextLine().toLowerCase();
 BufferedReader br = new BufferedReader(new FileReader(file));
 String line;
 boolean found = false;
 while ((line = br.readLine()) != null) {
 if (line.toLowerCase().contains(key)) {
 System.out.println(line);
 found = true;
 }
 }
 br.close();
 if (!found) System.out.println("No match found.");
 }
 static void issueBook(Scanner sc, String booksFile, String issuedFile) throws
IOException {
 System.out.print("Enter book title: ");
 String title = sc.nextLine();
 System.out.print("Enter your name: ");
 String student = sc.nextLine();
 BufferedReader br = new BufferedReader(new FileReader(booksFile));
 String[] lines = new String[500];
 int count = 0;
 String line;
 boolean issued = false;
 while ((line = br.readLine()) != null) {
 String[] p = line.split(",");
 if (p[0].equalsIgnoreCase(title) && p[2].equals("available")) {
 p[2] = "issued";
 issued = true;
 BufferedWriter ibw = new BufferedWriter(new FileWriter(issuedFile,
true));
 ibw.write(title + "," + student + "," + System.currentTimeMillis()
+ "\n");
 ibw.close();
 }
 lines[count++] = String.join(",", p);
 }
 br.close();
 BufferedWriter bw = new BufferedWriter(new FileWriter(booksFile));
 for (int i = 0; i < count; i++) bw.write(lines[i] + "\n");
 bw.close();
 System.out.println(issued ? "Book issued." : "Book unavailable.");
 }
 static void returnBook(Scanner sc, String booksFile, String issuedFile) throws
IOException {
 System.out.print("Enter book title: ");
 String title = sc.nextLine();
 System.out.print("Enter your name: ");
 String student = sc.nextLine();
 BufferedReader br = new BufferedReader(new FileReader(booksFile));
 String[] lines = new String[500];
 int count = 0;
 String line;
 while ((line = br.readLine()) != null) {
 String[] p = line.split(",");
 if (p[0].equalsIgnoreCase(title)) p[2] = "available";
 lines[count++] = String.join(",", p);
 }
 br.close();
 BufferedWriter bw = new BufferedWriter(new FileWriter(booksFile));
 for (int i = 0; i < count; i++) bw.write(lines[i] + "\n");
 bw.close();
 br = new BufferedReader(new FileReader(issuedFile));
 String[] issued = new String[500];
 int icount = 0;
 boolean found = false;
 while ((line = br.readLine()) != null) {
 String[] p = line.split(",");
 if (p[0].equalsIgnoreCase(title) && p[1].equalsIgnoreCase(student)) {
 found = true;
 long issueTime = Long.parseLong(p[2]);
 long days = (System.currentTimeMillis() - issueTime) / (1000 * 60
* 60 * 24);
 long fine = (days > 7) ? (days - 7) * 2 : 0;
 System.out.println("Book returned. Fine: ₹" + fine);
 } else {
 issued[icount++] = line;
 }
 }
 br.close();
 bw = new BufferedWriter(new FileWriter(issuedFile));
 for (int i = 0; i < icount; i++) bw.write(issued[i] + "\n");
 bw.close();
 if (!found) System.out.println("No such record found.");
 }
}
