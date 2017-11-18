USE [DblCentral]
GO

/****** Object:  Table [dbo].[Catering_Contact_Info]    Script Date: 01/01/2014 12:51:53 È.Ù ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Catering_Contact_Info](
	[CateringContactInfoId] [int] IDENTITY(1,1) NOT NULL,
	[Fname] [nvarchar](50) NULL,
	[Lname] [nvarchar](50) NULL,
	[Address] [nvarchar](255) NULL,
	[Phone] [nvarchar](10) NULL,
 CONSTRAINT [PK_Catering_Contact_Info] PRIMARY KEY CLUSTERED 
(
	[CateringContactInfoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


