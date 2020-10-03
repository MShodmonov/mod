create table region
(
    id            uuid default uuid_generate_v4() not null
        constraint region_pkey
            primary key,
    created_at    timestamp,
    updated_at    timestamp,
    name_oz       varchar(255),
    name_ru       varchar(255),
    name_uz       varchar(255),
    created_by_id uuid
        constraint fkk4b7iua4w2p5tyfop52pbmcp2
            references users,
    updated_by_id uuid
        constraint fkn39h59swmmt15xp25mc12ow4
            references users
);

alter table region
    owner to u1710009;

INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('50359241-63ff-4c8a-b3f4-43c7147306a4', null, null, 'Қорақалпоғистон Республикаси', 'Республика Каракалпакстан', 'Qoraqalpog‘iston Respublikasi', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('9ad44a51-9458-4f9f-959a-7bd509bbc3dd', null, null, 'Андижон вилояти', 'Андижанская область', 'Andijon viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('4c24e067-d917-493e-8e3d-84143b62d419', null, null, 'Бухоро вилояти', 'Бухарская область', 'Buxoro viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('f8981efc-05d8-480c-bed5-e63d3b7c317c', null, null, 'Жиззах вилояти', 'Джизакская область', 'Jizzax viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('33fc69b1-ea16-497d-9f90-f30685a204d5', null, null, 'Қашқадарё вилояти', 'Кашкадарьинская область', 'Qashqadaryo viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('d3cd95ac-13f9-4521-9326-65e2ea5f3802', null, null, 'Навоий вилояти', 'Навоийская область', 'Navoiy viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('49b099f8-07ab-44e3-817c-783da22a9e91', null, null, 'Наманган вилояти', 'Наманганская область', 'Namangan viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('9dc11d1b-c6a1-4287-a4a1-93c337fe9c7b', null, null, 'Самарқанд вилояти', 'Самаркандская область', 'Samarqand viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('d1b7e412-8413-4453-a26d-d68ff933171c', null, null, 'Сурхандарё вилояти', 'Сурхандарьинская область', 'Surxandaryo viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('4b403ff3-c102-4c59-afdd-78ed8a04d38e', null, null, 'Сирдарё вилояти', 'Сырдарьинская область', 'Sirdaryo viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('bd2f41b7-55fd-409d-80f7-5df256313078', null, null, 'Тошкент вилояти', 'Ташкентская область', 'Toshkent viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('0410559d-54c9-41d1-8c05-9487c7fe7b2b', null, null, 'Фарғона вилояти', 'Ферганская область', 'Farg‘ona viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('5ef20478-df6d-4564-b2f9-2487a88d436d', null, null, 'Хоразм вилояти', 'Хорезмская область', 'Xorazm viloyati', null, null);
INSERT INTO public.region (id, created_at, updated_at, name_oz, name_ru, name_uz, created_by_id, updated_by_id) VALUES ('76bd4755-c367-4c9f-8e88-dcc0fa2f90e1', null, null, 'Тошкент шаҳри', 'Город Ташкент', 'Toshkent shahri', null, null);