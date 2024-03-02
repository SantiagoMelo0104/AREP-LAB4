# LABORATORIO 4 AREP -  Arquitecturas de Servidores de Aplicaciones, Meta protocolos de objetos, Patrón IoC, Reflexión


En el contexto de este laboratorio, se continua con la implementación de un servidor web Java empleando la plataforma Spring Boot. Este servidor estará habilitado para dispensar tanto páginas HTML como imágenes PNG, junto con la facilitación de un marco IoC (Inversión de Control) para la construcción de aplicaciones web basadas en POJOS (Objetos Java Antiguos Simples).

El propósito esencial de esta empresa es concebir un prototipo básico que ilustre las destrezas reflexivas de Java , así como la derivación de una aplicación web a partir de este último. En adición, el servidor deberá gestionar múltiples solicitudes, si bien no concurrentes.Se utilizará la anotación @RequestMapping o @Component para señalar un clase como componente permitiendo la agrpacion de estas anotaciones para su respectivo uso y despliegue en el servidor.

# Iniciando 
A continuación se indican una serie de instruciones para bajar y ejecutar el proyecto de manera exitosa:

Es **importante**❗tener instalado: 
- [MAVEN](https://maven.apache.org) : Manejo de las dependecias. 
- [GIT](https://git-scm.com) : Control de versiones.
- [JAVA](https://www.java.com/es/) : Lenguaje de programación (JDK 20). 

# Instalación ⬇️
Los siguiente comando le permitira clonar el repositorio de manera local:
~~~
https://github.com/SantiagoMelo0104/AREP-LAB4
~~~

# Ejecución🪄 
Para este ejemplo usaremos el IDE de Intelij:

+ Una vez clonado, abrimos el proyecto en en IDE y ubicamos la siguiente clase **APP**.
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/66142a92-a6dd-4af5-812c-0b70b967d4a5)

+ Para ejecutar el proyecto podemos hacerlo presionando cualquiera de los recuadros a continuación
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/b56278b3-2163-4053-9a30-27a4817b23ae)

En este caso lo que primero queremos verificar es que ahora las anotaciones esten funcionando.
+ A continuación dirijase al navegador de su preferencia y vaya a la siguiente dirección  ```http://localhost:35000/components/[selecione entre hola, arep, suma]  ```
  + *Por ejemplo*
  
``` 
http://localhost:35000/components/hola
http://localhost:35000/components/arep?var=pepe
http://localhost:35000/components/suma?var=10
```

![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/f04185d7-caab-453f-9883-698cf310733b)

![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/338390e5-5747-44dc-964b-6f514582e227)

A continuacion de manera breve se evindecia que la implmentación de laboratorios anteriores siguen en correcto funcionamiento 

+ Primeramente se evidencia que aun sigue en funcionamiento nuestra union con la funciones labmdas con el segundo laboratorio

``` 
[http://localhost:35000/action/hola?nombre=AREP](http://localhost:35000/action/images/robot.jpg)
```
  ![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/a6e6ba0f-976d-45c1-9468-c650a4f99105)

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

/suma

![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/4592a168-fc68-4e44-bcf5-2ba42eb84b17)

para la siguiente prueba se cambia el valor directamente en postman var=1 ahora es var=24

![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/46d7eb19-4114-42fa-b8f2-1d26050de486)

/hola

![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/69a096ac-1a94-422b-87c2-6820f131c064)


### Desde el IDE : 
- Para correr la pruebas nos dirigimos al IDE y localizamos una carpeta llamada **test** y del mismo modo que ejecutamos el servidor lo haremos con la clase de pruebas
  
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/afbb7f99-aac2-4baf-a1b0-0d4d3785e007)



- Resultado✅
![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/a7e2a323-890b-4174-bc6c-6709ddd95fef)



### Por consola seria de la siguiente forma:
- Cambiar directorio
  ```
  cd AREP_LAB4
  ```

- Ejecutar 
  ```
  mvn test
  ```
# Arquitectura 📄 
El servidor utiliza las anotaciones @Component y  @GetMapping para gestionar y enlazar metodos espceifico hacia solicitudes GET para rutas específicas. El método classLoader,  recorre desde la raiz del proyecto agrgando todo los archivos a una path, luego busca las que están anotadas con @Component. Para cada clase, busca métodos anotados con @GetMapping y los almacena en un mapa llamado componentes. La clave es la ruta especificada en la anotación, y el valor es el método correspondiente(lo que va a realizar el metodo tras banbalinas).

![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/558d8fbf-f251-42bc-aea2-17841c80bfe5)

Aqui se evidencia el resultado tras haber cargado y clasificado aquellos archivos que cuentas con @Component y @RequestMapping

![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/63933c04-acda-420d-9df5-9e175a835227)

El controlador en este caso es aquella clse con la que estamos experimentado a ponerle las anotaciones para comprobar el funcionamineto de esta nueva funcionalidad del servidor:

![imagen](https://github.com/SantiagoMelo0104/AREP-LAB4/assets/123812833/84230d03-9bb6-49c7-a892-194d82ca62d9)



# Autor 
Santiago Naranjo Melo [SantiagoMelo0104](https://github.com/SantiagoMelo0104)
