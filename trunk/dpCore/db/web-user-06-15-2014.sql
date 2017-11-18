ALTER TABLE "web_user"	ADD "lang" NVARCHAR(5) NOT NULL;

UPDATE "DblCentral"."dbo"."web_user" SET "lang"='Fr'
UPDATE "DblCentral"."dbo"."web_user" SET "Subscribed"=1

ALTER TABLE "dbo"."web_user" DROP COLUMN "Phone";

ALTER TABLE "dbo"."web_user" ADD "Phone" NVARCHAR(10) NOT NULL DEFAULT '';

ALTER TABLE "dbo"."web_user" ADD "reg_from" VARCHAR(50) NULL;

