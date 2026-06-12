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
¿Qué puede hacer Alma?

📅 Agendar una citaGuía al paciente paso a paso, verifica disponibilidad y registra todo automáticamente
🔄 Reprogramar una citaBusca la cita existente del paciente y la mueve a la nueva fecha
❌ Cancelar una citaAnula la cita y actualiza todos los registros
🔍 Consultar disponibilidadRevisa la agenda en tiempo real antes de confirmar cualquier horario
📧 Enviar confirmaciónManda un correo automático al paciente con los detalles de su cita
---
¿Con qué sistemas se conecta?

Google CalendarVer disponibilidad, crear, modificar y eliminar citas
Google SheetsRegistrar y actualizar los datos de los pacientes
GmailEnviar confirmaciones automáticas por correo
WhatsAppCanal de conversación con el paciente
---
¿Qué gana tu consultorio?
⏱️ Ahorro de tiempo — el personal deja de atender manualmente cada solicitud de cita
🌙 Disponible 24/7 — los pacientes pueden agendar en cualquier momento, incluso fuera de horario
🚫 Menos errores — los registros se actualizan automáticamente, sin datos mal anotados
😊 Mejor experiencia — el paciente recibe respuesta inmediata sin esperar
📊 Todo registrado — cada cita queda guardada en Google Sheets para reportes y seguimiento

```
Alma está desplegada y en producción en un servidor propio, disponible en todo momento.
Cualquier sistema o canal puede conectarse a ella enviando simplemente el mensaje del paciente.
---
📁 Archivos del Repositorio

📁 WK_AgenteAlma/
├── 📄 README.md          ← Este archivo
├── 📄 Dockerfile         ← Configuración del servidor
├── 📄 requirements.txt   ← Dependencias del proyecto
├── 📄 .env.example       ← Variables de configuración
└── 📁 app/
    ├── main.py           ← Punto de entrada de la API
    ├── agent.py          ← Cerebro del agente Alma
    └── tools.py          ← Acciones que Alma puede ejecutar

---
## 👤 Autor
Genaro Pol Nolazco- Consultor IA y Especialista en Infraestructura con IA 
