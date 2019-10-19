# Style de code à respecter
## Langue
Le code doit être entièrement en Anglais. Cela inclut le nom des classes/variables, les commentaires, et les strings. Veillez à ne pas faire de "Franglais". Les strings seront traduis via le système de ressources Android. Si il n'y a vraiment pas d'autre choix, il est tout de même possible d'écrire un string en Français (mais il faut que ça soit justifié !!).

## Indentation
 * Espaces uniquement (pas de tabulations)
 * Un niveau = 4 espaces

## Espacement
 * Pas d'espacement avec les parenthèses: `func(argument)`, `if(condition)`, etc...
 * Espaces avec tous les opérateurs SAUF les opérateurs unitaires:
    - `if(condition1 && condition2)`
    - `variable1 += variable2;`
    - `variable++;`
    - `if(!condition)`
    - `() -> { ... }`
 * Les accollades sont toujours espacées, sauf dans le cas d'une annotation prenant un tableau, et du point virgule finale:
    - `if(condition) {`
    - `int[] array = new int[] { 1, 2, 3, ... };`
    - `@Annotation({ 1, 2, 3, ... })`

## Sauts de ligne
Les accollades doivent se trouver sur la ligne du mot clé (class, if, while, etc...). Aucune instruction ne doit être placée sur la même ligne qu'une accollade (à part, bien sûr, lors de la définition d'un tableau).

```java
public class Test {

    private int[] array = new int[] { 1, 2, 3 };

    public static void main(String[] args) {
        do {
            //...
        } while(...);

        if(condition) {
            //...
        } else {
            //...
        }
    }

}
```

Enchaînement d'appels: le développeur peut, si il le désire, placer des "enchaînements d'appels" sur plusieurs lignes, à condition d'ajouter un niveau d'indentation:
```java
Arrays.stream(array).filter(Objects::nonNull).forEach(MyObj::action); //Valid

Arrays.stream(array)
    .filter(Objects::nonNull)
    .forEach(MyObj::action); //Valid as well
```

Paramètres et conditions dans le cas de longs appels/de longues conditions: le développeur peut choisir de garder tout sur la même ligne où de faire un saut de ligne comme bon lui semble, à condition d'ajouter un niveau d'indentation. Si les paramètres/les conditions dépassent la largeur de l'écran, il est conseillé (mais pas obligatoire) d'effectuer ces sauts de lignes:
```java
object.function(param1, param2, param3, param4, param5, param6, param7, param8, param9); //Valid

if(condition1 && condition2 && condition3 && condition4 && condition5 && condition6) { //Valid
    //...
}

object.function(param1, param2, param3,
    param4, param5, param6,
    param7, param8, param9); //Valid as well

if(condition1 && condition2 && condition3
    && condition4 && condition5 && condition6) { //Valid as well
    //...
}
```

## Présence des accollades
Si le bloc ne contient qu'une seule instruction, ne pas mettre d'accollades, SAUF dans le cas où les blocs chainés au dessous **ET** en dessous utilisent des accollades.
```java
if(condition)
    instruction();

if(condition1) {
    instruction1();
    instruction2();
} else
    instruction3();

if(condition1)
    instruction1();
else {
    instruction2();
    instruction3();
}

while(condition)
    instruction();

Arrays.stream(array).forEach(obj -> func(obj, 42));

//DO NOT DO THIS:
if(condition1) {
    instruction1();
    instruction2();
} else if(condition2)
    instruction3();
else {
    instruction4();
    instruction5();
}
```

## Lignes vides
 * Pas lignes vides au début du fichier
 * Pas de ligne vide entre le premier commentaire du fichier (si il y en a) et l'instruction `package` (l'instruction `package` doit être la première instruction)
 * Une seule ligne vide doit se trouver entre l'instruction `package` et le premier `import`
 * Une seule ligne vide doit se trouver entre le dernier `import` et la définition de la classe/interface/enum
 * Une seule ligne vide doit se trouver entre la définition de la classe/interface/enum et le premier champ/méthode
 * Un maximum d'une seule ligne vide doit se trouver entre deux champs (veillez a l'utiliser pour séparer des "groupes" de champs, ou si ce champ est precede d'un commentaire)
 * Une seule ligne vide doit se trouver entre le dernier champ et la première méthode
 * Une seule ligne vide doit se trouver entre chaque méthode
 * Une seule ligne vide doit se trouver entre le dernier champ/méthode et la fermeture de la classe/interface/enum
 * Pas de nouvelle ligne entre la javadoc et le champ qu'elle décrit

## Champs, méthodes et classes internes
### Ordre
Le contenu des classes/interfaces/enums doivent réspecter l'ordre suivant:
 1. Constantes des énums (dans le cas des énums)
 2. Constantes (champs déclarés `static final`)
 3. Classes internes
 4. Champs
 5. Méthodes

**VEILLEZ A GROUPER LE GETTER ET LE SETTER D'UN MEME CHAMP ENSEMBLE**

### Règles de nommage
 * Les constantes (enum **ET** champs `static final`) doivent êtres nommées en `CAPITALIZED_WITH_UNDERSCORES`
 * Les champs et les méthodes doivent êtres nommés en `lowerCamelCase`
 * Les types (classes) doivent être nommées en `UpperCamelCase`
 * Nom des setters: `set(Has|Is|Should|Must...){NomDeLaVariable}` (Has/Is/Should/Must/etc... optionnels, dans le cas d'un booléen)
 * Nom des getters:
    - Si non booléen: `get{NomDeLaVariable}`
    - Si booléen: `has|is|should|must|...{NomDeLaVariable}`
 * Nom des interfaces: faire en sorte qu'elles se terminent en `able`, mais ne pas forcer (si le mot d'existe pas, tant pis)
 * Nom des enums: faire en sorte que le mot soit au pluriel (exemple: Color**__s__**)

### Précisions sur le lowerCamelCase
Dans le cas ou le premier mot est un acronyme (ou s'écrit, pour certaines raisons, entierement en majuscules), il faut l'écrire en minuscule. La règle est la suivante: **peut importe le premier mot, celui-ci est écrit en minuscules**. Par exemple, on écrira `apiEndpoint` et non `APIEndpoint` (hello Amaury).

### Accès
 * Utiliser private autant que possible
 * Eviter le package-local autant que possible
 * Les champs (à part les constantes `static final`) ne devraient jamais être déclarés `public`, sauf en cas de nécéssité absolue.

### Exemple:
```java
public class Example {

    public static final int CONSTANT_1 = 42;
    public static final int CONSTANT_2 = 69;

    public static class InternalClass {

        //...

    }

    private int field11;
    private final String field12 = "test";
    private String[] field13;

    private float field21;
    private SomeClass field22;

    /**
     * @name field23
     * @desc Some description that makes up a huge comment
     */
    public boolean field23;

    private static Example instance;

    public void method1() {
        //...
    }

    private void method2() {
    }

    public void setField21(float val) {
        field21 = val;
    }

    public float getField21() {
        return field21;
    }

    public void setField22(SomeClass sc) {
        if(sc == null)
            throw new IllegalArgumentException("field22 cannot be null");

        field22 = sc;
    }

    public SomeClass getField22() {
        return field22;
    }

}
```

## Javadoc
 * Les tokens ouvrant et fermants la javadoc (réspectivement, `/**` et `*/`), doivent se trouver sur une ligne à part
 * Veillez à précéder chaques lignes des commentaires javadoc par une étoile, et à garder cette étoile alignée avec la première étoile du token ouvrant
 * Veillez à documenter au maximum les méthodes et les constantes non-privées (public, protected et package-local)

Exemple:
```java
/**
 * @name test
 * @desc description
 */
public void test() {
    //...
}
```
