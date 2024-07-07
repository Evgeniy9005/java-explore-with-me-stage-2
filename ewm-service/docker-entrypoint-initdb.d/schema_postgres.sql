CREATE TABLE IF NOT EXISTS public.ewm
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    APP character varying(512) COLLATE pg_catalog."default" NOT NULL,
    URI character varying(512) COLLATE pg_catalog."default" NOT NULL,
    ip character varying(16) COLLATE pg_catalog."default" NOT NULL,
    TIMESTAMP TIMESTAMP NOT NULL,
    CONSTRAINT pk_ewm PRIMARY KEY (id)
);
