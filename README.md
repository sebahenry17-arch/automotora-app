📚 Bibliotecas principales
Spring Boot – Framework principal.

Spring Web – Controladores REST.

Spring Data JPA – Persistencia en MySQL.

Lombok – Generación automática de getters/setters.

Swagger/OpenAPI – Documentación interactiva.

Spring Cloud Gateway – Enrutamiento y filtros.

🛠️ Herramientas necesarias
Java JDK 21

Maven

MySQL

Git

VS Code

🏗️ Microservicios y puertos
Puerto	Servicio	Ruta base
9001	Vehículo	/api/v1/vehiculo
9002	Cliente	/api/v1/clientes
9003	FichaVehiculo	/api/v1/fichas
9004	Ventas	/api/v1/ventas
9005	Financiamiento	/api/v1/financiamientos
9006	Empleado	/api/v1/empleados
9007	Mantenimiento	/api/v1/mantenimientos
9008	Inventario	/api/v1/inventarios
9009	Proveedor	/api/v1/proveedores
9090	Gateway	/api/v1/** (redirecciona a todos los servicios)



🌐 Ejemplos de rutas REST
GET /api/v1/clientes → Lista todos los clientes.

POST /api/v1/vehiculo → Crea un vehículo.

PUT /api/v1/proveedores/{id} → Actualiza un proveedor.

DELETE /api/v1/mantenimientos/{id} → Elimina un mantenimiento.

📖 Swagger/OpenAPI
Cada servicio tiene su propia documentación:

http://localhost:{puerto}/swagger-ui.html

http://localhost:{puerto}/api/v1/v3/api-docs

Ejemplo:

Proveedor → http://localhost:9009/swagger-ui.html

Mantenimiento → http://localhost:9007/swagger-ui.html

⚠️ Manejo de errores
Todos los servicios implementan un GlobalExceptionHandler que devuelve un ErrorResponse estándar:

json
{
  "codigo": 404,
  "mensaje": "Recurso no encontrado",
  "path": "/api/v1/proveedores/99",
  "timestamp": "2026-06-21T23:30:00"
}

Desarrollo Full Stack 004-d
Integrantes:
Sebastian Alexander Henry Smith
Elam Aaron Espinoza Muñoz.
