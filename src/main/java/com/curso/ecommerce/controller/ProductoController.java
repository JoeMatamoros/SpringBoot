package com.curso.ecommerce.controller;

import java.util.Optional;
import org.hibernate.query.sqm.tree.domain.SqmPolymorphicRootDescriptor;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.tags.EditorAwareTag;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	private final Logger LOGGER  = LoggerFactory.getLogger(ProductoController.class);
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	/*Mapear informacion desde el boton*/
	@PostMapping("/save")
	public String save(Producto producto) {
		LOGGER.info("Este es el objeto producto{}", producto);
		Usuario u = new Usuario(1, "", "","" ,"" ,"" ,"" ,"" );
		producto.setUsuario(u);
		productoService.save(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Producto producto = new Producto();
		Optional<Producto> optionalProduct = productoService.get(id);
		
		producto = optionalProduct.get();
		
		LOGGER.info("Este es el id producto{}", producto);
		model.addAttribute("producto", producto);
		
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto) {
		productoService.update(producto);
		return "redirect:/productos";
	}

}
