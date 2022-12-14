# Crypto platform

Ejercicio para trabajar dobles de test, test unitarios y tests de integración

# Descripción

Crypto platform es una API que nos va a permitir estás al día en el mercado del estado de las criptomonedas.

Para eso, hace uso de una API externa cuya documentación se encuentra [aquí](https://www.coinlore.com/cryptocurrency-data-api)

Nuestra idea es ir añadiendo funcionalidades poco a poco en medida que las vayamos necesitando, pero el proyecto es
ambicioso y va a tener clave para la organización dado que nos va a permitir tomar mejores decisiones gracias 
a la información procesada y organizada en esta plataforma.


## Properties

Java version: 18

Gradle version: 7.5

Uses Spring boot as base framework and GSON for object serialization. 

Build project:
- ./gradlew build

  :warning: :warning: **Build will fail because of tests, this is done in purpose, have a look at them!**  

Run project:
- ./gradlew bootRun

---

- Get Java version:
  - java --version

- Update Java version:
  - Download the JDK (version 17 right now)
  - Remember to adjust Intellij settings File > Project Structure > Project

---

- Get current Gradle version:
    - ./gradlew --version
    
- Update Gradle version ([take in account project's Java version for compatibility](https://docs.gradle.org/current/userguide/compatibility.html)):
    - ./gradlew wrapper --gradle-version <gradle-version>