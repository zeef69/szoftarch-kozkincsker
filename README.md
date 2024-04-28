# Közösségi kincskereső a belvárosban - Community treasure hunt in downtown
Házifeladat a Budapesti Műszaki és Gazdaságtudományi Egyetem Szoftverarchitektúrák tantárgyhoz

Csapattagok:
 - Csapó Botond Balázs
 - Gyulai Gergő László
 - Farkas Réka
 - Simon Zoltán

## Projekt:
### Feladat ismertetése:
A megvalósítani kívánt projekt egy kalandjáték tervező és játszó alkalmazás fejlesztése. Az app egy közös felületet biztosít 
a felhasználói közösség számára, ahol kedvük szerint tevezhetnek érdekes és izgalmas küldetéseket különböző feladatokkal, 
vagy ha nem tevzeni, hanm épp játszani van kedvük, akkor kereshetnek maguknak mások által már elkészített küldetéseket. 
A küldetéseket a felhasználók készítik el saját maguknak, amiket utána egyénileg vagy csoportosan meghirdetve és eldinítva tudnak játszani.

A megvalósított applikáció 2 fő funkcióval rendelkezik: lehetséges benne küldetést tervezni,
illetve teljesíteni egy már korábban elkészített küldetést.

### Részletes feladat és megvalósítás leírás:
A részletes feladatspecifikáció, fogalomjegyzék, felhasználói szerepek és Use-case-ek illetve az adatstruktúrák és az architektúra leírása a _Dokumentáció.pdf_ fájlban található.

### Technikai részletek:
A követelmények alapján egy **Android** alapú mobilalkalmazást valósítottunk meg **Kotlin** nyelven a JetBrains Android Studio segítségével.
Az Androidra való fejlesztéseknél egyre népszerűbb a Google Jetpack könyvtár, így ebbe az irányba mentünk el, amikor keresni kellett a követelméyneknek megfelelő architektúrát.
A korábbi tanulmányaink során felkeltette az érdeklődésünket az erre is erősen építő **RainbowCake** , ezért végül ezt válaszotottuk.
Az adatok tárolására és a felhasználói authentikációhoz **Google Firebaset** használtuk. 

## Előnézetek az elkészült alkalmazásból
Küldetés tervező felületek előnézetei. Sorrendben: 
egy küldetés és a hozzá tartozó feladatokat létrehozó felület nézet; 
egy olyan feladat létrehozó felület, ahol több helyes választ is meg lehet adni; 
egy olyan feladat létrehozó felület, ahol a térképen kell eljutnia majd a játékosnak egy adott területre. 

![image](https://github.com/zeef69/szoftarch-kozkincsker/assets/78967605/ba537667-e7cd-4f82-842d-1269820b4f9c)
![image](https://github.com/zeef69/szoftarch-kozkincsker/assets/78967605/e755250c-ed11-4a14-89eb-6801f619b28b)
![image](https://github.com/zeef69/szoftarch-kozkincsker/assets/78967605/38fccc77-abef-498f-a661-9f1b84b49040)

Játszó felületek előnézetei. Sorrendben:
egy számot válaszként váró felület;
egy listából a jó válaszokat kiválasztó felület;
egy játék szinteket mutató felület.

![image](https://github.com/zeef69/szoftarch-kozkincsker/assets/78967605/e3406a78-2ad2-4159-8c37-c975a9acdc03)
![image](https://github.com/zeef69/szoftarch-kozkincsker/assets/78967605/b6109b94-d742-4e10-aab6-054b54ddcc0f)
![image](https://github.com/zeef69/szoftarch-kozkincsker/assets/78967605/02a70678-bcb8-406f-aab0-0e4392b8bf75)

