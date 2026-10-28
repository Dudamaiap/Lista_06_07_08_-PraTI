public abstract class Usuario {
    private final String nome;
    private int quantidadeEmprestada;

    protected Usuario(String nome) { this.nome = nome; }

    public abstract int getLimiteItens();

    public boolean podeEmprestar() {
        return quantidadeEmprestada < getLimiteItens();
    }

    public String getNome()              { return nome; }
    public int getQuantidadeEmprestada() { return quantidadeEmprestada; }

    void registrarEmprestimo() { quantidadeEmprestada++; }
    void registrarDevolucao()  { quantidadeEmprestada--; }
}
