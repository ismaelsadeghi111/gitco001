USE [DblCentral]
GO

/****** Object:  Table [dbo].[popularItems]    Script Date: 26/12/2013 9:37:52 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE TABLE "popularItems" (
	"popularItems_id" bigint IDENTITY(1,1) PRIMARY KEY  NOT NULL,
	"productNo" NVARCHAR(4) NOT NULL,
	"quantity" INT NOT NULL,
	"status" BIT NULL,
	"creation_date" DATETIME NULL,
	"category_id" INT NOT NULL,
	"group_id" NVARCHAR(4) NOT NULL,
	"priority" INT NULL

) ON [PRIMARY]



GO


