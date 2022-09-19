package edu.unimagalena.compras.carritocompras.controllers;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.unimagalena.compras.carritocompras.entities.Producto;
import edu.unimagalena.compras.carritocompras.exceptions.ResourceNotFound;
import edu.unimagalena.compras.carritocompras.services.ProductoService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("api/v1/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    //GET http://localhost:8080/api/v1/productos
    @GetMapping
    public ResponseEntity<List<Producto>> findAll(@RequestParam(name = "nombre", required = false) String nombre,
                                                    @RequestParam(name = "precioInicial", required = false) Double precioInicial,
                                                    @RequestParam(name = "precioFinal", required = false) Double precioFinal){  
        List<Producto> productos = new ArrayList<>();
        if(nombre == null && (precioInicial == null || precioFinal == null)){
            productos = productoService.findAll();
        }else if (precioInicial != null &&  precioFinal != null){
            productos = productoService.findInPriceRange(precioInicial, precioFinal);
        } else {
            productos = productoService.findByNombre(nombre);
        }
        return ResponseEntity.ok().body(productos);
    }
    //http://localhost:8080/api/v1/productos/25
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable("id") Long id){
        Producto producto = productoService.findByid(id).orElseThrow(() -> new ResourceNotFound("No se encontr[o producto"));
        return ResponseEntity.ok().body(producto);
    }
    //GET http://localhost:8080/api/v1/productos?nombre=teclado
    /*@GetMapping
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam("nombre") String nombre) {
        List<Producto> productos = productoService.findByNombre(nombre);
        return ResponseEntity.ok().body(productos);
    }
    //GET http://localhost:8080/api/v1/productos?precioInicial=2.4&precioFinal=8.0
    @GetMapping
    public ResponseEntity<List<Producto>> buscarEntreDosPrecios(@RequestParam("precioInicial") Double precioInicial,
                                                                @RequestParam("precioFinal") Double precioFinal){
        List<Producto> productos = productoService.findInPriceRange(precioInicial, precioFinal);
        return ResponseEntity.ok().body(productos);
    }*/
    //POST http://localhost:8080/api/v1/productos
    @PostMapping
    public ResponseEntity<Producto> create(@Valid @RequestBody Producto producto){
        Producto productoCreado = productoService.create(producto);
        //http://localhost:8080/api/v1/productos/2203
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(productoCreado.getId())
            .toUri();
        return ResponseEntity.created(location).body(productoCreado);
    }
    //PUT http://localhost:8080/api/v1/productos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable("id") Long id, @RequestBody Producto updatedProducto){
        Optional<Producto> updated = productoService.update(id, updatedProducto);
        return updated.map(producto -> ResponseEntity.ok().body(producto))
                    .orElseGet(()->{
                        Producto nuevoProducto = productoService.create(updatedProducto);
                        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(nuevoProducto.getId())
                        .toUri();
                        return ResponseEntity.created(location).body(nuevoProducto);
                    });
    }
    //DELETE http://localhost:8080/api/v1/productos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> delete(@PathVariable("id") Long id){
        
        try {
            productoService.delete(id);
        } catch (Exception e) {
            log.error("error al borrar", e);
           throw new ResourceNotFound("El producto a eliminar no se encuentra registrado");
        }
        return ResponseEntity.noContent().build();
    }


    
    


}
