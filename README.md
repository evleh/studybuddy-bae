# studybuddy-bae

- Wer macht welche entities:
- Minimal bis 4.6 Controller + Entiy mit null
- Maximal: mit Service + Repository
- 


## Annotations
### Annotations for Entity-Attributes
- `@Id` + `@GeneratedValue`: Gemeinsam ermöglichen sie primary keys zu definieren. 
 Dabei funktioniert `@Id` hauptsächlich als tag und mit `@GeneratedValue` kann man definieren *wie* der primary key generiert wird. 
 Aktuell verwenden wir die Strategie: `@GeneratedValue(strategy = GenerationType.IDENTITY)`. 
 Mehr Infos: https://www.javaguides.net/2023/07/jpa-id-and-generatedvalue-annotations.html
- `@UpdateTimestamp` und `@CreationTimestamp`: Hibernate Annotationen die tracking ermöglichen, nur Datentypen die einem TimeStamp entsprechen dürfen diese Annotation haben. https://www.baeldung.com/hibernate-creationtimestamp-updatetimestamp

## Verwendete Annotationen für Validation
- `@NotBlank`
- `@Min(1)` & `@Max(4)`
- `@Email`
- `@Size` (siehe zb: `@Size(min = 5, max = 2000)` in BoxCommentDto; für Strings)


## Naming

### Timestamps

Die Angabe schreibt:
> Every data entry has "createdAt at" and "updated at" (...) [Moodle: Project Description Page](https://moodle.technikum-wien.at/mod/page/view.php?id=1963792).  


Vorschlag wäre das gleich als Naming-Convention zu verwenden: createdAt und updatedAt  




## Dokumentation Allgemein: 
### Instance vs. LocalDateTime
Wir verwenden die `Instance` Klasse für alle Sachen die mit Zeit zutun haben. 
Instance ist ein spezifischer Zeitpunkt auf einer Timeline mit Unix epoch time als offset. `LocalDateTime` verwendet diesen 
Offset nicht. Beide haben allgemeine Rechenoperationen und schwierigkeiten mit Time Zones. 
Für uns ist es vermutlich egal was wir verwenden. https://www.baeldung.com/java-instant-vs-localdatetime


### HTTP Response Status Codes:
Geben an ob HTTP Request erfolgreich abgeschlossen wurde. In 5 Klassen aufgeteilt
Default verhalten von spring:
- 200 wen CRUD Methoden zu keiner Exception führen
- man kann einen ResponseStatus durch die `@ResponseStatus` Annotation zu Exceptions mappen.
  In der Controller Methode brauche ich dann keine `@ResponseStatus`Annotation.

Verwendete HTTP Codes:

| Nummer  | Erklärung | Wann verwendet|
|---------|-----------|---------------|
| **200** | OK        | default für erfolgreiche CRUD Methoden in Spring |
| **201** | Created   | create-CRUD |
| **404** | Not Found | ResourceNotFoundException |


### Checkstyle

Link to HP (extracted from lecture repo config file DTD link): [https://checkstyle.org](https://checkstyle.org)  

#### Checkstyle config syntax, documentation links

[Documentation-link to default checks overview list](https://checkstyle.org/checks.html)

#### Integration 

Link to the "maven-checkstyle-plugin" 
that seems to be used in the intro-repo of the BAE lecture 
and in the "use this" example of SLM: 
[https://maven.apache.org/plugins/maven-checkstyle-plugin/usage.html](https://maven.apache.org/plugins/maven-checkstyle-plugin/usage.html)

Note: The button that looks like a command line seems to give accessible in 
the "maven" foldout in IntelliJ seems to give something resembling what the 
maven docs describe as "command line".

Go there to try "mvn checkstyle:check" for a test of the check.

### Permission / docs

Tutorial for getting the PreAuthenticate/PostAuthenticate-Decorators to work:  
[https://www.baeldung.com/spring-security-method-security](https://www.baeldung.com/spring-security-method-security)  
