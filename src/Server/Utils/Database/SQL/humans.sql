-- Table: public.humanBeing
--DROP TABLE public.humanBeing;

CREATE TABLE IF NOT EXISTS public.humanBeing(
    id integer primary key not null,
    name TEXT NOT NULL,
    coordinatex DOUBLE PRECISION NOT NULL,
    coordinatey DOUBLE PRECISION NOT NULL,
    creationdate TEXT NOT NULL,
    realHero boolean NOT NULL,
    hasToothpick boolean NOT NULL,
    impactSpeed FLOAT NOT NULL,
    soundtrackName TEXT NOT NULL,
    weaponType TEXT NOT NULL,
    mood TEXT NOT NULL,
    login character varying(200) COLLATE pg_catalog."default" NOT NULL)

TABLESPACE pg_default;

ALTER TABLE public.humanBeing
    OWNER to postgres;