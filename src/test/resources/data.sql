INSERT INTO users (username, password, name, surname, parent_name, creation_date, last_edit_date)
VALUES ('admin', '$2a$10$.LwGLcJn9rjUZg5sk8aYcOroNdteh/TSPiYIb4Pc5zpcG0fcUTa72', 'Admin', 'Root', 'Rootovich',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('journalist1', '$2a$10$.LwGLcJn9rjUZg5sk8aYcOroNdteh/TSPiYIb4Pc5zpcG0fcUTa72', 'Ivan', 'Ivanov', 'Ivanovich',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('journalist2', '$2a$10$.LwGLcJn9rjUZg5sk8aYcOroNdteh/TSPiYIb4Pc5zpcG0fcUTa72', 'Petr', 'Petrov', 'Petrovich',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('subscriber1', '$2a$10$.LwGLcJn9rjUZg5sk8aYcOroNdteh/TSPiYIb4Pc5zpcG0fcUTa72', 'Anna', 'Annova', 'Annovna',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('subscriber2', '$2a$10$.LwGLcJn9rjUZg5sk8aYcOroNdteh/TSPiYIb4Pc5zpcG0fcUTa72', 'Olga', 'Olgina', 'Olginichna',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('subscriber3', '$2a$10$.LwGLcJn9rjUZg5sk8aYcOroNdteh/TSPiYIb4Pc5zpcG0fcUTa72', 'Sergey', 'Sergeev',
        'Sergeevich', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('user', '$2a$10$.LwGLcJn9rjUZg5sk8aYcOroNdteh/TSPiYIb4Pc5zpcG0fcUTa72', 'Vlad', 'Ivanov',
        'Sergeevich', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO user_role (user_id, role_set)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_JOURNALIST'),
       (3, 'ROLE_JOURNALIST'),
       (4, 'ROLE_SUBSCRIBER'),
       (5, 'ROLE_SUBSCRIBER'),
       (6, 'ROLE_SUBSCRIBER');

INSERT INTO news (title, text, creation_date, last_edit_date, inserted_by_id, updated_by_id)
VALUES ('News 1', 'Text for news 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 2', 'Text for news 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 3', 'Text for news 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 4', 'Text for news 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 5', 'Text for news 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 6', 'Text for news 6', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 7', 'Text for news 7', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 8', 'Text for news 8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 9', 'Text for news 9', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 10', 'Text for news 10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
       ('News 11', 'Text for news 11', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
       ('News 12', 'Text for news 12', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
       ('News 13', 'Text for news 13', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
       ('News 14', 'Text for news 14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
       ('News 15', 'Text for news 15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
       ('News 16', 'Text for news 16', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
       ('News 17', 'Text for news 17', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
       ('News 18', 'Text for news 18', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
       ('News 19', 'Text for news 19', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
       ('News 20', 'Text for news 20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3);

-- News 1
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Отличная новость!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 1),
       ('Интересно, что будет дальше?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 1),
       ('Неожиданный поворот событий.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 1),
       ('Хорошо, что об этом рассказали.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 1),
       ('Согласен, тема важная.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 1),
       ('Хотелось бы больше деталей.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 1),
       ('Это радует!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 1),
       ('Есть ли официальные подтверждения?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 1),
       ('Похоже на правду.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 1),
       ('Спасибо за информацию.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 1);

-- News 2
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Неожиданно!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 2),
       ('Хорошая работа журналистов.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 2),
       ('Это правда?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 2),
       ('Похоже на слухи.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 2),
       ('Когда это произошло?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 2),
       ('Будет ли продолжение?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 2),
       ('Интересная тема.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 2),
       ('Жду подробностей.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 2),
       ('Сомневаюсь в достоверности.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 2),
       ('Спасибо за новость.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 2);

-- News 3
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Удивительная история!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 3),
       ('Как это возможно?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 3),
       ('Очень спорная тема.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 3),
       ('Согласен, важно обсудить.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 3),
       ('Есть доказательства?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 3),
       ('Кажется, это правда.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 3),
       ('Интересно почитать.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 3),
       ('Много вопросов без ответов.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 3),
       ('Хотелось бы узнать больше.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 3),
       ('Спасибо за информацию.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 3);

-- News 4
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Это что-то новое!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4),
       ('Не верю своим глазам.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 4),
       ('Хорошая подача материала.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 4),
       ('Где источник?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4),
       ('Звучит убедительно.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 4),
       ('Тема интересная.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 4),
       ('Почему об этом молчали раньше?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4),
       ('Жду подробностей!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 4),
       ('Спасибо за публикацию.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 4),
       ('Продолжайте в том же духе.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4);

-- News 5
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Сильная новость!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 5),
       ('Интересно разворачивается.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5),
       ('Это неожиданно.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 5),
       ('Хороший анализ.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 5),
       ('Жду подробностей!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5),
       ('Не уверен в этом.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 5),
       ('Крутая тема!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 5),
       ('Что дальше?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5),
       ('Согласен с мнением.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 5),
       ('Спасибо за новость!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 5);

-- News 6
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Вау, неожиданно!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 6),
       ('Кто проверил инфо?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 6),
       ('Довольно интересно.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 6),
       ('Нужно больше фактов.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 6),
       ('Хорошая подача!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 6),
       ('Правда ли это?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 6),
       ('Жду продолжения.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 6),
       ('Реально круто!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 6),
       ('Отличная тема!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 6),
       ('Автор молодец!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 6);

-- News 7
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Неожиданная инфо!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 7),
       ('Похоже на сенсацию.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 7),
       ('Могу поверить.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 7),
       ('Давайте обсудим.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 7),
       ('Где источник?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 7),
       ('Тема горячая.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 7),
       ('Нужно разбираться.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 7),
       ('Очень интересно!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 7),
       ('Подробности будут?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 7),
       ('Спасибо, круто!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 7);

-- News 8
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Сильное заявление!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 8),
       ('Интересная новость.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 8),
       ('Что скажут эксперты?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 8),
       ('Нужно проверить.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 8),
       ('Звучит убедительно.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 8),
       ('Хорошая работа!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 8),
       ('Жду больше инфо.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 8),
       ('Это удивляет!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 8),
       ('Классная тема.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 8),
       ('Отличная подача!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 8);

-- News 9
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Серьезная новость!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 9),
       ('Это важно обсудить.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 9),
       ('Не уверен в достоверности.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 9),
       ('Интересная аналитика.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 9),
       ('Жду официальных данных.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 9),
       ('Крутая подача материала!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 9),
       ('Тема актуальная.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 9),
       ('А что думают эксперты?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 9),
       ('Это нужно проверить.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 9),
       ('Спасибо за новость!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 9);

-- News 10
INSERT INTO comment (text, creation_date, last_edit_date, inserted_by_id, news_id)
VALUES ('Событие значимое!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 10),
       ('Неожиданный поворот.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 10),
       ('Интересно, чем закончится.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 10),
       ('Жду подробностей!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 10),
       ('Хорошая работа журналиста.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 10),
       ('А что дальше?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 10),
       ('Стоит обсудить шире.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 10),
       ('Выглядит серьезно.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6, 10),
       ('Давайте следить за этим.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 10),
       ('Спасибо, интересно!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 10);

