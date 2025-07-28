package ValentinaDale.ProyectoModuloPOO.Models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class ProveedorDTO {


    //Atrubutos donde se guardan los datos que ingres ael usuario o que se le muestaran al usuario con sus validaciones
    private Long providerID;

    @NotBlank
    private String providerName;

    @NotBlank
    private String providerPhone;

    @NotBlank
    private String providerAddress;

    @Email(message= "El correo debe de tener un formato valido")
    private String providerEmail;

    @NotBlank
    private String providerCode;

    @NotNull
    private Long providerStatus;

    @Size(min = 10)
    private String providerComments;

}
