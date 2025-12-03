# EV3FullStack2
3era prueba de FullStack2
Integrantes: Angel Pimiento

Usuarios (UsuarioController)
GET /api/usuarios → Obtener todos los usuarios.

GET /api/usuarios/{id} → Obtener un usuario por ID.

POST /api/usuarios → Registrar un nuevo usuario (Cliente).

PUT /api/usuarios/{id} → Actualizar datos de un usuario completo.

DELETE /api/usuarios/{id} → Eliminar un usuario.

PATCH /api/usuarios/{id}/password → Actualizar solo la contraseña (encriptada).


Productos (ProductoController)
GET /api/productos → Obtener todos los productos (Lista completa).

GET /api/productos/{id} → Obtener detalle de un producto.

GET /api/productos/buscar?nombre={texto} → Buscar productos por nombre.

GET /api/productos/categoria/{nombreCategoria} → Filtrar productos por categoría.

POST /api/productos → Crear un nuevo producto (Solo Admin).

PUT /api/productos/{id} → Actualizar un producto existente (Solo Admin).

PATCH /api/productos/{id} → Actualización parcial de un producto.

DELETE /api/productos/{id} → Eliminar un producto (Solo Admin).


Ventas (VentaController)
POST /api/ventas → Registrar una compra (Recibe ID usuario y lista de productos).

GET /api/ventas → Obtener historial de ventas (Paginado).

GET /api/ventas/{id} → Obtener detalle de una venta específica.

PATCH /api/ventas/{id}/estado → Actualizar el estado de una venta (ej. PENDIENTE a PAGADO).

PATCH /api/ventas/{id} → Actualizar metadatos de la venta.

DELETE /api/ventas/{id} → Cancelar/Eliminar una venta.


Categorías (CategoriaController)
GET /api/categorias → Obtener todas las categorías (Para llenar el Spinner).

GET /api/categorias/{id} → Obtener una categoría por ID.

POST /api/categorias → Crear nueva categoría.

PUT /api/categorias/{id} → Editar categoría.

DELETE /api/categorias/{id} → Eliminar categoría.

<img width="1058" height="568" alt="image" src="https://github.com/user-attachments/assets/22616478-37ba-4d18-a878-659adba7799f" />
