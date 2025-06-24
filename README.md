# 🌍 ArcGIS MapServer API – REST paslauga ArcGIS metaduomenų nuskaitymui

Ši Spring Boot aplikacija leidžia naudotojams per HTTP užklausą pateikti ArcGIS MapServer serviso URL, automatiškai nuskaito jo metaduomenis JSON formatu ir pateikia struktūruotą atsakymą su pagrindine serviso informacija ir sluoksniais. Projektas sukurtas kaip praktinė užduotis siekiant įvertinti Java/Spring Boot kompetencijas dirbant su REST API bei išoriniais geoinformaciniais šaltiniais.

## 📌 Funkcionalumo santrauka

- ☑️ `GET` užklausa su `url` parametru, nurodančiu ArcGIS MapServer adresą
- ☑️ Automatinis `?f=json` parametro pridėjimas serviso informacijos gavimui
- ☑️ Išvestis JSON formatu, apimanti:
  - Žemėlapio pavadinimą (`mapName`)
  - Serviso aprašymą (`description`)
  - Sluoksnių sąrašą (`id`, `name`)

## 🛠️ Naudotos technologijos

- Java 17+
- Spring Boot 3.1.9
- Maven
- RestTemplate + Jackson
- Springdoc OpenAPI 2.x

## 🚀 Projekto paleidimas

```bash
git clone https://github.com/<wwiktorass1>/arcgis-api.git
cd arcgis-api
mvn spring-boot:run

```
> ⚠️ **Pastaba:** jei portas `8080` yra užimtas, galite jį pakeisti faile `src/main/resources/application.properties`:
> 
> ```properties
> server.port=8081
> ```


## Swagger / OpenAPI dokumentacija
Aplikacija automatiškai generuoja OpenAPI specifikaciją ir Swagger UI.
- Swagger UI: 
- OpenAPI JSON: 
http://localhost:8081/swagger-ui/index.html#/


GET /api/mapserver?url=https://www.geoportal.lt/mapproxy/gisc_pagrindinis/MapServer

## Testavimas

Projekto vienetinius testus galima paleisti su:
```bash
mvn test

Testavimo adresai

Galite naudoti šiuos ArcGIS MapServer URL: 
https://www.geoportal.lt/mapproxy/gisc_pagrindinis/MapServer ir https://www.geoportal.lt/mapproxy/nzt_ort10lt_2024_2026/MapServer

```

## 📁 Projekto struktūra

```
src/
├── controller/         → REST valdikliai
├── service/            → Verslo logika
├── dto/                → Duomenų modeliai (perdavimui)
├── config/             → Swagger / OpenAPI konfigūracija
└── exception/          → Klaidos apdorojimo klasės
```


## 🚀 Galimos plėtros kryptys

- Įdiegti parametrų validaciją (`@Valid`, `@NotNull`, ir kt.)
- Išplėsti atsakymą su papildomais MapServer laukais (pvz. `spatialReference`, `fullExtent`)
- Pridėti `POST` endpoint'ą su JSON `body` alternatyvai vietoj URL parametro

## 🐳 Deploy alternatyvos

Projektą galima papildyti `Dockerfile`, leidžiančiu paleisti aplikaciją konteineryje:

```dockerfile
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
CMD ["java", "-jar", "target/arcgis-api-0.0.1-SNAPSHOT.jar"]
```



Viktoras Vorobjovas



