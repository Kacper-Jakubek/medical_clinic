# medical_clinic

![Build Status](https://github.com/Kacper-Jakubek/medical_clinic/actions/workflows/tests.yml/badge.svg)

### Lista zadań do wykonania

#### Wymagania funkcjonalne

##### Pacjenci

- [x] Utwórz profil nowego pacjenta zawierający:
    - [x] Imię
    - [x] Nazwisko
    - [x] Nr PESEL
    - [x] Data urodzenia
    - [x] Wiek
    - [x] Telefon kontaktowy
    - [x] Adres mailowy
- [x] Dodaj możliwość:
    - [x] Wyszukiwania pacjenta po numerze PESEL i wyświetlenia jego danych.
    - [x] Wyszukiwania pacjentów po nazwisku i wyświetlenia danych wszystkich znalezionych osób.
- [x] Zaimplementuj testy jednostkowe.

##### Personel medyczny

- [x] Utwórz profil lekarza zawierający:
    - [x] Dane osobowe i kontaktowe (takie jak u pacjenta).
    - [x] ID lekarza.
    - [x] Specjalizacje (np. kardiolog, ortopeda).
- [x] Dodaj możliwość:
    - [x] Dodania nowej specjalizacji dla istniejącego lekarza.
    - [x] Wyszukiwania lekarza po ID i wyświetlenia jego danych oraz specjalizacji.
    - [x] Wyszukiwania lekarzy o określonej specjalizacji i wyświetlenia ich danych oraz ID.
- [x] Zaimplementuj testy jednostkowe.

##### Dokumentacja

- [x] Przygotuj prostą dokumentację zawierającą diagram klas:
    - [x] Wszystkie klasy użyte w projekcie.
    - [x] Pola klas.
    - [x] Zależności między klasami.
- [x] Skorzystaj z prostego narzędzia, np. Excalidraw.

##### Grafik lekarzy

- [x] Utwórz grafik pracy lekarza zawierający godziny pracy w wybranym dniu.
- [x] Dodaj możliwość pobierania grafików lekarza na najbliższy tydzień.
- [x] Zaimplementuj testy jednostkowe.

##### Wizyty lekarskie

- [x] Dodaj możliwość umawiania pacjentów na wizyty lekarskie:
    - [x] W określonym dniu i godzinie.
    - [x] W godzinach pracy lekarza na podstawie grafiku.
    - [x] Bez kolizji z innymi wizytami.
- [x] Obsłuż sytuacje wyjątkowe:
    - [x] Próba umówienia wizyty poza godzinami pracy lekarza.
    - [x] Kolizja z inną wizytą w tym samym czasie.
- [x] Zaimplementuj testy jednostkowe.

#### Ocenianie

- [x] Przygotuj prezentację uwzględniającą:
    - [x] Implementację wymagań biznesowych.
    - [x] Jakość rozwiązania.
    - [x] Testy jednostkowe.
    - [x] Diagram klas.
    - [x] Prezentację funkcjonalności aplikacji.
