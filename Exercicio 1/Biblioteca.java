import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Biblioteca {
    private final ItemBiblioteca[] acervo;
    private final Emprestimo[] emprestimosAtivos;
    private int totalItens = 0;

    public Biblioteca(int capacidadeAcervo, int capacidadeEmprestimos) {
        acervo = new ItemBiblioteca[capacidadeAcervo];
        emprestimosAtivos = new Emprestimo[capacidadeEmprestimos];
    }

    public boolean cadastrar(ItemBiblioteca item) {
        if (totalItens == acervo.length) {
            System.out.println("Acervo cheio: " + item.getTitulo() + " não cadastrado.");
            return false;
        }
        acervo[totalItens++] = item;
        return true;
    }

    public boolean emprestar(String codigo, Usuario usuario, LocalDate dataRetirada) {
        ItemBiblioteca item = buscar(codigo);
        if (item == null) {
            System.out.println("RECUSADO: item " + codigo + " não encontrado.");
            return false;
        }
        if (!item.isDisponivel()) {
            System.out.println("RECUSADO: " + item.getTitulo() + " já está emprestado.");
            return false;
        }
        if (!usuario.podeEmprestar()) {
            System.out.println("RECUSADO: " + usuario.getNome()
                    + " atingiu o limite de " + usuario.getLimiteItens() + " itens.");
            return false;
        }
        int posicao = posicaoLivre();
        if (posicao < 0) {
            System.out.println("RECUSADO: capacidade de empréstimos esgotada.");
            return false;
        }

        Emprestimo e = new Emprestimo(item, usuario, dataRetirada);
        emprestimosAtivos[posicao] = e;
        item.marcarEmprestado();
        usuario.registrarEmprestimo();
        System.out.println("OK: " + usuario.getNome() + " retirou " + item.getTitulo()
                + " (devolver até " + e.getDataDevolucaoPrevista() + ")");
        return true;
    }

    /** Devolve o item e retorna o valor da multa (0 se no prazo). */
    public double devolver(String codigo, LocalDate dataDevolucao) {
        for (int i = 0; i < emprestimosAtivos.length; i++) {
            Emprestimo e = emprestimosAtivos[i];
            if (e != null && e.getItem().getCodigo().equals(codigo)) {
                long atraso = ChronoUnit.DAYS.between(e.getDataDevolucaoPrevista(), dataDevolucao);
                double multa = e.getItem().calcularMulta(atraso);
                e.getItem().marcarDevolvido();
                e.getUsuario().registrarDevolucao();
                emprestimosAtivos[i] = null;
                return multa;
            }
        }
        throw new IllegalArgumentException("Não há empréstimo ativo para o item " + codigo);
    }

    public void listarAcervo() {
        for (int i = 0; i < totalItens; i++) {
            System.out.println(acervo[i]);   // polimorfismo: nenhum if de tipo
        }
    }

    private ItemBiblioteca buscar(String codigo) {
        for (int i = 0; i < totalItens; i++) {
            if (acervo[i].getCodigo().equals(codigo)) return acervo[i];
        }
        return null;
    }

    private int posicaoLivre() {
        for (int i = 0; i < emprestimosAtivos.length; i++) {
            if (emprestimosAtivos[i] == null) return i;
        }
        return -1;
    }
}