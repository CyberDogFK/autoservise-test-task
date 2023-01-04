--liquibase formatted sql
--changeset <antonpavliuk>:<create-orders-table>
CREATE TABLE IF NOT EXISTS public.orders
(
    id bigint NOT NULL,
    acceptance_date timestamp(6) without time zone,
    complete_date date,
    price numeric(38,2),
    problem_description character varying(255),
    status smallint,
    car_id bigint,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT cars_fkey FOREIGN KEY (car_id)
        REFERENCES public.car (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

--rollback DROP TABLE IF EXISTS public.orders;
