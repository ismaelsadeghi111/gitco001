USE [DblCentraltest]
GO

/****** Object:  Table [dbo].[standardcampaign]    Script Date: 01/06/2014 01:10:53 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE "standardcampaign" (
	"standardcampaign_id" INT  IDENTITY(1,1) NOT NULL,
		"food_id" NVARCHAR(50) NULL,
	"custom_order_operation" NVARCHAR(10) NULL,
	"creation_date" DATETIME NULL,
	"campaign_title_en" NVARCHAR(50) NULL,
	"campaign_title_fr" NVARCHAR(50) NULL,
	"status" NVARCHAR(20) NULL,
	"campaign_date" DATETIME NULL,
	"campaign_note" NVARCHAR(50) NOT NULL,
	"order_days" NVARCHAR(50) NOT NULL,
	"image_path" NVARCHAR(50) NULL,
	"order_type" NVARCHAR(50) NULL,
	"ordered" BIT NULL,
	"order_numbers" INT NOT NULL,
	"subjectEn" NVARCHAR(50) NULL,
	"subjectFr" NVARCHAR(50) NULL
);

ALTER TABLE "dbo"."standardcampaign"
	ADD "subjectEn" NVARCHAR(50) NOT NULL;

	ALTER TABLE "dbo"."standardcampaign"
	ADD "subjectFr" NVARCHAR(50) NOT NULL;

ALTER TABLE "dbo"."standardcampaign"
	ADD "items_html_en" TEXT NULL;

	ALTER TABLE "dbo"."standardcampaign"
	ADD "items_html_fr" TEXT NULL;

	ALTER TABLE "dbo"."standardcampaign"
	ADD "image_path_en" NVARCHAR(50) NULL;