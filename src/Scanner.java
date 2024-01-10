public class Scanner {
    private Register register;

    public Scanner(Register register) {
        this.register = register;
    }

    public void scan(String barcode) {
        register.scanItem(barcode);
    }
}