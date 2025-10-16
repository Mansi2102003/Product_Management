package com.demo.product.main;

import java.util.Scanner;

public class ProductMasterApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\n===== PRODUCT MASTER MENU =====");
			System.out.println("1. Import Products from Excel");
			System.out.println("2. Export Products to Excel");
			System.out.println("3. Import & Export Products");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int ch = sc.nextInt();

			switch (ch) {
			case 1:
				ImportProductApp.main(null);
				break;
			case 2:
				ExportProductApp.main(null);
				break;

			case 3: {
				ImportProductApp.main(null);
				ExportProductApp.main(null);
				break;
			}
			case 4: {
				System.out.println(" Exiting...");
				System.exit(0);
			}
			default:
				System.out.println(" Invalid choice!");
			}
		}
	}
}
