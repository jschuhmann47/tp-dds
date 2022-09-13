## Justificaciones entrega 4

- Se decide hacer una Single Table para los medios de transporte ya que hay pocos atributos en juego, y algunos medios
directamente no tienen atributos, por lo que preferimos tener algunas columnas en null pero ganar en velocidad de acceso
a la tabla. Para esto se cambio la interfaz MedioDeTransporte por una clase abstracta.
- A nivel general se persisten las clases que tienen datos y son relevantes al dominio 
(Organizacion, Trabajador, los medios de transporte) y no se persisten las clases que calculan o solo tienen logica 
(como los calculadores o los adapters)

Para los impedance matches
- Identidad: usamos claves subrogadas y autogeneradas
- Tipos de datos: para los enumerados tuvimos que guardarlos con su valor de tipo String. Para los valores decimales 
se tuvo que adaptar en base a la cantidad de decimales. Para las fechas usamos un converter.
Para los medios de notificacion, nos asistimos con un Factory para poder instanciar los objetos cuando se traen de la base 
de datos.
- Cardinalidad: Para las ManyToMany se tuvo que hacer una tabla intermedia, ya que la base de datos no soporta 
la bidireccionalidad en este tipo de relaciones. Para las otras se puso la PK de una tabla como FK en la otra tabla,
todo a traves del ORM.
- Herencia: Lo mencionado con respecto a los medios de transporte. Las interfaces no se persisten. 


