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
- @Email


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

