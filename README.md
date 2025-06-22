# ArcGIS MapServer API

Spring Boot aplikacija, leidžianti pasiekti ir transformuoti ArcGIS MapServer duomenis į struktūruotą JSON atsakymą.

## 📦 Funkcionalumas

- Vartotojas gali perduoti MapServer URL kaip parametrą
- Servisas kviečia atitinkamą ArcGIS REST API (`f=json`)
- Grąžinamas JSON su:
  - žemėlapio pavadinimu
  - aprašymu
  - sluoksnių sąrašu (`id` + `name`)

## 🔧 Naudojamos technologijos

- Spring Boot 3.1.9
- Maven
- Java 17+
- RestTemplate + Jackson

## 🚀 Paleidimas lokaliai

```bash
git clone https://github.com/<wwiktorass1>/arcgis-api.git
cd arcgis-api
mvn spring-boot:run

```
Jei portą 8080 naudoja kita programa, jį galima keisti faile application.properties:
server.port=8081