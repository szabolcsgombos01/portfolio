/**
 *
 * @author  Szabolcs Gombos
 * @since   2021-10-05
 */
package sikidom;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        Database database = new Database();
        try {
            database.read("data.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
            System.exit(-1);
        } catch (InvalidInputException ex) {
            System.out.println("Invalid input!");
            System.exit(-1);
        } catch (UresFile ex)
        {
            System.out.println("Üres a File!");
            System.exit(-1);
        }
        database.report();
        database.szamolo();
    }
}

