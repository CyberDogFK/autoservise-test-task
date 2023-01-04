--liquibase formatted sql
--changeset <antonpavliuk>:<create-brand-table>
CREATE TABLE IF NOT EXISTS public.brand
(
    id bigint NOT NULL,
    name character varying(256) NOT NULL,
    CONSTRAINT article_pkey PRIMARY KEY (id)
);

--rollback DROP TABLE brand;
