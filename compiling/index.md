---
layout: page
title: A szoftver fordítása
---

A szoftvert lehetséges parancssorban, valamint IDE-ben ( Eclipse / IntelliJ) fordítani, több platformra is.

A futtatást / fordítást mindkét esetben *maven* végzi, ami a szoftver függőségeit az első használatkor tölti le, emiatt az első futtatás előtt
hosszabb ideig várni kell.

A támogatott platformok:

* Windows
* Linux
* Web (GWT)
* Android

Fordítás parancssorból
======================

Parancssori fordításhoz / futtatáshoz szükség van a *maven* programcsomagra. Ez a legtöbb linux disztribúció alatt elérhető a 
csomagkezelő segítségével, Windows alatt pedig a JDK és a maven manuális telepítése szükséges.

### Csomag készítése

Csomag készítéséhez az *mvn package -Pplatform* parancs kiadása szükséges, ahol a platform lehet desktop, web vagy android.

A platformfüggő jar / war / apk kiterjesztésű fájlokat a platform könyvtárában, a target alkönyvtár alatt találhatjuk meg.

### A szoftver futtatása

Desktop és web platform esetén lehetőség van a csomag készítése helyett a szoftver azonnali futtatására is, ehhez a 
*mvn integration-test -Pplatform* parancs kiadása szükséges.

Fordítás / fejlesztés Eclipse alatt
===================================

Eclipse alatt a fordításhoz az m2e pluginre (alapértelmezetten megtalálható az Eclipse legtöbb kiadásában) van szükség, valamint amennyiben
az android verziót szeretnénk fordítani, ADK-ra és az m2e-android pluginre is, utóbbit az m2e marketplace-en érhetjük el. Amennyiben a projektet
az Eclipse segítségével szeretnénk letölteni github-ról az m2e-egit is ajánlott.

Eclipse projektet két módon hozhatunk létre a forráskódból:

* A forráskód letöltése után, maven projekt importálásával
* Új projekt létrehozásával (vagy szintén az import menü alatt), "Chechkout Maven Project from SCM" opcióval, a github git url megadásával

Mindkét opció ugyanúgy több projektet vesz fel, amik a különböző platformoknak felelnek meg.

Ezek után a megfelelő projektekhez hozzáadhatjuk a megfelelő futtatási konfigurációkat (pl. a desktophoz a java application-t), vagy akár maven
taskként is létrehozhatjuk őket, ugyanolyan paraméterekkel, mint a parancssori esetben.

Bővebb információ
=================

A projekt a libgdx-maven-archetype-ot használja, az itt leírt maven taskokat az definiálja. Bővebb információ megtalálható az
[archetype honlapján](https://github.com/libgdx/libgdx-maven-archetype).