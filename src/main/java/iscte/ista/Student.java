package iscte.ista;

public class Student {
    private String name;
    private int number;

    // Construtor vazio (obrigatório para a biblioteca Jackson conseguir recriar o objeto a partir do JSON)
    public Student() {
    }

    public Student(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', number=" + number + "}";
    }

    // Método exigido no guião para gerar a linha do CSV
    public String toCSV() {
        return number + "," + name;
    }
}