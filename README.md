<div align="center">

# 📊 Análisis de Datos y Diseño de Algoritmos

### Universidad de Sevilla

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Universidad de Sevilla](https://img.shields.io/badge/Universidad%20de%20Sevilla-red?style=for-the-badge)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=for-the-badge)

</div>

---

## 📖 Descripción

Este repositorio contiene los **ejercicios realizados** en la asignatura de **Análisis de Datos y Diseño de Algoritmos (ADDA)** correspondiente al **2º año** del **Grado en Ingeniería Informática - Tecnologías Informáticas** de la **Universidad de Sevilla**.

El repositorio incluye tanto las herramientas y bibliotecas base utilizadas para resolver los ejercicios, como los ejercicios prácticos desarrollados durante el curso académico.

---

## 📁 Estructura del Proyecto

| Proyecto | Descripción |
|----------|-------------|
| `DatosCompartidos` | Clases de datos compartidas entre proyectos |
| `Genéticos` | Implementación de algoritmos genéticos |
| `Grafos` | Estructuras de datos y algoritmos sobre grafos |
| `ParteComun` | Utilidades y clases comunes |
| `Parsers` | Analizadores sintácticos |
| `Solve` | Integración con Gurobi para optimización |
| `SolveTest` | Tests de integración con Gurobi |
| `PI2_2025_2026_Base_ADDA` | Ejercicios prácticos del curso 2025/2026 |
| `EjemplosAlgoritmos` | Ejemplos de algoritmos |
| `EjemplosDeGrafos` | Ejemplos de uso de grafos |
| `EjemplosGeneticos` | Ejemplos de algoritmos genéticos |
| `EjemplosIterativosRecursivos` | Ejemplos iterativos y recursivos |
| `EjemplosPL` | Ejemplos de programación lineal |
| `EjemplosParteComun` | Ejemplos de utilidades comunes |
| `EjemplosPracticas` | Ejemplos de prácticas |
| `EjemplosRecursivos` | Ejemplos de recursividad |

---

## 👥 Autores y Contribuidores

### 🛠️ Herramientas y Bibliotecas Base
> **Miguel Toro**
> 
> Autor de las herramientas utilizadas para realizar los ejercicios:
> - `DatosCompartidos`
> - `Geneticos`
> - `Grafos`
> - `ParteComun`
> - `Parsers`
> - `Solve`
> - `SolveTest`

### 📝 Documentación
> **Álvaro Rosa**
> 
> Autor de la documentación Javadoc en español del proyecto.

### 💻 Ejercicios Prácticos (PI2_2025_2026_Base_ADDA)
> **Álvaro Rosa** y **Adrián Jiménez**
> 
> Autores de los ejercicios prácticos del curso 2025/2026.

---

## 🎓 Información Académica

| Campo | Valor |
|-------|-------|
| **Asignatura** | Análisis de Datos y Diseño de Algoritmos (ADDA) |
| **Curso** | 2º año |
| **Titulación** | Grado en Ingeniería Informática - Tecnologías Informáticas |
| **Universidad** | Universidad de Sevilla |

---

## 📚 Contenidos del Curso

- **Algoritmos Voraces (Greedy)**
- **Programación Dinámica (PD)**
- **Backtracking (BT)**
- **Algoritmos A***
- **Algoritmos Genéticos (AG)**
- **Simulated Annealing (SA)**
- **Programación Lineal (PL)**
- **Estructuras de Grafos**

---

## ⚙️ Requisitos

- **Java 17** o superior
- **Eclipse IDE** (recomendado)
- **Gurobi Optimizer** (para programación lineal)

---

## 📘 Documentación JGraphT en Español

Este repositorio incluye documentación en español para la librería **JGraphT** (versión 1.5.0), disponible en:
- **HTML**: `PI2_2025_2026_Base_ADDA/docs/jgrapht-es/`
- **JAR (Javadoc)**: `ParteComun/lib/jgrapht-core-1.5.0-javadoc.jar`

### Adjuntar Javadoc en IntelliJ IDEA

1. `File` → `Project Structure` → `Libraries`
2. Localiza la librería `jgrapht-core-1.5.0.jar`
3. Selecciona la librería y pulsa `Attach Files...` o `Attach Documentation`
4. Selecciona `ParteComun/lib/jgrapht-core-1.5.0-javadoc.jar`
5. Confirmar. Ahora, al situar el cursor sobre una clase/método de JGraphT y pulsar `Ctrl+Q` (Quick Documentation), se mostrará la documentación en español.

### Adjuntar Javadoc en Eclipse

**En el proyecto ParteComun o Grafos** (donde está JGraphT):

1. Clic derecho sobre el proyecto (`ParteComun` o `Grafos`) → `Properties`
2. Ve a `Java Build Path` → pestaña `Libraries`
3. Expande `jgrapht-core-1.5.0.jar` (dentro de la carpeta `lib`)
4. Selecciona `Javadoc location` y pulsa `Edit...`
5. Selecciona `Javadoc in archive` → `External file`
6. Pulsa `Browse...` y navega hasta `ParteComun/lib/jgrapht-core-1.5.0-javadoc.jar`
7. En `Path within archive` escribe: `/` (solo la barra)
8. Pulsa `Validate...` para verificar que Eclipse encuentra `index.html` y `package-list`
9. Pulsa `OK` y `Apply and Close`

**Verificar**: Sitúa el cursor sobre una clase de JGraphT (ej: `SimpleGraph`) y pulsa `F2` o `Shift+F2` para ver la documentación en español.

---

## 📄 Licencia

Este proyecto está licenciado bajo la **Licencia Apache 2.0** - consulta el archivo [LICENSE](LICENSE) para más detalles.

---

<div align="center">

**Universidad de Sevilla** | Escuela Técnica Superior de Ingeniería Informática

</div>
