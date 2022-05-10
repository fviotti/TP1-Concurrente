# Enunciado
En un sistema de adquisición de datos existen dos buffers y tres categorías de actores. Los buffers se denominan Buffer Inicial y Buffer de validados y tienen cada uno una capacidad de 100 datos (trabajan a pura pérdida, es decir si están llenos al venir un creador se descarta el dato). Los actores del sistema pueden ser Creadores de Datos, Revisores de Datos o Consumidores de Datos. El ciclo de funcionamiento normal del sistema comienza con la creación de un dato por parte de un “Creador de Datos”. Este proceso lleva un tiempo aleatorio en ms (no nulo, a elección del grupo); una vez creado es almacenado en el Buffer Inicial. En este buffer debe
permanecer hasta que el total de “Revisores” hayan revisado el mismo, la revisión del dato lleva un tiempo en ms (no nulo a elección del grupo). Una vez que todos los “Revisores” hayan revisado el dato, el último “Revisor” guardará una copia del mismo en el Buffer de Validados. Los “Revisores” no pueden revisar más de una vez cada dato. Los consumidores de datos son los encargados de eliminar los datos de ambos buffers, siempre y cuando ya hayan sido validados; la eliminación de un dato lleva un tiempo en ms (no nulo a elección del grupo).

# Desarrollo:

## Clase App(Main):
Clase principal donde inicializan los threads tanto del Writer, Reviewer y User.
Aquí también se encuentra un pequeño bloque de codigo encargado de la escritura de los registros tomados, llamado log.txt. Además, se calcula el tiempo empleado en procesar mil datos. En el momento que se crean los actores, estos se suscriben a los eventos del EventManager (observer), para enterarse de lo que sucede a nivel global con la cantidad de datos procesados.

## [enum] Constants:
Numerado que contiene la cantidad de Writer, Reviewer, User y los tiempos que demoran en realizar dichas tareas, además que los límites del buffer y la cantidad máxima de datos a procesar. Esta implementación brinda la flexibilidad de poder generar variaciones en los parametros y ver como se comporta el sistema de una manera muy práctica.


## Clase Data:
Modela la información que vamos a escribir en nuestros buffers y tiene los siguientes campos: **Int** reviews, **ReadWriteLock** lock. El lock se utiliza para evitar inconsistencias de concurrencia en el campo _reviews_, es decir, no se corrompa dicho atributo y se pueda contabilizar de manera exacta. Además posee un método **isReady()** que devuelve _true_ cuando la cantidad de revisiones de una instancia de Data es igual a la cantidad de revisores configurados en el enumerado de constantes.


## Class EventManager:
Esta clase es la encargada de manejar todos los recursos compartidos del sistema, también cuenta con un observer (patrón de diseño de comportamiento) para que nos propagar en tiempo real los cambios en la cantidad de datos procesados (valor que se utiliza para finalizar la ejecucion de los hilos una vez que se llega al límite establecido en la configuración). Los _buffers_ están representados con estructuras de datos del tipo **ArrayList** donde se almacenan los datos de las distintas de ejecucion (iniciales y procesados). A estos buffers le podemos agregar datos, modificarlos y eliminar, así incrementar la data procesada, obtener la cantidad de data procesada. etc.
Esta clase es la encargada de manejar los recursos compartidos, también cuenta con un observer (patrón de diseño de comportamiento) para que nos propagar en tiempo real los cambios en la cantidad de datos procesados (valor que se utiliza para finalizar la ejecucion de los hilos una vez que se llega al límite establecido en la configuración). Los _buffers_ están representados con estructuras de datos del tipo **ArrayList** donde se almacenan los datos de las distintas de ejecucion (iniciales y procesados). A estos buffers le podemos agregar datos, modificarlos y eliminar, así como tambié incrementar la data procesada, obtener la cantidad de data procesada. etc.
Esta clase es la encargada de manejar los recursos compartidos, también cuenta con un observer (patrón de diseño de comportamiento) para que nos propagar en tiempo real los cambios en la cantidad de datos procesados (valor que se utiliza para finalizar la ejecucion de los hilos una vez que se llega al límite establecido en la configuración). Los _buffers_ están representados con estructuras de datos del tipo **ArrayList** donde se almacenan los datos de las distintas de ejecucion (iniciales y procesados). A estos buffers le podemos agregar datos, modificarlos y eliminar, así como también incrementar la cantidad de datos procesados y obtener dicha cantidad.


## Clase Writer:
Esta clase, se encarga de crear/escribir datos en el _buffer Inicial_, la creación de datos en éste buffer está **_sincronizado_** para protegerlo de inconsistencias en la concurrencia. Utiliza como objeto clave en la sincronización, la instancia del ArrayList del Buffer Inicial del EventManager, para que solo se bloquee este buffer mientras se realiza la escritura.


## Clase Reviewer:
Esta clase, se encarga de revisar los datos del buffer inicial y moverlos al buffer de verificados una vez todos los _Reviewers_ hayan revisado dicho dato. Se utilizan la instancia de EventManager para realizar éste procesamiento. Además como en el Writer se lo hace de forma sincronizada, pero ésta vez utilizamos como objeto clave en la sincronización, la instancia del ArrayList del Buffer Inicial del EventManager, para que solo se bloquee el buffer Inicial y luego si es necesario se utiliza otro objeto de sincronizacion si se debe escribir en el _Buffer de Validados_, éste otro objeto es la instancia del ArrayList del Buffer de Validados del EventManager.

## Clase User:
Clase que se dedica a consumir datos del buffer de verificados y una vez hecho esto elimina el dato de ambos buffer. Esta clase también utiliza el mismo mecanismo que la clase Reviewer, de forma que sincroniza primero la escritura(eliminar) al buffer de validados y luego por otro lado sincroniza el acceso al buffer inicial.


## Clase Utils:   
Clase solamente utilizada para reducción de código, posee 2 métodos, **mimir()** encargado del manejo del _sleep_ para dormir el hilo y el otro **randomGen()** que se encarga de generar numero random.


## Elecciones de tiempo:
La utilización de un Logger como herramienta para el análisis de los resultados obtenidos nos permitió observar a lo largo de nuestra ejecución la realización correctamente de dicho trabajo practico, ya que podemos observar cómo a medida que se van agregando datos al buffer, hay revisores que van poniendo al mismo tiempo los mismo en el buffer de verificados y el User los va quitando de los dos buffer. Probamos imprimir cada 100ms el estado de los buffer y cuantos datos íbamos procesando para poder tener mayor cantidad de ‘fotos’ del estado del sistema y su ejecución.
La elección de los tiempos fueron para los Writers: 45ms, Reviewers: 5ms, Users: 30ms estos valores estan metidos dentro de una funcion que genera numeros randoms de hasta ese valor, lo que nos permite que el sistema sea más variable. Dicha elección fue para poder observar más correctamente en el logger como se iban agregando en los buffer los datos ya que si elegimos tiempos de escritura más chicos podemos observar como los buffer permanecen en todo tiempo completo, mientras que si modificamos tiempo más bajo para el user podemos observar casi siempre los 2 buffer vacíos ya que ni bien el revisor agrega al buffer verificado este los va eliminando de los 2 muy rápidamente

# Conclusiones:
A través de varias pruebas e implementaciones, logramos observar los efectos de la sincronización. Es importante tener en cuenta de qué manera se puede acceder a los datos con secciones críticas, ya que corromperlas es más fácil de lo que parece.
Se realizaron ensayos de uso del método sleep() propio de la clase Thread, utilizándolo dentro y fuera de la sección crítica para ver los resultados. Se observó que si éste se encuentra dentro, los tiempos de ejecución aumentan ya que otros hilos no pueden acceder a esas secciones mientra el hilo esta en un estado de dormido. Distinto de si se coloca afuera.
Se pudo observar distintos mecanismos de sincronización, aunque se optó por la utilización en su mayoría del synchronized, y en parte del lock. 

