--liquibase formatted sql
--changeset <antonpavliuk>:<create-master-solved-orders-table>
CREATE TABLE IF NOT EXISTS public.model
(
    id bigint NOT NULL,
    name character varying(255),
    CONSTRAINT model_pkey PRIMARY KEY (id)
)

--rollback DROP TABLE IF EXISTS public.model;
