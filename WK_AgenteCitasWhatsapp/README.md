# 🤖 Agente IA de Gestión de Citas Médicas por WhatsApp
### Workflow desarrollado en n8n | Agente conversacional con memoria e integración multiservicio

---

## 📌 Descripción General

Este workflow implementa un **agente de inteligencia artificial conversacional** que gestiona citas médicas de forma completamente autónoma a través de WhatsApp. El agente entiende mensajes de texto y de voz, recuerda el historial de conversación de cada usuario, y puede agendar, reprogramar, consultar y anular citas — todo en lenguaje natural, sin intervención humana.

---

## ❗ Problema que Resuelve

Las clínicas y consultorios médicos pierden tiempo valioso atendiendo manualmente solicitudes de citas por WhatsApp: consultas de disponibilidad, cambios de horario, cancelaciones y confirmaciones. El personal de recepción debe estar disponible constantemente para responder, lo que genera cuellos de botella, errores y demoras.

**Solución:** Un agente IA disponible 24/7 que conversa con el paciente, valida su identidad, consulta disponibilidad en tiempo real, agenda en Google Calendar, actualiza registros en Google Sheets y envía confirmación por email — de forma completamente automática.

---

## ⚙️ ¿Cómo Funciona? (Flujo paso a paso)

```
[WhatsApp del paciente]
        ↓
[Evolution API → Webhook n8n]
        ↓
[Condición: ¿Es mensaje del usuario o respuesta propia? → Anti-bucle]
        ↓
[Switch: ¿Texto o Audio?]
        ├── AUDIO → Descarga base64 → Convierte archivo → OpenAI Whisper (transcripción)
        └── TEXTO → continúa directo
        ↓
[Redis Push → guarda mensaje en cola]
        ↓
[Espera 10 segundos → agrupa mensajes rápidos del mismo usuario]
        ↓
[Redis Get → lee todos los mensajes acumulados]
        ↓
[Condición: ¿Es el último mensaje? → evita procesar duplicados]
        ↓
[Conversión Array → String del mensaje consolidado]
        ↓
[Calcula día de la semana actual]
        ↓
[🧠 AGENTE IA (GPT-4o mini)]
        ├── Herramienta: Base de Conocimiento (workflow externo)
        ├── Herramienta: Google Calendar → Ver disponibilidad
        ├── Herramienta: Google Calendar → Crear cita
        ├── Herramienta: Google Calendar → Reprogramar cita
        ├── Herramienta: Google Calendar → Eliminar cita
        ├── Herramienta: Google Sheets → Buscar paciente por DNI
        ├── Herramienta: Google Sheets → Actualizar estado de cita
        └── Herramienta: Gmail → Enviar confirmación por correo
        ↓
[Corrige formato JSON de respuesta]
        ↓
[LLM Chain → estructura la respuesta en fragmentos]
        ↓
[Validador de mensajes → filtra respuestas vacías o inválidas]
        ↓
[Split Out → divide respuesta en partes si es larga]
        ↓
[Switch: ¿Respuesta en texto o audio?]
        ├── AUDIO → ElevenLabs TTS → genera audio → envía por WhatsApp
        └── TEXTO → HTTP Request → Evolution API → envía por WhatsApp
        ↓
[Redis limpia el historial de mensajes temporales]
```

---

## 🧠 Capacidades del Agente IA

El agente está instruido con un **prompt de sistema detallado** que le permite:

| Acción | Descripción |
|--------|-------------|
| ✅ Verificar disponibilidad | Consulta Google Calendar en tiempo real |
| 📅 Agendar cita | Crea el evento, actualiza Google Sheets y envía email |
| 🔄 Reprogramar cita | Modifica la fecha/hora en Google Calendar |
| ❌ Anular cita | Elimina el evento y actualiza el estado en Sheets |
| 🔍 Consultar cita | Busca los datos del paciente por DNI |

**Horario de atención configurado:** Lunes a Viernes, 08:00 a 18:00 hs (Lima, Perú)

---

## 🛠️ Tecnologías y Nodos Utilizados

| Nodo / Servicio | Función |
|-----------------|---------|
| `Webhook` | Recibe mensajes entrantes desde Evolution API |
| `IF / Switch` | Control de flujo: anti-bucle, tipo de mensaje, tipo de respuesta |
| `Redis` (Push/Get/Delete) | Cola de mensajes para agrupar envíos rápidos del mismo usuario |
| `Wait` | Espera 10s para consolidar múltiples mensajes seguidos |
| `OpenAI Whisper` | Transcripción de mensajes de voz a texto |
| `OpenAI GPT-4o mini` | Motor del agente IA y generación de respuestas |
| `Postgres Chat Memory` | Memoria persistente de conversación por usuario |
| `ElevenLabs` | Text-to-Speech para respuestas en audio |
| `Google Calendar` (x4) | Ver, crear, modificar y eliminar citas |
| `Google Sheets` (x3) | Base de datos de pacientes y estado de citas |
| `Gmail` | Envío de confirmaciones por correo |
| `HTTP Request` (x4) | Evolution API (envío de mensajes y descarga de audios) |
| `LLM Chain + Output Parser` | Estructuración y validación de la respuesta del agente |
| `Code (JS)` | Corrección de formato JSON y cálculo de día de semana |
| `Workflow Tool` | Invoca un sub-workflow de base de conocimiento |

---

## 🔗 Integraciones Externas

- **Evolution API** — Canal de entrada/salida de WhatsApp
- **OpenAI (GPT-4o mini + Whisper)** — Inteligencia del agente + transcripción de voz
- **ElevenLabs** — Síntesis de voz para respuestas en audio
- **Google Calendar** — Gestión de agenda de citas
- **Google Sheets** — Base de datos de pacientes
- **Gmail** — Notificaciones de confirmación
- **PostgreSQL** — Memoria persistente del chat por usuario
- **Redis** — Cola de mensajes temporales

---

## 💬 Ejemplo de Conversación

```
👤 Paciente: "Hola, quiero sacar una cita para el jueves"
🤖 Agente:   "Hola! Con gusto te ayudo. ¿Me puedes indicar tu DNI 
              para verificar tus datos?"

👤 Paciente: "12345678"
🤖 Agente:   "Perfecto, Juan. Tengo disponibilidad el jueves 19/06 
              a las 10:00, 11:30 y 15:00 hs. ¿Cuál prefieres?"

👤 Paciente: "Las 10"
🤖 Agente:   "Listo ✅ Tu cita está agendada para el jueves 19/06 
              a las 10:00 hs. Te envié una confirmación a tu correo."
```

---

## 🏗️ Arquitectura del Sistema

```
┌─────────────────────────────────────────────────────┐
│                    CAPA DE ENTRADA                   │
│  WhatsApp → Evolution API → Webhook n8n              │
└─────────────────────┬───────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────────┐
│               CAPA DE PREPROCESAMIENTO               │
│  Anti-bucle │ Redis Queue │ Wait │ Audio→Texto       │
└─────────────────────┬───────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────────┐
│                  CAPA DE AGENTE IA                   │
│  GPT-4o mini + Postgres Memory + 8 Herramientas     │
└─────────────────────┬───────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────────┐
│                  CAPA DE RESPUESTA                   │
│  Texto o Audio (ElevenLabs) → Evolution API          │
└─────────────────────────────────────────────────────┘
```

---

## 📊 Resultado / Impacto

- ✅ **Disponible 24/7** — responde en cualquier momento
- ✅ **Multimodal** — entiende texto y mensajes de voz
- ✅ **Con memoria** — recuerda el contexto de la conversación
- ✅ **Acción real** — agenda, modifica y cancela citas directamente
- ✅ **Anti-spam** — evita procesar mensajes duplicados o bucles infinitos
- ✅ **Confirmación automática** — envía email al paciente tras cada acción

---

## 📁 Archivos del Repositorio

```
📁 WK_AgenteCitasWhatsapp/
├── 📄 README.md                          ← Este archivo
└── 📄 WK_AgenteCitasWhatssap.json        ← Workflow importable en n8n
```

---

## 👤 Autor

Desarrollado como parte del portafolio de automatizaciones con **n8n**.  
Si tienes dudas o quieres implementar algo similar, no dudes en contactarme.
