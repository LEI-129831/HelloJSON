package iscte.ista;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class App {
    private static final String FIRSTNAMES_F_FILE = "data/firstnames_f.json";
    private static final String FIRSTNAMES_M_FILE = "data/firstnames_m.json";
    private static final String SURNAMES_FILE = "data/surnames.json";
    private static final String STUDENTS_CSV_FILE = "data/students.csv";
    private static final int INITIAL_NUMBER = 21000;

    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Ler os ficheiros JSON para arrays de Strings
            String[] femaleNames = mapper.readValue(new File(FIRSTNAMES_F_FILE), String[].class);
            String[] maleNames = mapper.readValue(new File(FIRSTNAMES_M_FILE), String[].class);
            String[] surnames = mapper.readValue(new File(SURNAMES_FILE), String[].class);

            // Juntar os nomes masculinos e femininos num só array para facilitar
            String[] allFirstNames = new String[femaleNames.length + maleNames.length];
            System.arraycopy(femaleNames, 0, allFirstNames, 0, femaleNames.length);
            System.arraycopy(maleNames, 0, allFirstNames, femaleNames.length, maleNames.length);

            // Pedir a quantidade de alunos ao utilizador
            Scanner scanner = new Scanner(System.in);
            System.out.print("Quantos alunos pretendes gerar? ");
            int numStudents = scanner.nextInt();
            scanner.close();

            Random random = new Random();
            Set<String> generatedNames = new HashSet<>();

            // Preparar para escrever no ficheiro CSV
            FileWriter csvWriter = new FileWriter(STUDENTS_CSV_FILE);
            int currentStudentNumber = INITIAL_NUMBER;

            for (int i = 0; i < numStudents; i++) {
                String fullName;

                // Gerar um nome aleatório único
                do {
                    String firstName = allFirstNames[random.nextInt(allFirstNames.length)];
                    String surname = surnames[random.nextInt(surnames.length)];
                    fullName = firstName + " " + surname;
                } while (generatedNames.contains(fullName)); // Garante que não há nomes repetidos

                generatedNames.add(fullName);

                // Criar o objeto Student e obter a string para o CSV usando o método toCSV()
                Student student = new Student(fullName, currentStudentNumber);
                csvWriter.append(student.toCSV()).append("\n");

                currentStudentNumber++;
            }

            csvWriter.close();
            System.out.println("Ficheiro '" + STUDENTS_CSV_FILE + "' gerado com sucesso com " + numStudents + " alunos!");

        } catch (IOException e) {
            System.out.println("Erro ao ler/escrever os ficheiros. Confirma se os ficheiros JSON estão mesmo na pasta 'data/'.");
            e.printStackTrace();
        }
    }
}