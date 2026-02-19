-- insert users to db
INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('mini', 'Jasmin', 'Jason', 'weiblich', 'mini@example.com', 'AT', 'password', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('maxi', 'Maximilian', 'Malve', 'männlich', 'maxi@example.com', 'DE', 'password', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('justme', 'Alex', 'Allison', 'non binary', 'justme@example.com', 'CH', 'password', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('schwupsdiwups', 'Finja', 'Funkelstern', 'weiblich', 'schwupsdiwups@example.com', 'SE', 'password', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('freddy', 'Frederik', 'Feierabend', 'männlich', 'freddy@example.com', 'DE', 'password', false, NOW(), NOW(), NOW(), NULL);

INSERT INTO studybuddy_user (username, firstname, lastname, gender, email, country, password, is_admin, created_at, updated_at, last_login, foto) VALUES
    ('kimDragon', 'Kim', 'Kiss', 'fluid', 'kim@example.com', 'LU', 'password', false, NOW(), NOW(), NOW(), NULL);

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
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '1.', 'Innere Stadt', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '2.', 'Leopoldstadt', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '3.', 'Landstraße', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '4.', 'Wieden', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '5.', 'Margarethen', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '6.', 'Mariahilf', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '7.', 'Neubau', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '8.', 'Josefstadt', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '9.', 'Alsergrund', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '10.', 'Favoriten', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '11.', 'Simmering', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '12.', 'Meidling', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '13.', 'Hietzing', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '14.', 'Penzing', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '15.', 'Rudolfsheim Fünfhaus', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '16.', 'Ottakring', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '17.', 'Hernals', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '18.', 'Währing', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '19.', 'Döbling', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '20.', 'Brigittenau', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '21.', 'Floridsdorf', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '22.', 'Donaustadt', now(), now()),
    ((SELECT id FROM box WHERE title = 'Bezirke Wien'), '23.', 'Liesing', now(), now());

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
        'Kommentar von mini'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'justme'),
        (SELECT id FROM box WHERE id = 3),
        '2024-01-11T12:30:00Z',
        '2024-01-11T12:30:00Z',
        'Kommentar von justme'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'schwupsdiwups'),
        (SELECT id FROM box WHERE id = 4),
        '2024-01-12T08:22:00Z',
        '2024-01-12T08:22:00Z',
        'Kommentar von schwupsdiwups'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'kimDragon'),
        (SELECT id FROM box WHERE id = 5),
        '2024-01-13T18:00:00Z',
        '2024-01-13T18:00:00Z',
        'Kommentar von kimDragon'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'freddy'),
        (SELECT id FROM box WHERE id = 6),
        '2024-01-14T09:10:00Z',
        '2024-01-14T09:10:00Z',
        'Kommentar von freddy'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'mini'),
        (SELECT id FROM box WHERE id = 2),
        '2024-01-15T14:00:00Z',
        '2024-01-15T14:00:00Z',
        'Zweiter Kommentar von mini'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'justme'),
        (SELECT id FROM box WHERE id = 3),
        '2024-01-16T11:45:00Z',
        '2024-01-16T11:45:00Z',
        'Zweiter Kommentar von justme'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'schwupsdiwups'),
        (SELECT id FROM box WHERE id = 4),
        '2024-01-17T16:30:00Z',
        '2024-01-17T16:30:00Z',
        'Zweiter Kommentar von schwupsdiwups'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'kimDragon'),
        (SELECT id FROM box WHERE id = 5),
        '2024-01-18T19:20:00Z',
        '2024-01-18T19:20:00Z',
        'Zweiter Kommentar von kimDragon'
    ),

    (
        (SELECT id FROM studybuddy_user WHERE username = 'freddy'),
        (SELECT id FROM box WHERE id = 6),
        '2024-01-19T07:50:00Z',
        '2024-01-19T07:50:00Z',
        'text'
    );

