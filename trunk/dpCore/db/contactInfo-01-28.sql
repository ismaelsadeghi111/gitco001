USE [DblCentral]
GO

/****** Object:  Table [dbo].[contact_info]    Script Date: 1/28/2014 1:05:50 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[contact_info](
	[contact_info_id] [bigint] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NULL,
	[addressScreen_en] [nvarchar](max) NULL,
	[addressScreen_fr] [nvarchar](max) NULL,
	[postalcode] [nvarchar](50) NULL,
	[streetNumber] [nchar](8) NULL,
	[street] [nchar](50) NULL,
	[SuiteAPT] [nvarchar](15) NULL,
	[city] [nvarchar](50) NULL,
	[Building] [nchar](15) NULL,
	[doorCode] [nvarchar](15) NULL,
	[phone] [nvarchar](15) NULL,
	[ext] [nvarchar](15) NULL,
  CONSTRAINT [PK_contact_info] PRIMARY KEY CLUSTERED
    (
      [contact_info_id] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[contact_info]  WITH CHECK ADD  CONSTRAINT [FK_contact_info_web_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[web_user] ([Id])
GO


