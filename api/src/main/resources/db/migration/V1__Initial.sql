CREATE TABLE IF NOT EXISTS public.bot_message(
    uuid uuid PRIMARY KEY,
    update_id BIGINT NOT NULL,
    message_id BIGINT NOT NULL,
    message_date timestamp without time zone NOT NULL,
    text TEXT NOT NULL,
    processing_status CHARACTER VARYING(20) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    last_modified_at timestamp without time zone NOT NULL,
    version BIGINT NOT NULL
);