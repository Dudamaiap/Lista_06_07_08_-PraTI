import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca(10, 20);

        // Cadastro
        biblioteca.cadastrar(new Livro("L001", "Dom Casmurro", "Machado de Assis"));
        biblioteca.cadastrar(new Livro("L002", "O Cortiço", "Aluísio Azevedo"));
        biblioteca.cadastrar(new Revista("R001", "Superinteressante", 450));
        biblioteca.cadastrar(new Revista("R002", "National Geographic", 312));
        biblioteca.cadastrar(new Dvd("D001", "Central do Brasil"));

        Usuario ana = new Aluno("Ana Souza");
        Usuario carlos = new Professor("Carlos Lima");
        LocalDate dia = LocalDate.of(2026, 10, 1);

        // Empréstimos bem-sucedidos
        biblioteca.emprestar("L001", ana, dia);
        biblioteca.emprestar("R001", ana, dia);
        biblioteca.emprestar("D001", ana, dia);

        // Recusado: aluno já tem 3 itens
        biblioteca.emprestar("L002", ana, dia);

        // Professor consegue
        biblioteca.emprestar("L002", carlos, dia);

        // Devolução com 3 dias de atraso (prazo da revista: 08/10)
        double multa = biblioteca.devolver("R001", LocalDate.of(2026, 10, 11));
        System.out.printf("Multa na devolução de R001: R$ %.2f%n", multa);

        System.out.println("\n--- Acervo ---");
        biblioteca.listarAcervo();
    }
}