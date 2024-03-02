# LABORATORIO 3 AREP -  MICROFRAMEWORKS WEB


Sparkweb es un microframework web mínimo y fácil de usar que le permite desarrollar aplicaciones web utilizando funciones Lambda en Java.
 En este taller, veremos la arquitectura de Sparkweb y crearemos un servidor web que admita diferentes tipos de solicitudes y respuestas.
 Es similar a Spark, pero está construido desde cero y solo tiene una API Java básica.
 Una característica clave de Sparkweb es la capacidad de registrar servicios GET y POST utilizando funciones Lambda.
 Esto proporciona una manera flexible y eficiente de manejar diferentes tipos de solicitudes y respuestas sin  crear clases o métodos adicionales.
 La implementación de Sparkweb en este taller se realiza de forma independiente a otros frameworks como Spark y Spring, con el objetivo de proporcionar una comprensión clara y profunda de  conceptos relacionados con el desarrollo de servidores web.
 Además de manejar solicitudes y respuestas, Sparkweb también brinda la capacidad de servir archivos estáticos y configurar sus directorios.
 Esta característica es especialmente útil en el desarrollo de aplicaciones web, ya que le permite acceder y servir archivos estáticos como hojas de estilo(css), imágenes y archivos JavaScript.

# Iniciando 
A continuación se indican una serie de instruciones para bajar y ejecutar el proyecto de manera exitosa:

Es **importante**❗tener instalado: 
- [MAVEN](https://maven.apache.org) : Manejo de las dependecias. 
- [GIT](https://git-scm.com) : Control de versiones.
- [JAVA](https://www.java.com/es/) : Lenguaje de programación (JDK 20). 

# Instalación ⬇️
Los siguiente comando le permitira clonar el repositorio de manera local:
~~~
git clone https://github.com/SantiagoMelo0104/AREP-LAB3.git
~~~

# Ejecución🪄 
Para este ejemplo usaremos el IDE de Intelij:

+ Una vez clonado, abrimos el proyecto en en IDE y ubicamos la siguiente clase **APP**.
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/89efdeb9-b920-4174-8a67-38b16df5315c)


+ Para ejecutar el proyecto podemos hacerlo presionando cualquiera de los recuadros a continuación
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/59eb08b6-6445-4bd3-a962-1c7db460eec4)


+ A continuación dirijase al navegador de su preferencia y vaya a la siguiente dirección  ```http://localhost:35000/action/la_carpeta _del_tipo/el_archivo_que_se_desee  ```
  + *Por ejemplo*
  
``` 
http://localhost:35000/action/html/movie.html
http://localhost:35000/action/images/robot.jpg
```

![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/d4ac7f93-8202-4961-888b-4875a3acbf00)
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/14f46836-a72c-4e3d-bfbf-2e6f83a7ab08)

+ Luego si se desea probar los métodos GET y POST, igrese la siguinte dirección en su navegador, en este caso usaremos la query que queramos para este ejemplo usaremos ?nombre=AREP
  + EJEMPLO GET:
``` 
http://localhost:35000/action/hola?nombre=AREP
```
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/62766765-dc7b-43d3-93bd-52f9e036c7e3)
  + EJEMPLO POST:
``` 
http://localhost:35000/action/adios?nombre=tito
```
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/9366357f-125e-4085-90bd-07c350808a5c)

# Ejecución de Pruebas 🧪
### Desde POSTMAN:
-para POST
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/5991d4bd-5763-4465-b2e6-7af05227c0b0)

-para GET
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/694583fd-de55-4b30-a0db-5eb1d8a418b1)

-para IMAGES
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/6a4bceac-0736-45f7-afcc-cfe3fbebc9ee)

### Desde el IDE : 
- Para correr la pruebas nos dirigimos al IDE y localizamos una carpeta llamada **test**
![image](https://github.com/SantiagoMelo0104/AREP-LAB2/assets/123812833/1a50b576-a8a3-496c-be49-e96cabd45dfa)

- Del mismo modo que ejecutamos el servidor lo haremos con la clase de pruebas:
![image](https://github.com/SantiagoMelo0104/AREP-LAB2/assets/123812833/c749b6cc-4846-4b8a-91a8-95989a09aaee)

- Resultado✅
![image](https://github.com/SantiagoMelo0104/AREP-LAB2/assets/123812833/28c02a27-b4ad-47fa-8f79-ff46559521de)


### Por consola seria de la siguiente forma:
- Cambiar directorio
  ```
  cd AREP_LAB02
  ```

- Ejecutar 
  ```
  mvn test
  ```
# Arquitectura 📄 
La arquitectura de las clases SNSpark, App y Function se basa en el patrón de diseño singleton, que garantiza que solo existe una instancia de SNSpark a la vez.
 SNSpark es la clase principal responsable de manejar las conexiones entrantes y delegar la lógica de procesamiento de solicitudes a  funciones registradas en la clase.
 SNSpark tiene un mapa estático llamado Servicio que almacena rutas registradas y pares de características.
 Cuando llega una solicitud entrante, SNSpark verifica si hay alguna característica registrada para esa ruta en particular.
 
 Si hay una coincidencia, se llama a la función  y se devuelve la respuesta HTTP correspondiente.Si no hay ninguna coincidencia, se devuelve una respuesta HTTP con un error 404.
 La clase SNSpark también tiene un método de inicio estático que crea un servidor en el puerto 35000 y escucha las solicitudes entrantes.
 Al recibir una solicitud, el servidor crea un hilo para procesar la solicitud y delega la lógica de procesamiento a la función registrada correspondiente.
 La clase App es el punto de entrada del programa y es responsable de registrar las funciones de manejo de solicitudes para las rutas /hola y /adios utilizando los métodos estáticos get y post de SNSpark.
 El método principal también inicia el servidor SNSpark si aún no se está ejecutando.
 
 Todas las funciones deben tener un método Handle que acepte una cadena de consulta y devuelva una cadena de respuesta.
 En resumen, la arquitectura de estas clases está diseñada para proporcionar una manera simple y flexible de crear servidores web livianos que puedan procesar solicitudes HTTP entrantes y devolver respuestas apropiadas.
 Esta implementación utiliza solo API  de Java básicas y no requiere ningún marco adicional.

# Pruebas 
#### - Al cargar un archivo html:
  ```
http://localhost:35000/action/html/movie.html
  ```
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/17a85b87-7d56-4216-b6c7-1520f51ffa13)


#### - Al cargar un imagen:
  ```
http://localhost:35000/action/images/poke.png
  ```
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/5b2f289a-88c2-4cfe-aa99-7da77ded2d04)


#### - Al cargar un archivo css:
```
http://localhost:35000/action/css/movie.css
```
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/7eb43eb1-c310-4e59-9c4a-10d0b405c5e6)



#### - Al cargar un archivo js:
```
http://localhost:35000/action/js/ApiConnection.js
```
![image](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/be2a9c43-25c4-4f47-a662-f7a92a6ad7fe)

#Probando en otro sistema operativo
+ GET:
  ![1](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/13c00027-7078-432e-80d7-d9afc165a0c8)
+ POST:
  ![2](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/cc6b5a0c-bfc8-432a-9629-93e066dc9cb0)
+ MOVIE:
  ![3](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/15fe5ad7-8e3f-44e2-beac-e5e4d8585ad4)
+ IMAGES:
  ![4](https://github.com/SantiagoMelo0104/AREP-LAB3/assets/123812833/55cbdfc9-fcd1-4a3f-b1cf-9fffd6f2d72b)



# Autor 
Santiago Naranjo Melo [SantiagoMelo0104](https://github.com/SantiagoMelo0104)
