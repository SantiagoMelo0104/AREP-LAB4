package org.arep;

import org.arep.annotation.Component;
import org.arep.annotation.GetMapping;

@Component
public class ComponentController {

    @GetMapping("/arep")
    public static String arep1(String a){
        return "hola " + a;
    }
    @GetMapping("/hola")
    public static String hello(){
        return "hola :3";
    }

    @GetMapping("/suma")
    public static String sum(String a){
        int num = Integer.parseInt(a);
        return "Siempre te voy a sumar  1 + " + num + " = " + String.valueOf(num + 1);
    }

}
