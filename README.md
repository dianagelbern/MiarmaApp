# Proyecto MIARMAP (Red social para sevillanos)

## Acceso a datos / programacón de servicios y procesos 

### Introducción:

Implementación de un API basada en una red social especializada en vídeos e imágenes 

En este proyecto se pretende que el usuario sea capaz de registrarse, logearse y a su vez gestionar las publicaciones que haga.

Este también tendrá la posibilidad de interaccionar con otros usuarios teniendo la capacidad de
seguirlos, aceptar solicitudes de amistad, etc.

### Modo de empleo:

#### Contenido:
- Colección de postamn
- Files requeridos:
  (Se encuentran en la ruta src/main/resources)
  - images
  - json

El usuario contará con un postman donde podrá interaccionar con el API por medio de las siguientes instrucciones

- Abrir la colección de Postman proporcionada dentro del proyecto
- En ella se encontrará con 3 folders 

  - Registro / Login 
  - Publicaciones
  - Usuarios

#### Registro / Login:

- Post registrar usuario 

Ingresar en from-data en la columna Key los valores "file" y "user" poniendo los files image_1.png y usuario2.json respectivamente (Estos se poporcionan en los files dentro de resources).
Esto dará un status 201 y devolverá al usuario creado.

- Post logear usuario

En este se deberá poner en el body el cuerpo del usuario que se pretende loguear, en este ejemplo será:
{
"email": "carlos@gmail.com",
"password": "mipassword"
}

- Get datos del usuario registrado

Sirve para saber el usuario que se encuentra logueado, deberá traer los datos completos y solo ocurre después del paso anterior


#### Publicación:

- Post crear publicación

Ingresar en from-data en la columna Key los valores "file" y "publicacion" poniendo los files video_1.mp4 y publicacion1.json respectivamente (Estos se poporcionan en los files dentro de resources).
Esto dará un status 201 y devolverá la publicación creada. Al solo estar permitidos los formatos "jpg, png y mp4" si se intenta meter otro tipo de formato producirá una exceción (se puede probar con imagen_no_valida.svg)

- Put modificar publicación

Ingresar en from-data en la columna Key los valores "file" y "publicacion" poniendo los files video_1.mp4 y publicacion1_modificada.json respectivamente (Estos se poporcionan en los files dentro de resources).
Esto dará un status 201 y devolverá la publicación creada.


- Delete eliminar publicación

En esta solo se deberá ingresar el id de la publicación que se desea eliminar y la eliminará con todo y file adjunto


- Get traer por id

En esta solo se deberá ingresar el id de la publicación que se desea ver


#### Usuarios:

- Post seguir a otro usuario

En esta se deberá poner en la ruta el nick de la persona a seguir, existen algunos usuarios registrados, se puede elegir entre seguir a "Manolo", "Diana", "Hector".
Devolverá un id conformado con los dos id de las personas relacionadas y el seguimiento en false ya que se eserará que el otro usuario la acepte


Con esto concluye las peticiones simponibles hasta el momento, espera su posible expansión en unos diás.

#### Autora: 
Diana Atziry González Suárez
