# Estado del porte de Bump

**No compila todavía.** Quedan 20 errores, y todos vienen del mismo sitio.

## Lo que ya está hecho

- El build apunta a paper-api 1.21.11, a nuestro `slimefun-core` y a Java 21.
- 54 ficheros con los paquetes de Slimefun remapeados, más 21 renombres de la API de Bukkit
  entre 1.16 y 1.21.
- **El autoactualizador, desarmado.** Lo llamaba por reflexión, con el nombre de la clase
  metido en una cadena (`Class.forName("net.guizhanss.guizhanlibplugin.updater.GuizhanUpdater")`),
  así que no salía en los imports y no lo veía una búsqueda por el nombre de la clase.
- **Las llamadas a `Validate.noNullElements`.** El core shadea commons-lang**3**, que quitó las
  variantes de un solo argumento: el mensaje pasó a ser obligatorio. Se añadió diciendo qué se
  estaba validando.

## Lo que bloquea

Bump depende de **GuizhanLib-api 1.7.6** y **SefiLib 0.2.6**, y las dos están compiladas contra
el Slimefun de upstream. Eso mete en el classpath `io.github.thebusybiscuit.slimefun4.api.items.ItemGroup`,
que no es el mismo tipo que el nuestro aunque se llame igual — de ahí los `incompatible types`.

No se arregla desde Bump: **hay que portar las dos librerías primero**. No es un uso suelto que
se pueda sustituir con un ayudante, como se hizo con GuizhanLib en SlimefunWarfare: son 20 de los
77 ficheros, e incluyen `AbstractAddon` y `Localization`, que son el esqueleto del plugin y su
sistema de idiomas.

Ninguna de las dos está en el workspace; habría que traerlas y portarlas como un addon más.
