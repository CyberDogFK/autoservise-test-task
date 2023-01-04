--liquibase formatted sql
--changeset <antonpavliuk>:<create-owner-orders-table>
CREATE TABLE IF NOT EXISTS public.owner_orders
(
    owner_id bigint NOT NULL,
    orders_id bigint NOT NULL,
    CONSTRAINT orders_fk UNIQUE (orders_id),
    CONSTRAINT owner_fk FOREIGN KEY (owner_id)
        REFERENCES public.owner (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

--rollback DROP TABLE IF EXISTS public.owner_orders;
