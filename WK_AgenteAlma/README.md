# 🏥 Agente Alma — API de Gestión de Citas Médicas con IA
### Agente conversacional desplegado en producción | Python · LangGraph · Claude Sonnet · FastAPI

---

## 📌 Descripción General

**Agente Alma** es una API REST con inteligencia artificial conversacional que gestiona citas médicas de forma autónoma. El agente mantiene memoria de conversación por sesión, entiende lenguaje natural y ejecuta acciones reales: consulta disponibilidad, agenda en Google Calendar, registra en Google Sheets y envía confirmaciones por correo — todo a través de una arquitectura basada en **LangGraph** y **Claude Sonnet de Anthropic**.

🔗 **API en producción:** `https://appagentappointment.iainnova.net/docs`

---

## ❗ Problema que Resuelve

Los sistemas tradicionales de agendamiento médico requieren que el personal atienda manualmente cada solicitud de cita: verificar disponibilidad, registrar datos, crear el evento en el calendario y notificar al paciente. Este proceso es lento, propenso a errores y no escala.

**Solución:** Una API con agente IA que cualquier canal puede consumir (WhatsApp, web, app móvil), capaz de llevar una conversación completa con el paciente y ejecutar todas las acciones de agendamiento de forma autónoma y en tiempo real.

---

## 🏗️ Arquitectura del Sistema

```
┌─────────────────────────────────────────────────────────────┐
│                     CANAL DE ENTRADA                         │
│     WhatsApp / Web / App  →  POST /chat                      │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│                    AGENTE IA (LangGraph)                      │
│                                                              │
│   Claude Sonnet  ←→  Grafo de estado  ←→  PostgreSQL Memory  │
│                           ↓                                  │
│              8 Herramientas disponibles                      │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│                   WEBHOOKS n8n (Acciones)                     │
│  Google Calendar │ Google Sheets │ Gmail                     │
└─────────────────────────────────────────────────────────────┘
```

---

## 🚀 Endpoints de la API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/` | Información del agente (nombre, versión) |
| `GET` | `/health` | Health check del servidor |
| `POST` | `/chat` | Enviar mensaje al agente y recibir respuesta |

### Ejemplo de uso — POST /chat

**Request:**
```json
{
  "session_id": "whatsapp:+51999999999",
  "message": "Hola, quiero agendar una cita para el jueves"
}
```

**Response:**
```json
{
  "response": "¡Hola! Soy Alma. ¿Me puedes indicar tu DNI para verificar tus datos?",
  "session_id": "whatsapp:+51999999999"
}
```

> El `session_id` identifica la conversación. La memoria persiste en PostgreSQL entre reinicios del servidor.

---

## 🛠️ Herramientas del Agente

El agente dispone de 8 herramientas que invoca de forma autónoma según la necesidad:

| Herramienta | Acción |
|-------------|--------|
| `availability_calendar` | Verifica disponibilidad en Google Calendar |
| `create_event` | Crea una nueva cita en Google Calendar |
| `update_event` | Reprograma una cita existente |
| `delete_event` | Anula y elimina una cita |
| `read_sheet` | Busca datos del paciente en Google Sheets por DNI |
| `append_sheet` | Registra una nueva cita en Google Sheets |
| `update_sheet` | Actualiza el estado de una cita en Google Sheets |
| `send_email` | Envía confirmación por correo al paciente |

---

## 💬 Ejemplo de Conversación

```
👤 Usuario: "Quiero agendar una cita para el viernes"
🤖 Alma:    "¡Hola! Soy Alma. ¿Me puedes indicar tu DNI?"

👤 Usuario: "12345678"
🤖 Alma:    "Hola María. Tengo disponibilidad el viernes 20/06
             a las 09:00, 11:00 y 14:30 hs. ¿Cuál prefieres?"

👤 Usuario: "Las 11"
🤖 Alma:    "✅ Listo. Tu cita está agendada para el viernes 20/06
             a las 11:00 hs. Te envié la confirmación a tu correo."
```

---

## 🔧 Stack Técnico

| Tecnología | Uso |
|------------|-----|
| **Python 3.12** | Lenguaje base |
| **LangGraph** | Grafo de estado con memoria persistente para el agente |
| **Claude Sonnet (Anthropic)** | Modelo de lenguaje del agente |
| **FastAPI + Uvicorn** | API REST de alto rendimiento |
| **PostgreSQL + PostgresSaver** | Persistencia de sesiones conversacionales |
| **Docker** | Contenerización para despliegue |
| **Coolify** | Plataforma de despliegue con auto-deploy desde GitHub |
| **n8n** | Orquestador de integraciones (Calendar, Sheets, Gmail) |
| **pytest + httpx** | Tests de integración |

---

## 📦 Despliegue en Producción

El proyecto está **desplegado y en funcionamiento** en un servidor propio usando **Coolify**:

- Contenerizado con **Docker**
- Auto-deploy activado: cada `push` a `main` despliega automáticamente
- API pública disponible en: `https://appagentappointment.iainnova.net`
- Documentación Swagger en: `https://appagentappointment.iainnova.net/docs`

---

## ⚙️ Variables de Entorno Requeridas

```env
ANTHROPIC_API_KEY=      # API key de Anthropic (Claude)
DATABASE_URL=           # URL de conexión PostgreSQL
N8N_WEBHOOK_BASE_URL=   # URL base de tu instancia de n8n
```

---

## 🏃 Cómo Correr Localmente

```bash
# 1. Clonar el repositorio y crear entorno virtual
python -m venv venv
source venv/bin/activate  # Linux/Mac
venv\Scripts\activate     # Windows

# 2. Instalar dependencias
pip install -r requirements.txt

# 3. Configurar variables de entorno
cp .env.example .env
# Editar .env con tus valores

# 4. Levantar el servidor
uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

Con Docker:
```bash
docker build -t agente-alma .
docker run -p 8000:8000 --env-file .env agente-alma
```

---

## 📊 Resultado / Impacto

- ✅ **En producción real** — API desplegada y consumible
- ✅ **Memoria persistente** — el agente recuerda el contexto entre mensajes
- ✅ **Multi-canal** — cualquier cliente puede consumir la API (WhatsApp, web, app)
- ✅ **Acciones reales** — no solo responde, sino que ejecuta: agenda, modifica, cancela
- ✅ **Auto-deploy** — CI/CD configurado desde GitHub hacia el servidor

---

## 📁 Archivos del Repositorio

```
📁 WK_AgenteAlma/
├── 📄 README.md               ← Este archivo
├── 📄 Dockerfile
├── 📄 requirements.txt
├── 📄 .env.example
├── 📁 app/
│   ├── main.py                ← FastAPI app
│   ├── agent.py               ← Lógica del agente LangGraph
│   └── tools.py               ← Definición de las 8 herramientas
└── 📁 tests/
    └── test_chat.py
```

---

## 👤 Autor
Genaro Pol Nolazco

Desarrollado como parte del portafolio de automatizaciones e IA.  
Si tienes dudas o quieres implementar algo similar, no dudes en contactarme.
