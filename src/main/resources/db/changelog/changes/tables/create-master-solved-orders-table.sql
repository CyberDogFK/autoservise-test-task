--liquibase formatted sql
--changeset <antonpavliuk>:<create-master-solved-orders-table>
CREATE TABLE IF NOT EXISTS public.master_solved_orders
(
    master_id bigint NOT NULL,
    solved_orders_id bigint NOT NULL,
    CONSTRAINT master_fkey FOREIGN KEY (master_id)
        REFERENCES public.master (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

--rollback DROP TABLE IF EXISTS public.master_solved_orders;
