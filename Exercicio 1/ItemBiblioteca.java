public abstract class ItemBiblioteca {
    private final String codigo;
    private final String titulo;
    private boolean disponivel = true;

    protected ItemBiblioteca(String codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
    }

    public abstract int getPrazoDias();
    public abstract double getMultaPorDia();
    public abstract String getTipo();

    public double calcularMulta(long diasAtraso) {
        return diasAtraso > 0 ? diasAtraso * getMultaPorDia() : 0.0;
    }

    public String getCodigo()     { return codigo; }
    public String getTitulo()     { return titulo; }
    public boolean isDisponivel() { return disponivel; }

    // package-private: só a Biblioteca altera a disponibilidade
    void marcarEmprestado() { disponivel = false; }
    void marcarDevolvido()  { disponivel = true; }

    @Override
    public String toString() {
        return String.format("%-6s %-8s %-28s %s",
                codigo, getTipo(), titulo, disponivel ? "disponível" : "emprestado");
    }
}
