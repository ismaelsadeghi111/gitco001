
/****** Object:  Table [dbo].[standardcampaign]    Script Date: 01/06/2014 01:10:53 ******/
SET ANSI_NULLS ON


SET QUOTED_IDENTIFIER ON


SET ANSI_PADDING ON


CREATE TABLE "unsubscribe" (
	"Id" INT  IDENTITY(1,1) NOT NULL,
	"User_id" NVARCHAR(50) NULL,
	"Reason" NVARCHAR(255) NULL
);
