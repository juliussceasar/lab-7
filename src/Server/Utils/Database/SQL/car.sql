-- Table: public.car
--DROP TABLE public.car;

CREATE TABLE IF NOT EXISTS public.car
(
    name character varying(1000) COLLATE pg_catalog."default" not null,
    cool boolean not null
)

TABLESPACE pg_default;

ALTER TABLE public.car
    OWNER to postgres;