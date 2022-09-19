package edu.unimagalena.compras.carritocompras.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unimagalena.compras.carritocompras.entities.Producto;
import edu.unimagalena.compras.carritocompras.exceptions.ResourceNotFound;
import edu.unimagalena.compras.carritocompras.repositories.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    
    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    public List<Producto> findAll(){
        return productoRepository.findAll();
    }
    public Optional<Producto> findByid(Long id){
        return productoRepository.findById(id);
    }
    public Producto create(Producto producto){
        Producto copiaProducto = Producto.builder()
                                    .nombre(producto.getNombre())
                                    .description(producto.getDescription())
                                    .precio(producto.getPrecio())
                                    .imagen(producto.getImagen())
                                    .build();
        return productoRepository.save(copiaProducto);
    }
    public Optional<Producto> update(Long id, Producto nuevoProducto){
        return productoRepository.findById(id).map(producto -> {
            Producto productoActualizado = producto.actualizarCon(nuevoProducto);
            return productoRepository.save(productoActualizado);
        });
    }
    public void delete(Long id){
        
        log.info("Deleting with id: "+id);
        productoRepository.deleteById(id);
        
        
    }
    public List<Producto> findByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }
    public List<Producto> findInPriceRange(Double precioInicial, Double precioFinal){
        return productoRepository.findByPrecioBetween(precioInicial, precioFinal);
    }
}
