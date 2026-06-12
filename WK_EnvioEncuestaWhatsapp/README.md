# 📋 Envío Automático de Encuesta de Satisfacción por WhatsApp
### Workflow desarrollado en n8n | Cliente: Veterinaria Viviana PetShop

---

## 📌 Descripción General

Este workflow automatiza el envío diario de encuestas de satisfacción por WhatsApp a los clientes que visitaron la veterinaria durante el día. El sistema identifica automáticamente quiénes fueron atendidos, cruza esa información con la base de datos maestra de clientes, y les envía un mensaje personalizado con un enlace a un formulario de Google Forms — todo sin intervención humana.

---

## ❗ Problema que Resuelve

La veterinaria atendía decenas de clientes por día pero no contaba con un mecanismo para recolectar feedback de forma sistemática. Hacerlo manualmente era inviable: requería tiempo del personal, era inconsistente y tenía baja tasa de respuesta.

**Solución:** Un flujo automatizado que, todos los días al mediodía, detecta los clientes del día y les envía un WhatsApp personalizado con la encuesta, sin que nadie tenga que hacer nada.

---

## ⚙️ ¿Cómo Funciona? (Flujo paso a paso)

```
[Trigger 12:00 PM]
        ↓
[Busca hoja maestra en Google Drive]
        ↓
[Lee Google Sheet Maestro → lista completa de clientes con teléfonos]
        ↓
[Busca hoja de visitas del día en Google Drive]
        ↓
[Lee Google Sheet Visitas del día → clientes atendidos hoy]
        ↓
[Cruza ambas listas → obtiene teléfonos de clientes del día]
        ↓
[Filtra: descarta clientes no registrados y registros de asistentes]
        ↓
[Segmenta cliente por cliente]
        ↓
[Arma el número con formato internacional (+549...)]
        ↓
[Construye mensaje personalizado con nombre del cliente]
        ↓
[Corrige formato del mensaje para la API]
        ↓
[Envía WhatsApp vía Evolution API]
        ↓
[Espera 30 segundos → pasa al siguiente cliente]
```

---

## 🛠️ Tecnologías y Nodos Utilizados

| Nodo | Función |
|------|---------|
| `Schedule Trigger` | Dispara el workflow todos los días a las 12:00 PM (zona horaria Argentina) |
| `Google Drive` (x2) | Busca dinámicamente los archivos de Google Sheets en Drive por nombre |
| `Google Sheets` (x2) | Lee la hoja maestra de clientes y la hoja de visitas del día |
| `Code (JS)` | Cruza ambas listas para obtener teléfono y datos del cliente visitante |
| `Filter` | Excluye registros sin coincidencia y filas de asistentes internos |
| `Split Out` | Segmenta el array en ítems individuales por cliente |
| `Split In Batches` | Procesa los clientes uno a uno de forma controlada |
| `Set` (x2) | Formatea el número de teléfono (+549) y construye el mensaje personalizado |
| `Code (JS)` | Normaliza saltos de línea del mensaje para compatibilidad con la API |
| `HTTP Request` | Llama a la Evolution API para enviar el mensaje por WhatsApp |
| `Wait` | Espera 30 segundos entre cada envío para no saturar la API |

---

## 🔗 Integraciones Externas

- **Google Drive** — Localización dinámica de archivos por nombre
- **Google Sheets** — Fuente de datos de clientes y visitas del día
- **Evolution API** — Envío de mensajes de WhatsApp vía instancia `GenaroNolazco`
- **Google Forms** — Formulario de encuesta de satisfacción

---

## 💬 Ejemplo del Mensaje Enviado

```
Hola [Nombre del Cliente] 👋

🐶💙 ¡Gracias por visitar Veterinaria Viviana PetShop! 🐾 Tu opinión es muy importante para nosotros.

📋 Te invitamos a responder este breve cuestionario para seguir mejorando y brindarte una experiencia aún mejor. ¡Nos encantará conocer tu opinión! 😊✨

🔗 [Enlace al formulario]

¡Esperamos verte pronto! 🐶🐱💖
```

---

## 📁 Estructura de Datos Requerida

### Google Sheet Maestro (`veterinaria_viviana_maestro`)
| Cliente | Telefono | Veterinario |
|---------|----------|-------------|
| Juan Pérez | 1134567890 | Dr. García |

### Google Sheet Visitas del Día (`veterinaria_viviana_visita`)
| Cliente |
|---------|
| Juan Pérez |

> ⚠️ Los nombres deben coincidir entre ambas hojas (la comparación ignora mayúsculas/minúsculas y espacios).

---

## 🚀 Lógica Clave del Workflow

- **Cruce de listas:** El código JavaScript busca cada cliente de la hoja de visitas en la hoja maestra para obtener su teléfono. Si no existe, devuelve "No Existe" y es filtrado.
- **Filtro inteligente:** Se excluyen automáticamente los registros de asistentes internos (campo `Veterinario = ASISTENTES`) y clientes no registrados.
- **Envío con pausa:** Entre cada WhatsApp enviado, el flujo espera 30 segundos para respetar los límites de la API y evitar bloqueos.
- **Formato de número:** Se añade automáticamente el prefijo `+549` (Argentina) al número almacenado en la hoja.

---

## 📊 Resultado / Impacto

- ✅ **100% automático** — sin intervención del personal de la veterinaria
- ✅ **Personalizado** — cada mensaje incluye el nombre del cliente
- ✅ **Escalable** — funciona con cualquier cantidad de clientes del día
- ✅ **Controlado** — pausa entre mensajes para evitar bloqueos de WhatsApp

---

## 📂 Archivos del Repositorio

```
📁 WK_EnvioEncuestaWhatsapp/
├── 📄 README.md                        ← Este archivo
└── 📄 WK_EnvioEncuestaWhatssap.json    ← Workflow importable en n8n
```

---

## 👤 Autor

Desarrollado como parte del portafolio de automatizaciones y agentes IA
