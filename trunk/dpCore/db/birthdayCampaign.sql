USE [DblCentral]
GO

/****** Object:  Table [dbo].[birthdaycampaign]    Script Date: 02/14/2014 21:27:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[birthdaycampaign](
	[birthdaycampaign_id] [int] IDENTITY(1,1) NOT NULL,
	[campaign_title_en] [varchar](50) NULL,
	[campaign_title_fr] [varchar](50) NULL,
	[image_url] [varchar](50) NULL,
	[menu_id] [varchar](6) NULL,
	[campaign_date] [datetime] NULL,
	[expire_date] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[birthdaycampaign_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


