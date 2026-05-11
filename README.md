# Taller Automotriz — Dominio

Sistema de gestión de órdenes de trabajo para un taller mecánico, modelado con principios de **Domain-Driven Design (DDD)**. El proyecto define el núcleo del dominio: entidades, value objects, repositorios y utilidades de manejo de errores, sin depender de ningún framework ni tecnología de persistencia.

---

## Tecnologías

| Herramienta | Versión |
|-------------|---------|
| Java        | 26      |
| Maven       | 3.x     |

---

## Estructura del proyecto

```
src/main/java/com/taller/
├── domain/
│   ├── entities/          # Entidades del dominio
│   │   ├── Persona.java         (clase abstracta base)
│   │   ├── Cliente.java
│   │   ├── Empleado.java
│   │   ├── Vehiculo.java
│   │   └── OrdenDeTrabajo.java
│   ├── valueobjects/      # Value Objects inmutables
│   │   ├── Email.java
│   │   ├── Placa.java
│   │   └── Telefono.java
│   ├── enums/             # Enumeraciones del negocio
│   │   ├── EstadoDelTrabajo.java
│   │   ├── Marca.java
│   │   ├── Rol.java
│   │   └── TipoDeContrato.java
│   ├── repositories/      # Contratos de persistencia (interfaces)
│   │   ├── ClienteRepository.java
│   │   ├── EmpleadoRepository.java
│   │   ├── VehiculoRepository.java
│   │   └── OrdenRepository.java
│   ├── interfaces/
│   │   ├── IServicio.java       (contrato para servicios del taller)
│   │   └── IErrorApp.java       (contrato base de errores)
│   ├── errors/
│   │   └── VerificationError.java
│   └── utils/
│       └── Result.java          (tipo Result para manejo de errores)
└── external/
    └── App.java                 (punto de entrada)
```

---

## Modelo del dominio

### Entidades

**`Persona`** — Clase abstracta base para `Cliente` y `Empleado`. Contiene `id`, `nombre`, `Telefono` y `Email`.

**`Cliente`** — Persona registrada en el taller. Puede tener uno o varios vehículos asociados. Se crea únicamente a través del factory method `Cliente.crear(...)`.

**`Empleado`** — Trabajador del taller con un `Rol` (MECÁNICO o ADMINISTRADOR) y un `TipoDeContrato` (FIJO o PARCIAL). Se crea con `Empleado.crear(...)`.

**`Vehiculo`** — Identificado de forma natural por su `Placa`. Siempre pertenece a un `Cliente`. Se crea con `Vehiculo.crear(...)`.

**`OrdenDeTrabajo`** — Entidad central del negocio. Agrupa un vehículo, un empleado a cargo y una lista de servicios. Gestiona su propio ciclo de vida:

```
PENDIENTE → EN_PROCESO → EN_ESPERA_DE_PAGO → FINALIZADO
```

Registra automáticamente la `fechaDeFinalizacion` al pasar a `EN_ESPERA_DE_PAGO` y la `fechaDePago` al pasar a `FINALIZADO`.

### Value Objects

Objetos inmutables que encapsulan validación y normalización. No pueden instanciarse directamente; se crean mediante su factory `crear(...)`, que retorna un `Result`.

| Value Object | Validación |
|---|---|
| `Email` | Formato RFC básico, normalizado a minúsculas |
| `Telefono` | 7–15 dígitos, acepta prefijo `+` internacional |
| `Placa` | Formato colombiano `ABC123`, normalizado a mayúsculas |

### Enums

| Enum | Valores |
|---|---|
| `EstadoDelTrabajo` | `PENDIENTE`, `EN_PROCESO`, `EN_ESPERA_DE_PAGO`, `FINALIZADO` |
| `Marca` | `CHEVROLET`, `MAZDA`, `TOYOTA`, `RENAULT`, `KIA` |
| `Rol` | `MECANICO`, `ADMINISTRADOR` |
| `TipoDeContrato` | `FIJO`, `PARCIAL` |

---

## Patrón Result

El dominio no usa excepciones para errores esperados. Todas las operaciones de creación y validación retornan un `Result<T, E>`:

```java
Result<Empleado, IErrorApp> resultado = Empleado.crear(
    "EMP001", "Juan Pérez", "3001234567", "juan@mail.com",
    Rol.MECANICO, TipoDeContrato.FIJO
);

if (!resultado.isSuccess) {
    System.out.println(resultado.getError().getMessage());
    return;
}

Empleado empleado = resultado.getValue();
```

Esto hace que el flujo de errores sea explícito y predecible, sin sorpresas en tiempo de ejecución.

---

## Repositorios

Las interfaces de repositorio definen el contrato de persistencia que la capa de infraestructura debe implementar. El dominio no conoce el mecanismo de almacenamiento (JPA, MongoDB, en memoria, etc.).

Operaciones comunes a todos los repositorios: `guardar`, `actualizar`, `buscarPorId`, `listarTodos`, `eliminar`, `siguienteNumeroId`.

`OrdenRepository` además expone: `listarPorEstados`, `listarPorVehiculo`, `listarPorEmpleado`.

`VehiculoRepository` usa `Placa` como identificador natural en lugar de un número secuencial.

---

## Cómo compilar

```bash
mvn compile
```

Para generar el JAR:

```bash
mvn package
```

---

## Estado del proyecto

El proyecto contiene el modelo de dominio completo con sus validaciones. Pendiente de implementar:

- Implementaciones concretas de los repositorios (capa de infraestructura)
- Casos de uso / servicios de aplicación
- Event pattern para `IErrorApp.handle()`
- Tests unitarios
- Interfaz de usuario o API REST
