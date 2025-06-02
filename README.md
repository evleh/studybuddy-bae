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



## Implementation 
### Service Class:
- put/update: Die save-Methode implementiert update und add und erkennt anhand von PK ob Entity schon existiert. 
Dazu wird allerdings nicht das id-Attribut aus dem Controller verwendet. Ich lass es jetzt noch drinnen aber implementation 
muss man vermutlich nochmal genauer anschauen. 
- 

