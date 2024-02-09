DO $$
BEGIN

    CREATE SEQUENCE public.url_seq
        INCREMENT BY 1
        MINVALUE 1
        MAXVALUE 9223372036854775807
        START 1
        CACHE 1
        NO CYCLE;

END $$;