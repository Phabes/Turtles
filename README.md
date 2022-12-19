# Technologie Obiektowe - projekt

- Mateusz Mazur
- Wojciech Łącki
- Mikołaj Klimek

## Milestone 2
### Model
Zaktualizowany diagram klas modelu
![image](./diagram2.jpg)

- **Klasa Vector2d** używana jest do określania pozycji pól na mapie.
- **Klasa Turtle** reprezentuje żółwia, wraz z nickiem użytkownika oraz liczbą punktów, a także wskaźniki na opcjonalne żółwie, które mogą znajdować się nad lub pod tym konkretnym żółwiem. Liczba punktów żółwia może być aktualizowana po dojściu do mety lub zjedzeniu owocu.
- **Klasa Field** reprezentuje pojedyncze pole, na którym przebywać mogą żółwie, przechowuje informacje o polach sąsiadów, do które żółwie mogą przechodzić oraz o owocu, który żółwie mogą zjeść .
- **Klasa Neigbourhood** reprezentuje  mapę odwzorowującą połączenie pola z sąsiadami na podstawie kierunku (**Direction**  - ENUM).
- **Klasa Fruit** reprezentuje owoce zjadane przez żółwie, każdy owoc ma przypisaną liczbę punktów.
- **Klasa Board** reprezentuje planszę, na której toczy się gra, zawiera listę żółwi i pól oraz wskazanie mety, pozwala zakończyć rozgrywkę po osiągnięciu mety, korzystając z warstwy persystencji zapewnia zapis do bazy danych.
Board wczytuje nieliniową planszę z pliku, co jest możliwe dzięki klasie **MapParser**.

#### Format pliku, z którego wprowadzane są dane dotyczące mapy
Każde pojedyncze pole reprezentowane jest przez wiersz:
```
<współrzędna_x>:<współrzędna_y>|<kierunek>;<kierunek>;...|<punkty_owocu>
```
Pierwsze pole pliku uznawane jest za pole startowe, a ostatnie za metę. Przykładowe mapy dodano w folderze ./resources/map.

### Gui
Za wygląd aplikacji odpowiedzialne są pliki FXML przechowywane w katalogu ./resources/view oraz powiązane z nimi kontrolery.
- **GameSettings** - ekran z wstępnymi ustawieniami - liczbą żółwi i rozmiarem planszy.
- **PlayersSettings** - ekran pobierający dane o graczach - nicki i wybrane kolory.
- **BoardPane** - ekran z planszą, po której poruszają się żółwie w czasie gry.
- **EndGame** - ekran przedstawiający wyniki po zakończeniu rozgrywki.

Klasa **App** uruchamia pierwszy widok.

Wczytywanie plików FXML ułatwia klasa **FXMLLoaderProvider**.

### Persistence
Zawiera klasy pozwalające na zapis obiektów do bazy danych. W aplikacji zapisywane są logi z podsumowaniem gier po zakończeniu rozgrywki. Zawierają one liczbę graczy oraz pól, pseudonim i ilość punktów zwycięzcy oraz datę. Do reprezentowania logów stworzona została osobna klasa **GameLog.** 

Aby umożliwić zapis do bazy wykorzystany został interfejs **GameLogRepository** rozszerzający JpaRepository ze Spring Boot.

### Testy
W **TurtlesApplicationTests** zaimplementowane zostały testy sprawdzające poprawność łączenia pól, dołączania żółwi do pól oraz zapisy logów gier do bazy danych.

Po dodaniu odpowiedniej funkcjonalności do aplikacji, **TurtlesApplicationTests** wzbogacona została również o testy sprawdzające poprwaność określania sąsiednich pól na nieliniowej mapie, operacje w klasie Vector2d oraz funkcje związane ze zjadaniem owoców przez żółwie, w tym również przy wprowadzeniu wielu żółwi na pole.
