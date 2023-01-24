--liquibase formatted sql
--changeset <antonpavliuk>:<create-order-seq-id>
CREATE SEQUENCE IF NOT EXISTS public.order_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.order_id_seq;
