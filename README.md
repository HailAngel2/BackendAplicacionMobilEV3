# El Maestro - App de Gestión para Ferretería

## Integrantes del Equipo
* **Angel Pimiento**
* **Asignatura:** Desarrollo de Aplicaciones Móviles (DSY1105)
* **Sección:** 002D

---

## Descripción del Proyecto
Aplicación móvil nativa para la gestión y venta de productos de ferretería. El sistema permite la interacción de diferentes tipos de usuarios (Clientes y Administradores por ahora) con un catálogo en tiempo real, conectándose a un backend propio de microservicios y consumiendo datos externos.

### Funcionalidades Principales
* **Autenticación y Seguridad:** Login con validación de credenciales y manejo de sesiones.
* **4 Roles de Usuario con vistas diferenciadas:**
    * **Administrador:** Gestión total (CRUD) de productos.
    * **Cliente:** Catálogo, carrito de compras y precios estándar.
* **Catálogo Interactivo:** Listado de productos por categorías (Electricidad, Gasfitería, Herramientas).
* **Integración API Externa:** Consumo de datos externos (convierte el precio a dolares) mediante Retrofit.
* **Persistencia de Datos:** Base de datos con PostgreSQL remoto.

---

## Endpoints y Arquitectura

### API Externa
* **Servicio:** GsonConverterFactory
* **Uso en la app:** Muestra el valor del dólar para herramientas importadas

### Microservicios Propios (Backend Spring Boot)
El backend gestiona la lógica de negocio y la base de datos PostgreSQL.
* **Base URL:** https://backappe3.onrender.com/

**Principales Endpoints:**
* `POST /api/usuarios/registrar` - Registro de nuevos usuarios.
* `POST /api/usuarios/login` - Autenticación.
* `GET /api/productos` - Obtener catálogo completo.
* `POST /api/productos` - Crear nuevo producto (Solo Admin).
* `DELETE /api/productos/{id}` - Eliminar producto (Solo Admin).
* `GET /api/categorias` - Listar categorías.

---

**User:**Admin1
**Password:** Admin1*

---

## Instrucciones de Ejecución

### 1. Backend (Microservicios)
1.  Asegurarse de tener la api (backend) y base de datos corriendo.

### 2. Aplicación Móvil
1.  Abrir el proyecto en **Android Studio**.
2.  Sincronizar Gradle (`File > Sync Project with Gradle Files`).
3.  Seleccionar un emulador o dispositivo físico.
4.  Dar clic en **Run 'app'**.

---
