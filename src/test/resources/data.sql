INSERT INTO categoria
    (ID, NOME)
VALUES
    ('72ac1bf5-acc3-4475-bb01-589738d100dd', 'Aventura'),
    ('2cad1d9c-53c6-4860-b6c9-16bdb21ea933', 'Comedia'),
    ('85b34370-80b9-4b49-99be-204510a20718', 'Terror'),
    ('2c7b6cae-034b-4da4-adb2-28f8899e900d', 'Acao'),
    ('d7cbf058-0203-42b1-8542-b432e1c3a5d7', 'Suspense'),
    ('ea15668e-006a-4c0b-b25d-df48c5652e21', 'Ficcao Cientifica');

INSERT INTO video
    (ID, TITULO, DESCRICAO, URL, DATA_PUBLICACAO)
VALUES
    ('76b6c4df-5439-4935-a5c5-45561008c780',
    'Guardioes da Galaxia Volume 3',
    'Peter Quill deve reunir sua equipe para defender o universo e proteger um dos seus. Se a missão não for totalmente bem-sucedida, isso pode levar ao fim dos Guardiões.',
    'https://www.youtube.com/watch?v=d1yNc9skssk',
    '2023-05-04'),
    ('11d171cd-8bdf-4e5c-bbe1-2d0e112cc943',
    'Duna: Parte 2',
    'Paul Atreides se une a Chani e aos Fremen enquanto busca vingança contra os conspiradores que destruíram sua família. Enfrentando uma escolha entre o amor de sua vida e o destino do universo, ele deve evitar um futuro terrível que só ele pode prever.',
    'https://www.youtube.com/watch?v=ncwsW3qxQlo',
    '2024-03-14'),
    ('2a943085-d85f-4d22-afec-f35408e5e49a',
    'Homem-Aranha: Através do AranhaVerso',
    'Depois de se reunir com Gwen Stacy, Homem-Aranha é jogado no multiverso, onde ele encontra uma equipe encarregada de proteger sua própria existência.',
    'https://www.youtube.com/watch?v=LZBlXkDvhh4',
    '2023-06-01');

INSERT INTO video_categorias
    (VIDEO_ID, CATEGORIAS_ID)
VALUES
    ('76b6c4df-5439-4935-a5c5-45561008c780', '2c7b6cae-034b-4da4-adb2-28f8899e900d'),
    ('76b6c4df-5439-4935-a5c5-45561008c780', '72ac1bf5-acc3-4475-bb01-589738d100dd'),
    ('11d171cd-8bdf-4e5c-bbe1-2d0e112cc943', 'ea15668e-006a-4c0b-b25d-df48c5652e21'),
    ('11d171cd-8bdf-4e5c-bbe1-2d0e112cc943', '72ac1bf5-acc3-4475-bb01-589738d100dd'),
    ('2a943085-d85f-4d22-afec-f35408e5e49a', '2c7b6cae-034b-4da4-adb2-28f8899e900d'),
    ('2a943085-d85f-4d22-afec-f35408e5e49a', '2cad1d9c-53c6-4860-b6c9-16bdb21ea933');
