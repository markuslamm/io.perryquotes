CREATE TABLE IF NOT EXISTS public.bot_message(
    uuid uuid PRIMARY KEY,
    update_id BIGINT NOT NULL,
    message_id BIGINT NOT NULL,
    message_date timestamp without time zone NOT NULL,
    text TEXT NOT NULL,
    processing_status CHARACTER VARYING(20) NOT NULL,
    quote_uuid uuid,
    created_at timestamp without time zone NOT NULL,
    last_modified_at timestamp without time zone NOT NULL,
    version BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.book_source (
    uuid uuid PRIMARY KEY,
    name CHARACTER VARYING(255) NOT NULL UNIQUE,
    shortcut CHARACTER VARYING(255) NOT NULL UNIQUE,
    created_at timestamp without time zone NOT NULL,
    last_modified_at timestamp without time zone NOT NULL,
    version BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.author (
    uuid uuid PRIMARY KEY,
    name CHARACTER VARYING(255) NOT NULL UNIQUE,
    created_at timestamp without time zone NOT NULL,
    last_modified_at timestamp without time zone NOT NULL,
    version BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.quote (
    uuid uuid PRIMARY KEY,
    text TEXT NOT NULL,
    book_source_uuid uuid NOT NULL REFERENCES public.book_source(uuid),
    quote_state CHARACTER VARYING(20) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    last_modified_at timestamp without time zone NOT NULL,
    version BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.quote_author (
    quote_uuid uuid NOT NULL REFERENCES public.quote (uuid),
    author_uuid uuid NOT NULL REFERENCES public.author (uuid)
);

