package edu.unimagalena.compras.carritocompras.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.unimagalena.compras.carritocompras.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    public List<Producto> findByNombre(String nombre);
    public List<Producto> findByPrecioBetween(Double precioInicial, Double precioFinal);    
}
