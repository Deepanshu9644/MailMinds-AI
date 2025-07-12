# MailMinds-AI
# 📧 MailMinds AI – Gmail AI Reply Assistant

MailMinds AI is a Chrome Extension integrated with a Java Spring Boot backend that uses AI to generate intelligent email replies directly in Gmail. It leverages Google Gemini API for natural language generation and enhances productivity by reducing manual email writing effort.

---

## 🚀 Features

- ✉️ Generates smart email replies inside Gmail  
- 🔗 Chrome Extension button to trigger backend  
- 🤖 AI-generated content using Gemini API  
- ☕ Spring Boot REST API backend  
- ⚛️ Lightweight frontend injected into Gmail DOM  
- 🔐 Firebase security integration (optional)

---

## 🛠 Tech Stack

- **Frontend:** Vanilla JS (Content Script for Chrome Extension)  
- **Backend:** Java Spring Boot  
- **AI Integration:** Google Gemini API  
- **Security:** Firebase Authentication (planned or integrated)  
- **Extension Runtime:** Chrome Extension APIs

---

## 📂 Project Structure

MailMinds-AI/
├── backend/ # Spring Boot project
├── frontend/ # Chrome extension (contentScript.js, manifest.json)
└── README.md # This file

yaml
Copy
Edit

---

## 🔧 How to Run

### 🧠 Backend (Spring Boot)

1. Navigate to the `backend/` directory:
   ```bash
   cd backend
Run the Spring Boot app:

bash
Copy
Edit
mvn spring-boot:run
🌐 Frontend (Chrome Extension)
Open Chrome and go to:

arduino
Copy
Edit
chrome://extensions/
Enable Developer Mode

Click Load Unpacked and select the frontend/ folder

Open Gmail → Click on the "Generate Reply" button near a mail → See AI magic ✨


POST /api/email/generate
Content-Type: application/json
Response: Plain String (AI-generated reply)
