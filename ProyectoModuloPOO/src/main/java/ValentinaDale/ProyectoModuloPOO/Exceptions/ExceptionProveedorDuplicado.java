package ValentinaDale.ProyectoModuloPOO.Exceptions;

public class ExceptionProveedorDuplicado extends RuntimeException {

    private String campoDupliacdo;

    public ExceptionProveedorDuplicado(String message) {
        super(message);
    }

    public String getCampoDupliacdo() {
        return campoDupliacdo;
    }
}
