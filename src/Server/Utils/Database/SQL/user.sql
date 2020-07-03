-- Table: public."user"
--DROP TABLE public."user";

CREATE TABLE IF NOT EXISTS public."user"
(
    login character varying(200) COLLATE pg_catalog."default" NOT NULL,
    password character varying(200) COLLATE pg_catalog."default" NOT NULL,
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT user_login_key UNIQUE (login)
)

TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER to postgres;