-- insert users to db
INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('mini', 'Jasmin', 'Jason', 'weiblich', 'mini@example.com', 'AT', '$2a$10$.S9J.9rdrT3T1pip1PoeUOIhQnGIs8VWC0PgO0zW5oi/zd0tnJCPy', true, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('maxi', 'Maximilian', 'Malve', 'männlich', 'maxi@example.com', 'DE', '$2a$10$IXI.nhRFIjqxHoEwgjS2d.SPLXtIv8Rshyuk8Nko63N5lEzdvWChi', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('justme', 'Alex', 'Allison', 'non binary', 'justme@example.com', 'CH', '$2a$10$IXI.nhRFIjqxHoEwgjS2d.SPLXtIv8Rshyuk8Nko63N5lEzdvWChi', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('schwupsdiwups', 'Finja', 'Funkelstern', 'weiblich', 'schwupsdiwups@example.com', 'SE', '$2a$10$IXI.nhRFIjqxHoEwgjS2d.SPLXtIv8Rshyuk8Nko63N5lEzdvWChi', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('freddy', 'Frederik', 'Feierabend', 'männlich', 'freddy@example.com', 'DE', '$2a$10$IXI.nhRFIjqxHoEwgjS2d.SPLXtIv8Rshyuk8Nko63N5lEzdvWChi', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('kimDragon', 'Kim', 'Kiss', 'fluid', 'kim@example.com', 'LU', '$2a$10$IXI.nhRFIjqxHoEwgjS2d.SPLXtIv8Rshyuk8Nko63N5lEzdvWChi', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('some_user', 'Kim', 'Kiss', 'fluid', 'somesome@example.com', 'LU', '$2a$10$IXI.nhRFIjqxHoEwgjS2d.SPLXtIv8Rshyuk8Nko63N5lEzdvWChi', false, NOW(), NOW(), NOW(), NULL);



-- insert boxes to db
INSERT INTO box (title, owner_id, created_at, updated_at, is_public, description) VALUES
    ('Bezirke Wien', (SELECT id FROM studybuddy_user WHERE username='mini'), '2025-10-03 00:00:00+00', '2025-10-03 00:00:00+00', true, 'Mit dieser Kartei kann man die 23 Wiener Gemeindebezirke lernen.');

INSERT INTO box (title, owner_id, created_at, updated_at, is_public, description) VALUES
    ('Griechisch für den Urlaub', 2, '2024-12-23 00:00:00+00', '2024-12-23 00:00:00+00', true, 'Diese Kartei enthält Wörter und Phrasen, die dir bei deinem nächsten Griechendlandurlaub bestimmt nützlich sein werden!');
INSERT INTO box (title, owner_id, created_at, updated_at, is_public, description) VALUES
    ('English classroom vocabulary', 3, '2025-09-09 00:00:00+00', '2025-09-09 00:00:00+00', false, 'Vokabel aus Unit1. Nur für mich zum Lernen für den blöden Vokabeltest.');
INSERT INTO box (title, owner_id, created_at, updated_at, is_public, description) VALUES
    ('Knochen Deutsch-Latein', 4, '2025-05-05 00:00:00+00', '2025-05-05 00:00:00+00', true, 'Die Knochen des menschlichen Körpers mit ihren deutschen und lateinischen Bezeichnungen. Für Med-Studis und alle anderen, die Anatomie lernen müssen oder wollen (freaks!) :)');
INSERT INTO box (title, owner_id, created_at, updated_at, is_public, description) VALUES
    ('Heilpflanzen Deutsch - Latein', 5, '2023-04-23 00:00:00+00', '2023-04-23 00:00:00+00', true, 'Einige wichtige Heipflanzen mit ihren deutschen und lateinischen Bezeichnungen. Vielleicht ergänz ich auch mal Kärtchen mit Bild.');
INSERT INTO box (title, owner_id, created_at, updated_at, is_public, description) VALUES
    ('English irregular plurals', 6, '2022-06-16 00:00:00+00', '2022-06-16 00:00:00+00', true, 'Some of the most common English nouns with irregular plural forms.');

-- ==============================
-- BEZIRKE WIEN
-- ==============================

INSERT INTO card (box_id, question, answer, created_at, updated_at)
VALUES
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 1. Bezirk?', 'Innere Stadt', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 2. Bezirk?', 'Leopoldstadt', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 3. Bezirk?', 'Landstraße', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 4. Bezirk?', 'Wieden', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 5. Bezirk?', 'Margarethen', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 6. Bezirk?', 'Mariahilf', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 7. Bezirk?', 'Neubau', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 8. Bezirk?', 'Josefstadt', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 9. Bezirk?', 'Alsergrund', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 10. Bezirk?', 'Favoriten', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 11. Bezirk?', 'Simmering', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 12. Bezirk?', 'Meidling', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 13. Bezirk?', 'Hietzing', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 14. Bezirk?', 'Penzing', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 15. Bezirk?', 'Rudolfsheim Fünfhaus', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 16. Bezirk?', 'Ottakring', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 17. Bezirk?', 'Hernals', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 18. Bezirk?', 'Währing', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 19. Bezirk?', 'Döbling', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 20. Bezirk?', 'Brigittenau', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 21. Bezirk?', 'Floridsdorf', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 22. Bezirk?', 'Donaustadt', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), 'Wie heißt der 23. Bezirk?', 'Liesing', now(), now());

-- ==============================
-- GRIECHISCH
-- ==============================

INSERT INTO card (box_id, question, answer, created_at, updated_at)
VALUES
    ((SELECT id FROM box WHERE title = 'Griechisch für den Urlaub'), 'Guten Morgen!', 'Kali mera!', now(), now()),
    ((SELECT id FROM box WHERE title = 'Griechisch für den Urlaub'), 'Guten Abend!', 'Kali spéra', now(), now()),
    ((SELECT id FROM box WHERE title = 'Griechisch für den Urlaub'), 'Danke!', 'Efcharistó!', now(), now()),
    ((SELECT id FROM box WHERE title = 'Griechisch für den Urlaub'), 'Guten Tag!', 'Yassas!', now(), now()),
    ((SELECT id FROM box WHERE title = 'Griechisch für den Urlaub'), 'bitte', 'paracalló', now(), now());

-- ==============================
-- ENGLISH CLASSROOM
-- ==============================

INSERT INTO card (box_id, question, answer, created_at, updated_at)
VALUES
    ((SELECT id FROM box WHERE title = 'English classroom vocabulary'), 'Tafel', 'board', now(), now()),
    ((SELECT id FROM box WHERE title = 'English classroom vocabulary'), 'Bleistift', 'pencil', now(), now()),
    ((SELECT id FROM box WHERE title = 'English classroom vocabulary'), 'Buch, Heft', 'book', now(), now()),
    ((SELECT id FROM box WHERE title = 'English classroom vocabulary'), 'Sessel', 'chair', now(), now()),
    ((SELECT id FROM box WHERE title = 'English classroom vocabulary'), 'Schreibtisch', 'desk', now(), now()),
    ((SELECT id FROM box WHERE title = 'English classroom vocabulary'), 'Schultasche', 'schoolbag', now(), now());


-- ======================================
-- BOX COMMENTS
-- ======================================

INSERT INTO box_comment (author_id, box_id, created_at, updated_at, text)
VALUES
    (
        (SELECT id FROM studybuddy_user WHERE username = 'mini'),
        (SELECT id FROM box WHERE id = 2),
        '2024-01-10T10:15:00Z',
        '2024-01-10T10:15:00Z',
        'Poli oreia! Voll schöne Urlaubsvorfreudebeschäftigung! =)'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'justme'),
        (SELECT id FROM box WHERE id = 1),
        '2024-01-11T12:30:00Z',
        '2024-01-11T12:30:00Z',
        'Stabil! Geotest kann kommen.'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'schwupsdiwups'),
        (SELECT id FROM box WHERE id = 1),
        '2024-01-12T08:22:00Z',
        '2024-01-12T08:22:00Z',
        'Seit 20 Jahren wohn ich in Wien und dermerk mir die Bezirke einfach nicht. Mit dieser Kartei hatte ich sie in einer Woche intus - und Spaß gemacht hat es auch noch.👍'

    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'kimDragon'),
        (SELECT id FROM box WHERE id = 5),
        '2024-01-13T18:00:00Z',
        '2024-01-13T18:00:00Z',
        'Oh wie schön! Hätte nicht gedacht, dass ich zu so einem Nischenthema eine Kartei hier finde! Hilft mir sehr bei meiner Kräuterpädagogik-Ausbildung!'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'freddy'),
        (SELECT id FROM box WHERE id = 6),
        '2024-01-14T09:10:00Z',
        '2024-01-14T09:10:00Z',
        'LIFE SAVER für die English-Revision 😅'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'maxi'),
        (SELECT id FROM box WHERE id = 2),
        '2024-02-15T14:00:00Z',
        '2024-02-15T14:00:00Z',
        'Efcharistó! Freut mich, dass du dran Freude hast! Schönen Urlaub!🌞'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'justme'),
        (SELECT id FROM box WHERE id = 4),
        '2024-01-16T11:45:00Z',
        '2024-01-16T11:45:00Z',
        'Noice. Werd ich mir für den Biotest reinziehen.'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'schwupsdiwups'),
        (SELECT id FROM box WHERE id = 4),
        '2024-02-17T16:30:00Z',
        '2024-02-17T16:30:00Z',
        'Viel Erfolg beim Test! Lass mich wissen, wenn du noch andere Karteien brauchst ;)'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'kimDragon'),
        (SELECT id FROM box WHERE id = 5),
        '2024-01-18T19:20:00Z',
        '2024-01-18T19:20:00Z',
        'Heyo! Ich wollt mal fragen, ob du das mit den Bildern noch vorhast? Sonst kann ich gern eine eigene Kartei mit den Bildern machen. Wär sehr hilfreich!'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'freddy'),
        (SELECT id FROM box WHERE id = 6),
        '2024-02-19T07:50:00Z',
        '2024-02-19T07:50:00Z',
        'Ich schlag dir einen Deal vor: Du machst mir eine Kartei zu irregular verbs und ich füge die Bilder zu den Heilpflanzen hinzu. Wär das was?'
    );

