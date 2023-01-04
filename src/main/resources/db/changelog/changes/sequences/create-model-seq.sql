--liquibase formatted sql
--changeset <antonpavliuk>:<create-model-seq-id>
CREATE SEQUENCE IF NOT EXISTS public.model_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.model_id_seq;
