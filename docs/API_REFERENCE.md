# Petstore Backend – API Reference (v1)

## Overview
- Base URL: `https://{gateway-domain}/api`
- Authentication: Bearer JWT (header `Authorization: Bearer <token>`)
- Content-Type: `application/json`
- Versioning: `v1`

## Authentication

### POST `/auth/login`
- Request:
```json
{
  "username": "admin",
  "password": "secret123"
}
```
- Response 200:
```json
{
  "token": "jwt-token",
  "expiresIn": 3600,
  "roles": ["ROLE_ADMIN"]
}
```
- Errors: 401 (invalid credentials), 423 (account locked)

### POST `/auth/logout`
- Requires valid JWT
- Invalidates active session on server

## Inventory

### GET `/inventory/productos`
- Returns active products
- Response:
```json
[
  {
    "codigo": 101,
    "nombre": "Alimento Premium",
    "stock": 45,
    "precio": 89.90,
    "proveedorNombre": "PetSupply",
    "umbralMinimo": 10,
    "stockBajo": false,
    "imagen": "https://cdn.example.com/image.png",
    "descripcion": "Bolsa 10kg",
    "activo": true
  }
]
```

### POST `/inventory/productos`
- Body:
```json
{
  "nombre": "Collar Nylon",
  "cantidad": 20,
  "precio": 14.99,
  "idProveedor": 3,
  "umbralMinimo": 5,
  "imagen": "https://cdn.example.com/collar.png",
  "descripcion": "Collar ajustable"
}
```
- Response 201: same schema as GET
- Validation: nombre ≤ 30 chars, cantidad ≥ 1, precio ≥ 0.01, URL ≤ 500 chars

### POST `/inventory/productos/{codigo}/aumentar-stock`
- Body:
```json
{
  "cantidad": 5
}
```
- Response: product with updated stock

### POST `/inventory/productos/{codigo}/disminuir-stock`
- Body: same as aumentar
- Errors: 400 when stock insuficiente

### DELETE `/inventory/productos/{codigo}`
- Soft delete (campo `activo = false`)

### GET `/inventory/productos/stock-bajo`
- Returns productos with `stockBajo = true`

## Notificaciones

### GET `/inventory/notificaciones`
- Lista notificaciones activas
- Response:
```json
[
  {
    "id": 12,
    "idProducto": 101,
    "nombreProducto": "Alimento Premium",
    "stockActual": 5,
    "umbralMinimo": 10,
    "fechaCreacion": "2025-11-10T12:32:10Z",
    "eliminada": false
  }
]
```

### POST `/inventory/notificaciones/{id}/eliminar`
- Marca notificación como eliminada

## Error Handling
- 400: Validación fallida (Bean Validation)
- 401: Token inválido/ausente
- 403: Rol insuficiente
- 404: Recurso no encontrado
- 409: Producto duplicado
- 500: Error inesperado

Respuesta error genérica:
```json
{
  "timestamp": "2025-11-11T20:05:43.123Z",
  "status": 400,
  "error": "Bad Request",
  "message": "El nombre del producto es obligatorio",
  "path": "/api/inventory/productos"
}
```

## Security Notes
- Autenticación: JWT (HS256) emitido por `JwtService`
- Sesiones únicas por usuario (`SesionService`)
- Rate limiting recomendado en el API Gateway
- CORS: habilitar solo dominios del frontend
- Recomendado usar HTTPS con TLS 1.2+

## OpenAPI / Swagger
- Documentación interactiva: `GET /swagger-ui.html`
- Esquema OpenAPI: `GET /v3/api-docs`
- Generado automáticamente con `springdoc-openapi`


