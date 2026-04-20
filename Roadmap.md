FASE 0 - SETUP INIZIALE
Obiettivo:
- Avviare correttamente il progetto

Attività:
- Creare progetto con Spring Initializr usando :contentReference[oaicite:0]{index=0}
- Aggiungere dipendenze: Spring Web, Spring Data JPA, driver :contentReference[oaicite:1]{index=1}, Lombok (opzionale)
- Configurare application.yml
- Testare avvio applicazione

Best Practice:
- Usare application.yml invece di properties
- Separare ambienti (dev, test, prod)
- Non hardcodare credenziali


FASE 1 - CRUD BASE USER
Obiettivo:
- Implementare primo flusso completo

Entità: User
Variabili:
- Long id
- String firstName
- String lastName
- String email
- String password
- LocalDate dateOfBirth
- String taxCode (codice fiscale)
- LocalDateTime createdAt
- LocalDateTime updatedAt

Attività:
- Creare Entity, Repository, Service, Controller
- CRUD completo

Best Practice:
- Email unica (vincolo nel DB)
- Password criptata (anche se security arriva dopo)
- Non esporre password nei DTO


FASE 2 - DTO E MAPPING
Obiettivo:
- Separare API e DB

DTO:
UserRequest:
- String firstName
- String lastName
- String email
- String password

UserResponse:
- Long id
- String firstName
- String lastName
- String email

Attività:
- Introdurre mapper con :contentReference[oaicite:2]{index=2}

Best Practice:
- Non includere campi sensibili nei response
- Validare input (@NotNull, @Email, etc.)


FASE 3 - LIQUIBASE
Obiettivo:
- Versionare database

Attività:
- Integrare :contentReference[oaicite:3]{index=3}
- Creare tabella user

Best Practice:
- Naming coerente (snake_case per DB)
- Aggiungere vincoli (unique, not null)


FASE 4 - MODELLAZIONE DOMINIO

Entità: Account
Variabili:
- Long id
- String iban
- BigDecimal balance
- String currency (es. EUR)
- String type (CHECKING, SAVINGS)
- LocalDateTime createdAt
- Long userId

Entità: Card
Variabili:
- Long id
- String cardNumber
- String cvv
- LocalDate expirationDate
- String type (DEBIT, PREPAID)
- Boolean active
- Long accountId

Relazioni:
- User 1:N Account
- Account 1:N Card

Best Practice:
- IBAN unico
- Non salvare CVV in chiaro (anche se simulazione)
- Usare BigDecimal per denaro (mai double)


FASE 5 - BUSINESS LOGIC

Entità: Transaction
Variabili:
- Long id
- BigDecimal amount
- String type (DEPOSIT, WITHDRAW, TRANSFER)
- String status (PENDING, COMPLETED, FAILED)
- Long sourceAccountId
- Long destinationAccountId
- String description
- LocalDateTime createdAt

Attività:
- Deposito
- Prelievo
- Trasferimento

Best Practice:
- Validare saldo prima del prelievo
- Rendere operazioni atomiche (transazioni DB)
- Loggare operazioni importanti


FASE 6 - EXCEPTION HANDLING
Obiettivo:
- Gestire errori

Attività:
- GlobalExceptionHandler
- Custom exception

Best Practice:
- Errori chiari ma non troppo dettagliati
- Standardizzare risposta (timestamp, message, code)


FASE 7 - DOCUMENTAZIONE
Obiettivo:
- Test API

Attività:
- Swagger
- Postman

Best Practice:
- Documentare tutti gli endpoint
- Usare esempi realistici


FASE 8 - SECURITY

Entità: Role
Variabili:
- Long id
- String name (ROLE_USER, ROLE_ADMIN)

Aggiornamento User:
- Set<Role> roles

Attività:
- Integrare :contentReference[oaicite:4]{index=4}
- Login/Register
- JWT

Best Practice:
- Password con BCrypt
- Token con scadenza
- Non esporre dati sensibili nei token


FASE 9 - AUTORIZZAZIONE
Obiettivo:
- Controllo accessi

Attività:
- Limitare accesso ai dati per utente

Best Practice:
- Verificare sempre che l’utente sia proprietario della risorsa
- Usare annotazioni tipo @PreAuthorize


FASE 10 - FUNZIONALITÀ AVANZATE

Entità: MoneyRequest
Variabili:
- Long id
- BigDecimal amount
- Long requesterUserId
- Long targetUserId
- String status (PENDING, ACCEPTED, REJECTED)
- LocalDateTime createdAt

Entità: Appointment
Variabili:
- Long id
- LocalDateTime appointmentDate
- String status (BOOKED, CANCELLED)
- Long userId
- String notes

Best Practice:
- Gestire stati con enum
- Validare date (no appuntamenti nel passato)


FASE 11 - TESTING
Obiettivo:
- Qualità codice

Attività:
- Unit test service
- Integration test controller

Best Practice:
- Coprire casi limite
- Mockare dipendenze esterne


FASE 12 - OTTIMIZZAZIONE
Obiettivo:
- Performance

Attività:
- Paginazione
- Sorting
- Filtri

Best Practice:
- Evitare query pesanti
- Usare indici DB


FASE 13 - EXTRA

Aggiunte:
- createdBy, updatedBy (auditing)
- logging operazioni

Best Practice:
- Non loggare password o dati sensibili
- Mantenere codice semplice


ARCHITETTURA FINALE

Controller → DTO → Service → Mapper → Repository → Database
↓
Security
↓
Liquibase


MODELLO DOMINIO

User
├── Account
│     ├── Card
│     ├── Transaction
│
├── Appointment (nice to have)
├── MoneyRequest


CONSIGLI FINALI

- Usare BigDecimal per tutte le operazioni finanziarie
- Separare sempre DTO ed Entity
- Scrivere codice leggibile e modulare
- Testare ogni step prima di procedere
- Pensare sempre a casi reali (errori, concorrenza, sicurezza)