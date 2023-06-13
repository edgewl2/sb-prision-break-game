# Prison Break Game - Challenge

_Proyecto de reto de desarrollo de API con el juego de fuga de prisión._

## Demo

Imagina que un personaje desea salir de un sitio donde se encuentra como una casa desconocida, un laberinto o una prisión. Si tiene un mapa pero no sabe ¿Cuál es la ruta correcta que le llevará a la salida?... ten por seguro que a través de este juego sabrás si puede escapar del lugar o no.

Ven y prueba a través de la **[Demo](http://18.221.160.65/prison-break-game-doc/webjars/swagger-ui/index.html)**.

![Demo](https://user-images.githubusercontent.com/20669949/245322753-3ac0ddff-2208-4e99-9606-154832465033.gif)

### Características y pruebas

La aplicación tiene las siguientes funciones:
 - Revisa el mapa y nos dice **si** o **no** es posible escapar.
> Desde un cliente de desarrollo y pruebas de API o haciendo uso del [Swagger](https://swagger.io/docs/) integrado al proyecto, envía una petición **POST** usando la siguiente URL:
> ```bash
> http://localhost:8081/prisoner
> ```
> Con un objeto JSON con el siguiente formato:
>```bash
>  {
>    "prison":["||||||S||","|P ||   |","||  | | |","|v| | < |","| |   | |","|   |   |","|||||||||"]
>  }
>```
> Como respuesta tendrá un estado: HTTP 200 si puede escapar, y un HTTP 403 en caso contrario.
> 
> **NOTA:** Las solicitudes se validan para evitar procesar formatos incorrectos.

 - Devuelve el estado de escapes (Completados, fallidos y la tasa escapes realizados).
> Envía una petición **GET** usando la siguiente URL:
> ```bash
> http://localhost:8081/stats
> ```
> Recibirá como respuesta un HTTP 200 junto a un objeto JSON con el siguiente formato:
>```bash
>  {
>    "ratio": 0.8,
>    "count_successful_escape": 11,
>    "count_unsuccessful_escape": 2
>  }
>```

### Requerimientos del proyecto

Para la ejecución del proyecto se requiere como mínimo:
- [Git](https://git-scm.com/downloads) - Git es un sistema de control de versiones distribuido de código abierto y gratuito.
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Java es un lenguaje de programación multiplataforma robusto.
- [PostgreSQL](https://www.postgresql.org/download/) - Simplemente Postgres para darle un nombre más pintoresco, es un sistema de código abierto de administración de bases de datos.
- [Docker](https://www.docker.com/get-started/) - Docker es una plataforma de software que le permite crear, probar e implementar aplicaciones rápidamente.
- [Visual Studio Code](https://code.visualstudio.com/download) - Visual Studio Code es un editor de código fuente multiplataforma desarrollado por Microsoft. (Puede usarse también cualquier IDE tales como IntelliJ IDEA, Eclipse, etc...).

### Instrucciones de instalación local

Para realizar este proceso lo primero que debemos hacer a través de la terminal del sistema o una herramienta como [GitHub Desktop](https://desktop.github.com/), es descargar el proyecto para luego realizar cualquiera de las opciones de instalación. Por ello se hace lo siguiente:

> - Clone el repositorio.
>
> ```bash
> git clone -b dev https://github.com/edgewl2/sb-prison-break-game.git
> ```
>
> - Prepare una base de datos para la aplicación.
> ```bash
> prison_break_game
> ```
> **NOTA:** Si cambia el nombre deberá modificar las propiedades del proyecto en el archivo **application.yml**.
>
> - Acceder al directorio raíz del proyecto desde la terminal.
> ```bash
> cd sb-prison-break-game/
> ```

#### 1. Manual

Habiendo instalado los elementos requeridos ya mencionados, se procede desde la terminal con los siguiente comandos:

> - Construir el archivo .jar a ejecutar.
> ```bash
> ./gradlew build
> ```
> 
> - Ejecutar el archivo usando el comando.
> ```bash
> java -DHOST=<localhost> -DDB_USER=<usuario> -DDB_PASSWORD=<clave> -jar ./build/libs/prison-break-game-0.0.1-SNAPSHOT.jar
> ```

#### 2. Automático

Para esto requiere instalar Docker Compose.

> - Ejecutar el comando desde la terminal.
> ```bash
> docker-compose up -d
> ```

#### 3. Cliente REST API

Ahora podemos probar la aplicación localmente usando cualquier cliente, sino puede usar Swagger. 
> - Escribiendo en su navegador el siguiente ruta:
>```bash
> /prison-break-game-doc/swagger-ui.html
> ```
