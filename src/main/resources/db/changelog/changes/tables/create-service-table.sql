--liquibase formatted sql
--changeset <antonpavliuk>:<create-service-table>
CREATE TABLE IF NOT EXISTS public.service
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    price numeric(38,2),
    status varchar(255),
    master_id bigint,
    CONSTRAINT service_pkey PRIMARY KEY (id),
    CONSTRAINT master_fk FOREIGN KEY (master_id)
        REFERENCES public.master (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

--rollback DROP TABLE IF EXISTS public.service;
