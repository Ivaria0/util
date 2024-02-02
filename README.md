# Инструкция по запуску утилиты фильтрации содержимого файлов

При запуске утилиты в командной строке подается несколько файлов, содержащих в
перемешку целые числа, строки и вещественные числа. В качестве разделителя
используется перевод строки. Строки из файлов читаются по очереди в соответствии с их
перечислением в командной строке.

Задача утилиты записать разные типы данных в разные файлы. Целые числа в один
выходной файл, вещественные в другой, строки в третий. По умолчанию файлы с
результатами располагаются в текущей папке с именами integers.txt, floats.txt, strings.txt.
Дополнительно с помощью опции -o нужно уметь задавать путь для результатов. Опция -p
задает префикс имен выходных файлов. Например -o /some/path -p result_ задают вывод в
файлы /some/path/result_integers.txt, /some/path/result_strings.txt и тд.
По умолчанию файлы результатов перезаписываются. С помощью опции -a можно задать
режим добавления в существующие файлы.

В процессе фильтрации данных собирается статистика по каждому типу данных.
Статистика поддерживается двух видов: краткая и полная. Выбор статистики
производится опциями -s и -f соответственно. Краткая статистика содержит только
количество элементов записанных в исходящие файлы. Полная статистика для чисел
дополнительно содержит минимальное и максимальное значения, сумма и среднее.
Полная статистика для строк, помимо их количества, содержит также размер самой
короткой строки и самой длинной.



Версия Java: 21.0.2

Использовалась система сборки Maven версии 3.5.0

Использовалась сторонняя библиотека args4j версии 2.33: https://args4j.kohsuke.org/
