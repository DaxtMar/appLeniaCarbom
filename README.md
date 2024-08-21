# Aplicación final del curso de Desarrollo de Aplicaciones Móviles 2

## Descripción

**Leña y Carbón** es una aplicación desarrollada para **Pollería Leña y Carbón**, diseñada para gestionar pedidos de manera eficiente. La aplicación permite a los usuarios explorar los platos que ofrece la polleria, añadir productos al carrito, completar pagos a través de una pasarela y gestionar su perfil. Además, los usuarios pueden revisar su historial de pedidos y visualizar su ubicación en un mapa.

## Características Principales

- **Exploración de Productos:** Navega por una lista de productos y categorías disponibles en la pollería.
- **Carrito de Compras:** Añade productos al carrito y realiza el pago a través de una pasarela de pago integrada.
- **Perfil de Usuario:** Accede al historial de pedidos y visualiza la información de tu cuenta.
- **Visualización de Mapa:** Utiliza la funcionalidad de mapa para ubicar la pollería, basada en la dependencia **OSMDroid**.
- **Conexión API:** La aplicación está conectada a servicios API desarrollados en **Java con Spring Boot** para la gestión de datos.

## Configuración

```
 /*Dentro del proyecto buscar la carpeta dataEjemplo dentro hay un archivo dataclases colocar la ip privada y puerto por donde se está ejecutando el backend
 */
const val ipserver: String = "http://127.0.0.1:8080/"
```

## Créditos
- **Docente del Curso:** Luis Angel Salvatierra Aquino

