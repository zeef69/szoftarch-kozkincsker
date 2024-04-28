# Közösségi kincskereső a belvárosban - Community treasure hunt in downtown
Házifeladat a Budapesti Műszaki és Gazdaságtudományi Egyetem Szoftverarchitektúrák tantárgyhoz

Csapattagok:
 - Csapó Botond Balázs
 - Gyulai Gergő László
 - Farkas Réka
 - Simon Zoltán

## Projekt:
### Feladat ismertetése:
A megvalósítani kívánt projekt egy kalandjáték tervező és játszó alkalmazás fejlesztése, ami
a felhasználói közösség számára egy felületet biztosít, ahol kedvük szerint állíthatnak össze
érdekes és izgalmas küldetéseket, vagy kereshetnek maguknak játékos és szórakoztató
elfoglaltságot. A küldetéseket a felhasználók készítik el saját maguknak, amiket utána
egyénileg vagy csoportosan tudnak teljesíteni.

A megvalósított applikáció 2 fő funkcióval rendelkezik: lehetséges benne küldetést tervezni,
illetve teljesíteni egy már korábban elkészített küldetést.

### Technikai részletek:
A követelmények alapján egy **Android** alapú mobilalkalmazást valósítottunk meg **Kotlin** nyelven a JetBrains Android Studio segítségével.
Az Androidra való fejlesztéseknél egyre népszerűbb a Google Jetpack könyvtár, így ebbe az irányba mentünk el, amikor keresni kellett a követelméyneknek megfelelő architektúrát.
A korábbi tanulmányaink során felkeltette az érdeklődésünket az erre is erősen építő **RainbowCake** , ezért végül ezt válaszotottuk.
Az adatok tárolására és a felhasználói authentikációhoz **Google Firebaset** használtuk. 

## Előnézetek az elkészült alkalmazásból
Küldetés tervező felületek előnézetei. Sorrendben: 
egy küldetés és a hozzá tartozó feladatokat létrehozó felület nézet; 
egy olyan feladat létrehozó felület, ahol több helyes választ is meg lehet adni; 
egy olyan feladat létrehozó felület, ahol a térképen kell eljutnia majd a játékosnak egy adott területre; 

![image](https://github.com/zeef69/szoftarch-kozkincsker/assets/78967605/ba537667-e7cd-4f82-842d-1269820b4f9c)
![image](https://github.com/zeef69/szoftarch-kozkincsker/assets/78967605/e755250c-ed11-4a14-89eb-6801f619b28b)
![image](https://github.com/zeef69/szoftarch-kozkincsker/assets/78967605/38fccc77-abef-498f-a661-9f1b84b49040)



