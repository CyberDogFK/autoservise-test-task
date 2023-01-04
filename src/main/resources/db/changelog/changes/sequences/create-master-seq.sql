--liquibase formatted sql
--changeset <antonpavliuk>:<create-master-seq-id>
CREATE SEQUENCE IF NOT EXISTS public.master_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.master_id_seq;
