# TicTacToeWithJson
Данная программа предназначена для игры в крестики-нолики в консоли.
1. Выбор режима игры.
1.1 При запуске необходимо выбрать режим работы программы: ".xml" или ".json" путем ввода цифры 1 или 2 и последующим нажатием клавиши "Enter".
2. Выбор действия.
2.1 Предлагается выбрать действие: 1 - для воспроизведения сохраненой игры, 2 - для новой игры (путем ввода цифры 1 или 2 и последующим нажатием клавиши "Enter").
2.2 Если выбран пункт 1, предлагается ввести номер сохраненной игры.
После ввода номера начнется воспроизведение.
По окончании будет предложено продолжить просмотр сохраненных игр (необходимо ввести любой символ и нажать Enter, либо просто нажать Enter).
Для выхода из программы необходимо ввести "exit" и нажать Enter.
2.3 Если выбран пункт 2, выводится основное меню игры.
3. Выбор игроков на текущую игру.
3.1 Предлагается ввести уникальный ID первого игрока (число).
3.2 Если игрок не существует в полном списке игроков, будет предложено ввести его имя.
3.3 После ввода имени создается новая запись в полном списке игроков, игрок добавляется в пул игроков на текущую игру.
3.4 Если игрок существует в полном списке, то он добавляется в пул игроков на текущую игру без ввода имени. Его имя выводится на экран.
3.5 Проводятся процедуры п. 3.1-3.4 для второго игрока.
9. Основной цикл игры
9.1 Первый игрок вводит номер строки, в которую хочет поставить крестик (от 0 до 2).
9.2 Первый игрок вводит номер столбца, в котором хочет поставить крестик (от 0 до 2).
9.3 При ошибках ввода, либо если выбранная клетка занята, программа просит повторить действия из п. 9.1-9.2.
9.4 Второй игрок выполняет п. 9.1-9.3 для нолика.
9.5 По окончании игры обновляется файл рейтинга "rating.txt", сохраняется игра в зависимости от режима в файл формата ".xml" или ".json".
Имя файла формируется из имен участвовавших игроков, даты и времени игры.
9.6 Игроку предлагается продолжить игру либо выйти.
Для выхода из программы необходимо ввести "exit" и нажать Enter. Для новой игры необходимо ввести любой символ и нажать Enter, либо просто нажать Enter.

Файл статистики сортируется в следующем порядке: 1. Максимальное количество побед, 2. Максимальное количество ничьих, 3. Минимальное количество поражений, 
4. Имя игрока, 5. ID игрока.
