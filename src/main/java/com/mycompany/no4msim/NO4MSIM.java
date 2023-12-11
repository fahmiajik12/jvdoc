/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.no4msim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class NO4MSIM {
    private static final String DB_URL = "jdbc:mysql://localhost/mahasiswa";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("========== Menu Utama ==========");
            System.out.println("1. Input Data");
            System.out.println("2. Tampil Data");
            System.out.println("3. Update Data");
            System.out.println("0. Keluar");
            System.out.print("Pilihan > ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    inputData();
                    break;
                case 2:
                    tampilData();
                    break;
                case 3:
                    updateData();
                    break;
                case 0:
                    System.out.println("Keluar dari menu.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (choice != 0);

        scanner.close();
    }

    static void inputData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== Input Data Mahasiswa ==========");
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan Alamat: ");
        String alamat = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO mahasiswa (nama, alamat) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nama);
                preparedStatement.setString(2, alamat);

                preparedStatement.executeUpdate();

                System.out.println("Data berhasil dimasukkan ke dalam tabel mahasiswa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void tampilData() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM mahasiswa";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                System.out.println("========== Data Mahasiswa ==========");
                System.out.printf("%-5s %-20s %-20s%n", "ID", "Nama", "Alamat");

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nama = resultSet.getString("nama");
                    String alamat = resultSet.getString("alamat");

                    System.out.printf("%-5d %-20s %-20s%n", id, nama, alamat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== Update Data Mahasiswa ==========");
        System.out.print("Masukkan ID yang akan di-update: ");
        int idToUpdate = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        System.out.print("Masukkan Nama Baru: ");
        String newNama = scanner.nextLine();

        System.out.print("Masukkan Alamat Baru: ");
        String newAlamat = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE mahasiswa SET nama = ?, alamat = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newNama);
                preparedStatement.setString(2, newAlamat);
                preparedStatement.setInt(3, idToUpdate);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Data berhasil di-update.");
                } else {
                    System.out.println("ID tidak ditemukan. Data tidak di-update.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

