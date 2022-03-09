# Учебные задания университета ИТМО
## Программа для рекурсивного обхода директорий и подсчета хэшей файлов
  1. Пока что не выложена из-за угрозы списывания кода
## Домашние задания по курсу Web-программирования
  1. Ссылка на код
## Программа для разбора и вычисления выражений трёх переменных
1. Имеет интерфейс коммандной строки, принимает следующие параметры:<br>
    * Тип, в котором будут производится вычисления
    
        <table> <tbody> <tr> <th>Режим</th> <th>Тип</th> </tr><tr> <td><code>-i</code></td><td><code>int</code> (с детекцией переполнений)</td></tr><tr> <td><code>-d</code> </td><td><code>double</code></td></tr><tr> <td><code>-bi</code></td><td><code>BigInteger</code></td></tr><tr> <td><code>-u</code></td><td><code>int</code> (без детекции переполнений)</td></tr><tr> <td><code>-l</code></td><td><code>long</code> (без детекции переполнений)</td></tr><tr> <td><code>-s</code></td><td><code>short</code> (без детекции переполнений)</td></tr></tbody></table>
    * Вычисляемое выражение. Пример: `(x + z + y / 2) * 3`
2. Выводит результаты вычисления для всех целочисленных значений переменных из диапазона `[−2; 2]`
3. Обрабатывает ошибки парсинга и вычислений
4. Вычисление в различных типах реализовано с помощью Generics
5. Парсинг производится методом рекурсивного спуска
6. [Ссылка на парсер](https://github.com/bfrolikov/ITMO-prog/blob/master/prog-paradigms/expression/generic/parser/ExpressionParser.java)
7. [Ссылка на класс-вычислитель](https://github.com/bfrolikov/ITMO-prog/blob/master/prog-paradigms/expression/generic/GenericTabulator.java)
8. [Реализация на JavaScript](https://github.com/bfrolikov/ITMO-prog/blob/master/prog-paradigms/javascript/objectExpression.js), разбирающая выражения только в обратной польской записи
9. [Реализация на Clojure](https://github.com/bfrolikov/ITMO-prog/blob/master/prog-paradigms/clojure/expression.clj), разбирающая выражения, записанные в стандартной для Clojure форме
## Максимально расширяемая реализация игры m,n,k
1. Реализация [игры m,n,k](https://en.wikipedia.org/wiki/M,n,k-game)
2. Программа построена с соблюдением [SOLID](https://ru.wikipedia.org/wiki/SOLID_(%D0%BE%D0%B1%D1%8A%D0%B5%D0%BA%D1%82%D0%BD%D0%BE-%D0%BE%D1%80%D0%B8%D0%B5%D0%BD%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%BD%D0%BE%D0%B5_%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5))
3. Поддерживает символы `X`, `O`, `-` и `|`
4. Поддерживает игру 3 и 4 игроков
5. Поддерживает доску в форме ромба (квадрата, повернутого на 45°)
6. Обрабатывает ошибки ввода пользователя
7. [Ссылка на код](https://github.com/bfrolikov/ITMO-prog/tree/master/prog-intro/game)
## Seam Carving
1. Aлгоритм изменения размера изображения с учетом содержания, реализованный на C++
2. [Подробное описание задачи и код](https://github.com/bfrolikov/ITMO-prog/tree/master/cpp/seam-carving)
## Быстрая версия класса Scanner из стандартной библиотеки Java
1. Быстрый аналог класса Scanner на основе Reader
2. Поддерживает десятичные и шестнадцатеричные числа
3. [Ссылка на код](https://github.com/bfrolikov/ITMO-prog/blob/master/prog-intro/FastScanner.java)
