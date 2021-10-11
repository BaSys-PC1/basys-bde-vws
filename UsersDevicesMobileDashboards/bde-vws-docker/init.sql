CREATE TABLE public."Notification" (
    "ID" character varying(100),
    "SIGNAL" character varying(11),
    "TIMESTAMP" timestamp with time zone,
    "CRITICALITY" character varying(10),
    "STATUS" character varying(10),
    "MESSAGE" TEXT,
    "VALUE" numeric,
    "USERID" character varying(100),
    "DEVICEID" character varying(100),
    "SKILLS" TEXT,
    "ROLES" TEXT,
    "CONFIRMEDBY" character varying(100),
    "CONFIRMEDDATE" timestamp with time zone,
    "TOPIC" character varying(255)	
);
ALTER TABLE public."Notification" OWNER TO postgres;

CREATE TABLE public."OperationalData" (
    "ID" character varying(100),
    "SIGNAL" character varying(11),
    "NAME" character varying(32),
    "COMMENT_DEU" TEXT,
    "COMMENT_ENG" TEXT,
    "VALUE" bigint,
    "UNIT" character varying(16),
    "NOTIFICATION_ID" character varying(100),
    "TIMESTAMP" timestamp(0) with time zone
);


ALTER TABLE public."OperationalData" OWNER TO postgres;

CREATE DATABASE basyx_registry_user;
CREATE DATABASE basyx_registry_device;
CREATE DATABASE basyx_registry_iba;
CREATE DATABASE basyx_registry_dfki;