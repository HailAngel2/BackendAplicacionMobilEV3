# EV3AplicacionesMoviles
Desarrollo de Aplicaciones moviles
Integrantes: Angel Pimiento

Funcionalidades

Autenticación: Inicio de sesión y registro de usuarios nuevos contra base de datos.

Catálogo: Listado de productos con búsqueda inteligente (Texto + Filtro por Categoría).

Carrito de Compras: Gestión de productos, cálculo de total y conversión de divisa (API Externa).

Panel de Administración: CRUD completo de productos (Crear, Editar, Eliminar) exclusivo para rol Admin.

Persistencia: Conexión a base de datos PostgreSQL mediante API REST.

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

Requisitos
•	Dispositivo Android con versión 7.0 (Nougat) o superior.
•	Conexión a Internet activa (para cargar productos e imágenes).

<img width="237" height="356" alt="Captura de pantalla 2025-12-03 092414" src="https://github.com/user-attachments/assets/e9d915a9-6300-4331-b51b-45cc34db247b" />

Pasos
1.	Transferir el archivo app-debug.apk o app-release.apk al dispositivo móvil.
2.	Habilitar la instalación desde "Orígenes desconocidos" en la configuración de seguridad del teléfono.
3.	Ejecutar el archivo APK e instalar.
4.	Abrir la aplicación "EV3".

