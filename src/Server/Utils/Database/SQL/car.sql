-- Table: public.car
--DROP TABLE public.car;

CREATE TABLE IF NOT EXISTS public.car
(
    name character varying(1000) COLLATE pg_catalog."default" not null,
    cool boolean not null,
    login character varying(200) COLLATE pg_catalog."default" NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE public.car
    OWNER to postgres;