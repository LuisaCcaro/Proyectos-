package com.Mascoshop.Controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Control {

    @GetMapping(value = {"/", "/home"})
    public String home() {
        return "index"; 
    }

    @GetMapping(value = {"/producto"})
    public String prd() {
        return "productos";
    }
    @GetMapping(value = {"/logins"})
    public String lgn() {
        return "login"; 
    }
    @GetMapping(value = {"/deseos"})
    public String dso() {
        return "deseo";
    }
    @GetMapping(value = {"/carta"})
    public String crt() {
        return "cartaProductos"; 
    }
    @GetMapping(value = {"/carritos"})
    public String crrt() {
        return "carrito";
    }
    @GetMapping(value = {"/blogs"})
    public String blg() {
        return "blog";
    }
    @GetMapping(value = {"/administrador"})
    public String adm() {
        return "admin"; 
    }
    @GetMapping(value = {"/productos-Administrador"})
    public String pdAdm() {
        return "adminProductos"; 
    }
    @GetMapping(value = {"/actualizar-Administrador"})
    public String pfAdm() {
        return "adminUpdateProductos"; 
    }
}
