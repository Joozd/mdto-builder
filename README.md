# MDTOBuilder

A lightweight Kotlin library for parsing and generating **MDTO (Metagegevens voor Duurzaam Toegankelijke Overheidsinformatie)** XML files.

The library provides:

- A schema-faithful Kotlin data model  
- A StAX-based XML parser  
- XML generation support (initially primarily for test data)  
- Optional XSD validation support (if enabled by the caller)

Designed for archival, governmental, and digital preservation workflows.

---

## Features

- Strict mapping of the MDTO XSD to Kotlin data classes  
- Deterministic parsing using `javax.xml.stream.XMLEventReader`  
- Proper handling of:
  - `xsd:date`, `gYear`, `gYearMonth`
  - `xsd:dateTime`
  - `xsd:duration`
  - `xsd:anyURI`
  - XSD unions
- Sealed model types for schema unions
- Clean separation between parsing and validation

---

## Installation

### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven {
        url = uri("https://joozd.nl/nexus/repository/maven-releases/")
    }
}

dependencies {
    implementation("nl.joozd:mdtobuilder:0.2.0-alpha-prerelease")
}
```

---

## Usage

### Parse an MDTO file

```kotlin
val root: MDTORoot = parseMdto(pathToXml)
```

The result contains:

```kotlin
data class MDTORoot(
    val content: MdtoBaseClassContent
)
```

Where `content` is either:

- `Informatieobject`
- `Bestand`

Both map directly to the MDTO XSD.

### Verify an MDTO file

```kotlin
val root: MDTORoot = validateMdtoXml(pathToXml)
```

This will verify an XML file against the curently supported MDTO XSD (MDTO-XML1.0.1.xsd). It will throw an exception if the validation failed, or do nothing if it was successful.

---



## Design Principles

- **Schema fidelity first** — Kotlin model mirrors the XSD exactly.
- **No reflection / no JAXB** — explicit StAX parsing.
- **Fail fast** — required fields are enforced.
- **Separation of concerns** — parsing is independent of validation.

---

## Validation

XSD validation is not enforced by default.

If required, validation can be performed explicitly before parsing.

This keeps the parser fast and deterministic while allowing formal validation in ingest or QA workflows.

---

## License

MDTOBuilder is licensed under the **Joozd Public Use License (JPUL) v1.0**:

- Free for use in:
  - Government organizations  
  - Educational institutions  
  - Internal business use  
  - Private projects  

- Not permitted without explicit written permission:
  - Inclusion in paid/commercial software, including (but not limited to) SaaS products.
  - Redistribution as part of commercial SaaS or licensed products  
  - Resale or sublicensing  

For licensing inquiries: contact the repository owner.

---

## Status

Early-stage development.  
API may change before 1.0 release.

Any bugs or feature requests are much appreciated.

---

## Author

Developed by Joost Welle  
Netherlands
