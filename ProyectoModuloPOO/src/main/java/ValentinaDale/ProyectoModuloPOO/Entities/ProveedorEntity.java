package ValentinaDale.ProyectoModuloPOO.Entities;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name ="TBPROVIDER")
@Getter @Setter @ToString @EqualsAndHashCode
public class ProveedorEntity {

    //Atributos donde se guardan los datos que se ingresan a la base de datos y que devuelve la base, se relacionan directamente con ls atributos de la entidad/tabla en la base

    @Id
    @Column(name = "PROVIDERID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_provider")
    @SequenceGenerator(name = "seq_provider", sequenceName = "seq_provider", allocationSize = 1)
    private Long providerID;

    @Column(name = "PROVIDERNAME")
    private String providerName;

    @Column(name = "PROVIDERPHONE")
    private String providerPhone;

    @Column(name ="PROVIDERADDRESS")
    private String providerAddress;

    @Column(name = "PROVIDEREMAIL", unique = true)
    private String providerEmail;

    @Column(name = "PROVIDERCODE")
    private String providerCode;

    @Column(name = "PROVIDERSTATUS")
    private Long providerStatus;

    @Column(name = "PROVIDERCOMMENTS")
    private String providerComments;

}
