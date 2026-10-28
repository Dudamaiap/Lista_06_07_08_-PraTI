public class Dvd extends ItemBiblioteca {
    public Dvd(String codigo, String titulo) { super(codigo, titulo); }

    @Override public int getPrazoDias()      { return 3; }
    @Override public double getMultaPorDia() { return 2.00; }
    @Override public String getTipo()        { return "DVD"; }
}