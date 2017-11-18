USE [DblCentral]
GO

/****** Object:  Table [dbo].[dpsetting]    Script Date: 22/11/2013 2:43:32 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[dpsetting](
	[dpsetting_id] [int]  IDENTITY(1,1) PRIMARY KEY NOT NULL,
	[dpdollarminvalue] [float] DEFAULT 0.0
) ON [PRIMARY];

INSERT INTO "dpsetting" ("dpdollarminvalue") VALUES (17);


