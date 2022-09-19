package edu.unimagalena.compras.carritocompras.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;
    @NotNull
    @Min(0)

    private Double precio;
    private String description;
    private String imagen;    

    public Producto actualizarCon(Producto producto){
        return new Producto(
            this.id, 
            producto.nombre, 
            producto.precio,
            producto.description,
            producto.imagen
        );
    }
}
