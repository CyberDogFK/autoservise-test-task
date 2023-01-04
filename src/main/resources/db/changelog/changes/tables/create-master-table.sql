--liquibase formatted sql
--changeset <antonpavliuk>:<create-master-table>
CREATE TABLE IF NOT EXISTS public.master
(
    id bigint NOT NULL,
    name character varying(255),
    CONSTRAINT master_pkey PRIMARY KEY (id)
)

--rollback DROP TABLE IF EXISTS public.master;
