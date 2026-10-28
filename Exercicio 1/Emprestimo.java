import java.time.LocalDate;

public class Emprestimo {
    private final ItemBiblioteca item;
    private final Usuario usuario;
    private final LocalDate dataRetirada;
    private final LocalDate dataDevolucaoPrevista;

    Emprestimo(ItemBiblioteca item, Usuario usuario, LocalDate dataRetirada) {
        this.item = item;
        this.usuario = usuario;
        this.dataRetirada = dataRetirada;
        this.dataDevolucaoPrevista = dataRetirada.plusDays(item.getPrazoDias());
    }

    public ItemBiblioteca getItem()          { return item; }
    public Usuario getUsuario()              { return usuario; }
    public LocalDate getDataRetirada()       { return dataRetirada; }
    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
}